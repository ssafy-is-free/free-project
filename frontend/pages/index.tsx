import { useEffect, useLayoutEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import Splash from './splash';
import RankMenu2 from '@/components/common/RankMenu2';
import SearchBar from '@/components/rank/RankSearchBar';
import FilterIcon from '../public/Icon/FilterIcon.svg';
import MainUserItem from '@/components/rank/MainUserItem';
import MainOtherItem from '@/components/rank/MainOtherItem';
import NoAccount from '@/components/rank/NoAccount';
import LoginModal from '@/components/login/LoginModal';
import BojModal from '@/components/login/BojModal';
import FilterModal from '@/components/rank/FilterModal';
import { useDispatch, useSelector } from 'react-redux';
import { splashCheck } from '@/redux/splashSlice';
import { RootState } from '@/redux';
import {
  getBojRanking,
  getBojRankingFilter,
  getGithubRanking,
  getGithubRankingFilter,
  getMyBojRanking,
  getMyGitRanking,
} from './api/rankAxios';
import { resultInformation, resultMyInformation } from '@/components/rank/IRank';
import { Spinner } from '@/components/common/Spinner';
import { useInView } from 'react-intersection-observer';
import { setNew } from '@/redux/authSlice';
import FilterOption from '@/components/rank/FilterOption';
import RSearchIcon from '../public/Icon/SearchingIcon.svg';
import SettingIcon from '../public/Icon/SettingIcon.svg';
import LogoIcon from '../public/Icon/LogoPrimaryHeader.svg';

import Profile from '@/components/profile/Profile';
import SettingModal from '@/components/rank/SettingModal';

const Wrapper = styled.div<{ searchClick: boolean }>`
  width: 100vw;
  height: 100vh;
  background-color: ${(props) => props.theme.bgWhite};
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  padding: ${(props) => (props.searchClick ? '0px 0px' : '48px 0px')};

  overflow-y: scroll;
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none; /* Firefox */
  &::-webkit-scrollbar {
    display: none; /* Chrome, Safari, Opera*/
  }

  .content-wrapper {
    /* position: absolute; */
    position: relative;
    /* border: 3px solid red; */
    z-index: 1;
    /* bottom: 0; */
    padding: 1rem 2rem;
    width: 100%;
    height: calc(100vh - 40px);
    background-color: ${(props) => props.theme.bgWhite};
    display: flex;
    flex-direction: column;

    .option-box {
      width: 100%;
      margin-bottom: 16px;
    }

    .my-rank {
      p {
        font-size: 20px;
        font-weight: bold;
        margin-bottom: 16px;
      }
      margin-bottom: 16px;
    }

    .all-rank {
      p {
        font-size: 20px;
        font-weight: bold;
        /* margin-bottom: 16px; */
        padding-top: 16px;
        padding-bottom: 16px;
        background-color: ${(props) => props.theme.bgWhite};
        width: 100%;
      }
      .rank-list {
        li {
          margin-bottom: 8px;
        }

        .observer-box {
          height: 5%;
          width: 100%;
          background-color: white;
        }
      }
    }
  }

  .search-content-wrapper {
    position: relative;
    z-index: 1;
    padding: 1rem 2rem;
    width: 100%;
    height: 100%;
    background-color: ${(props) => props.theme.bgWhite};
    display: flex;
    flex-direction: column;

    overflow-y: scroll;
    -ms-overflow-style: none; /* IE and Edge */
    scrollbar-width: none; /* Firefox */
    &::-webkit-scrollbar {
      display: none; /* Chrome, Safari, Opera*/
    }
  }
`;

const Header = styled.div`
  width: 100%;
  height: 48px;
  background-color: ${(props) => props.theme.bgWhite};
  display: flex;
  align-items: center;
  justify-content: space-around;
  position: fixed;
  top: 0;
  z-index: 2;

  .logo-box {
    height: 100%;
    /* width: 40px; */
    padding: 16px 8px 8px;
    position: absolute;
    left: 16px;
    display: flex;
    align-items: center;
  }

  .header-right {
    display: flex;
    position: absolute;
    right: 16px;
    height: 100%;

    .icon-box {
      height: 100%;
      width: 40px;
      padding: 16px 4px 8px;
      display: flex;
      align-items: center;
      justify-content: center;
      margin-right: 4px;
    }

    .filter-box {
      height: 100%;
      width: 40px;
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 12px 4px 4px;
      margin-right: 4px;
    }

    .setting-box {
      height: 100%;
      width: 40px;
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 16px 4px 8px;
    }
  }
`;

const Main = () => {
  // login 상태값 가져오기
  const isLogin = useSelector<RootState>((selector) => selector.authChecker.isLogin);
  // isnew 상태값 가져오기
  const isNew = useSelector<RootState>((selector) => selector.authChecker.isNew);
  // 로그인 중임을 나타내는 state
  const loginStart = useSelector<RootState>((selector) => selector.authChecker.loginStart);

  // splash 상태관리
  const splashState = useSelector<RootState>((selector) => selector.splashChecker.check);
  const dispatch = useDispatch();

  // 랭크 menu select 모달 열기
  const [openSelect, setOpenSelect] = useState<boolean>(false);
  // 로그인 모달 열기
  const [openLogin, setOpenLogin] = useState<boolean>(false);
  // 백준 모달 열기
  const [opeBoj, setOpenBoj] = useState<boolean>(false);
  // filter 모달 열기
  const [openFilter, setOpenFilter] = useState<boolean>(false);
  // 피드백 setting 모달 열기
  const [openSetting, setOpenSetting] = useState<boolean>(false);
  // 상세정보 열기
  const [openProfile, setOpenProfile] = useState<boolean>(false);
  const [clickedUserId, setClickedUserId] = useState<number>(0);
  const [clickMy, setClickMy] = useState(false);

  // 무한 스크롤 구현하기
  const [ref, inView, entry] = useInView({
    threshold: 0,
  });
  const [inViewFirst, setInViewFirst] = useState<boolean>(false);
  const [loading, setLoading] = useState<boolean>(false);
  const [size, setSize] = useState<number>(20);
  const [nextRank, setNextRank] = useState<number>(1);
  const [tempRank, setTempRank] = useState<number>(1);
  const [isLangId, setIsLangId] = useState<number>(0); // 필터링 적용한 경우 무한스크롤 분기위해 추가
  const [noMore, setNoMore] = useState<boolean>(false);
  // 검색후 무한 스크롤 방지하기
  const [noScroll, setNoScroll] = useState<boolean>(false);

  // TODO 이렇게 타입을 일일이 써줘야 하나..
  /**
   * 깃허브 랭크 리스트, 백준 랭크 리스트
   */
  const [gitRankList, setGitRankList] = useState<resultInformation | null>(null);
  const [bojRankList, setBojRankList] = useState<resultInformation | null>(null);

  /**
   *  나의 깃허브 랭킹, 나의 백준 랭킹
   */
  const [myGitRank, setMyGitRank] = useState<resultMyInformation | null>(null);
  const [myBojRank, setMyBojRank] = useState<resultMyInformation | null>(null);

  // 필터 모덜에서 옵션 선택후 적용하면 필터 state에 값 셋팅
  const [selectedOption, setSelectedOption] = useState<{ languageId: number; name: string } | null>(null);

  // 검색 아이콘 클릭 여부
  const [searchClick, setSearchClick] = useState<boolean>(false);

  /**
   * splash check, useEffect
   */
  useEffect(() => {
    if (loginStart && isNew) {
      setOpenBoj(true);
      dispatch(setNew());
    }

    // dispatch(setLoginIng());

    const timer = setTimeout(() => {
      dispatch(splashCheck());
    }, 1500);
    return () => clearTimeout(timer);
  }, []);

  // 깃허브인지 백준인지 상태값 0: 깃허브, 1: 백준
  const [curRank, setCurRank] = useState<number>(0);
  const onChangeCurRank = (el: number) => {
    setNextRank(1);
    setCurRank(el);
    setOpenSelect(false);
  };

  // 상세정보 열기
  const goProfile = (userId: number) => {
    setClickedUserId(userId);
  };

  useEffect(() => {
    if (clickMy) {
      setOpenProfile(true);
    }
  }, [clickMy]);

  useEffect(() => {
    if (clickedUserId !== 0) {
      setOpenProfile(true);
    }
  }, [clickedUserId]);

  // nouserItem 클릭시
  const onClickNoUser = () => {
    if (curRank == 0) {
      // 깃허브 페이지 일 때
      // 로그인 모달 띄우기
      setOpenLogin(true);
    } else if (curRank == 1) {
      // 백준 페이지 일 때
      if (isLogin) {
        // 이미 로그인한 상태면
        // 백준 모달 띄우기
        setOpenBoj(true);
      } else {
        // 로그인 하지 않은 상태라면
        // 로그인 모달 부터 띄우기
        setOpenLogin(true);
      }
    }
  };

  // TODO : 유저가 한 명일 떄 대응하는 걸 해야함
  // 무한 스크롤 구현하기
  useEffect(() => {
    if (!noMore && !noScroll) {
      if (inView && !inViewFirst) {
        // inView가 true 일때만 실행한다.
        setInViewFirst(true);
      }

      if (inView && inViewFirst) {
        // inView가 true 일때만 실행한다.
        setTempRank(nextRank);
      }
    }
  }, [inView]);

  useEffect(() => {
    if (isLangId > 0) {
      getRankList(size, nextRank, isLangId);
    } else {
      getRankList(size, nextRank);
    }
  }, [tempRank]);

  useEffect(() => {
    setNoMore(false);
    getRankList(size, 1);
    setSelectedOption(null);
  }, [curRank]);

  // 랭킹 정보 가져오기
  const getRankList = (sizeParam: number, nextRankParam: number, languageIdParam?: number) => {
    try {
      if (curRank == 0) {
        // 깃허브 랭크 가져오기 => rank 갱신할 때마다 rank값 수정해서 보내기

        (async () => {
          let data;

          if (nextRank == 1 || nextRankParam == 1) {
            // 1등
            let data;
            if (languageIdParam) {
              // 필터 적용 O
              setIsLangId(languageIdParam);
              data = await getGithubRankingFilter(sizeParam, languageIdParam);
            } else {
              // 필터 적용 X
              setIsLangId(0);
              data = await getGithubRanking(sizeParam);
            }

            if (data.length > 0) {
              setGitRankList([...data]);
              setNextRank(data[data?.length - 1]?.rank);
            } else {
              setGitRankList(null);
            }
          } else {
            // 그 이후
            let data;
            if (languageIdParam) {
              // 필터 적용 O
              if (gitRankList) {
                setIsLangId(languageIdParam);

                const userId = gitRankList[gitRankList?.length - 1]?.userId;
                const score = gitRankList[gitRankList?.length - 1]?.score;

                data = await getGithubRankingFilter(sizeParam, languageIdParam, nextRankParam, userId, score);
              }
            } else {
              // 필터 적용 X
              if (gitRankList) {
                setIsLangId(0);

                const userId = gitRankList[gitRankList?.length - 1]?.userId;
                const score = gitRankList[gitRankList?.length - 1]?.score;

                data = await getGithubRanking(sizeParam, nextRankParam, userId, score);
              }
            }

            if (data?.length == 0) {
              // 더이상 조회할 데이터 X
              setNoMore(true);
            } else {
              // 새로 추가될 배열
              let newArr = new Array();
              data?.map((el: any) => {
                newArr.push(el);
              });

              // 이전 배열
              let oldArr = new Array();
              gitRankList?.map((el) => {
                oldArr.push(el);
              });

              setNextRank(data[data?.length - 1]?.rank);
              setGitRankList([...oldArr, ...newArr]);
            }
          }
        })();

        // 나의 깃허브 랭킹 가져오기
        if (isLogin) {
          (async () => {
            let data;
            if (languageIdParam) {
              // 필터 적용 O
              data = await getMyGitRanking(languageIdParam);
            } else {
              // 필터 적용 X
              data = await getMyGitRanking();
            }

            if (data.data?.githubRankingCover) setMyGitRank(data.data?.githubRankingCover);
            else setMyGitRank(null);
          })();
        }
      } else {
        // 백준 랭크 가져오기
        (async () => {
          let data;

          if (nextRank == 1 || nextRankParam == 1) {
            // 1등
            let data;
            if (languageIdParam) {
              // 필터 적용 O
              data = await getBojRankingFilter(sizeParam, languageIdParam);
            } else {
              // 필터 적용 X
              data = await getBojRanking(sizeParam);
            }

            if (data.length > 0) {
              setBojRankList([...data]);
              setNextRank(data[data?.length - 1]?.rank);
            } else {
              setBojRankList(null);
            }
          } else {
            // 그 이후
            let data;
            if (languageIdParam) {
              // 필터 적용 O
              if (bojRankList) {
                const userId = bojRankList[bojRankList?.length - 1]?.userId;
                const score = bojRankList[bojRankList?.length - 1]?.score;

                data = await getBojRankingFilter(sizeParam, languageIdParam, nextRankParam, userId, score);
              }
            } else {
              // 필터 적용 X
              if (bojRankList) {
                const userId = bojRankList[bojRankList?.length - 1]?.userId;
                const score = bojRankList[bojRankList?.length - 1]?.score;

                data = await getBojRanking(sizeParam, nextRankParam, userId, score);
              }
            }

            if (data?.length == 0) {
              // 더이상 조회할 데이터 X
              setNoMore(true);
            } else {
              // 새로 추가될 배열
              let newArr = new Array();
              data?.map((el: any) => {
                newArr.push(el);
              });

              // 이전 배열
              let oldArr = new Array();
              bojRankList?.map((el) => {
                oldArr.push(el);
              });

              console.log('dataaaaa', data);
              if (data) {
                setNextRank(data[data?.length - 1]?.rank);
                setBojRankList([...oldArr, ...newArr]);
              }
            }
          }
        })();

        // 나의 백준 랭킹 가져오기
        if (isLogin) {
          (async () => {
            let data;
            if (languageIdParam) {
              data = await getMyBojRanking(languageIdParam);
            } else {
              data = await getMyBojRanking();
            }

            if (data?.data?.userId != null) setMyBojRank(data?.data);
            else {
              setMyBojRank(null);
            }
          })();
        }
      }
    } finally {
      // console.log('finally');
    }
  };

  // 전체 랭킹 fixed event + Scroll event
  const allRef = useRef<any>();
  const wrapperRef = useRef<any>();
  const headerRef = useRef<any>();

  const handleScroll = (event: any) => {
    const el = document.querySelector('.my-rank');
    if (el instanceof HTMLElement) {
      const target = el?.offsetHeight + el.clientHeight;
      const scrollPoint = event.currentTarget.scrollTop;

      if (scrollPoint > target) {
        allRef.current.style.position = 'fixed';
        allRef.current.style.top = '48px';
        wrapperRef.current.style.zIndex = '4';
        headerRef.current.style.zIndex = '5';
      } else {
        allRef.current.style.position = '';
        allRef.current.style.top = '';
        wrapperRef.current.style.zIndex = '';
        headerRef.current.style.zIndex = '2';
      }
    }
  };

  if (!splashState) {
    return <Splash />;
  } else if (openProfile) {
    return (
      <Profile
        curRank={curRank}
        id={clickedUserId.toString()}
        my={clickMy}
        back={() => {
          setOpenProfile(false);
          setClickMy(false);
          setClickedUserId(0);
        }}
      ></Profile>
    );
  } else {
    return (
      <>
        {!searchClick && (
          <Header ref={headerRef}>
            {/* 로고 */}
            <div
              className="logo-box"
              onClick={() => {
                window.location.href = '/';
              }}
            >
              <LogoIcon />
            </div>
            {/* 아이콘 */}

            <div className="header-right">
              <div className="icon-box" onClick={() => setSearchClick(true)}>
                <RSearchIcon />
              </div>

              {!noScroll && (
                <div className="filter-box">
                  <FilterIcon onClick={() => setOpenFilter(true)} />
                </div>
              )}

              <div className="setting-box" onClick={() => setOpenSetting(true)}>
                <SettingIcon />
              </div>
            </div>
          </Header>
        )}
        <Wrapper searchClick={searchClick} onScroll={handleScroll} ref={wrapperRef}>
          {!searchClick ? (
            <>
              <RankMenu2 curRank={curRank} onChangeCurRank={onChangeCurRank} setNoScroll={setNoScroll} />
              <div className="content-wrapper">
                {selectedOption && (
                  <div className="option-box">
                    <FilterOption
                      item={selectedOption}
                      isInMain={true}
                      getRankList={getRankList}
                      size={size}
                      setSelectedOption={setSelectedOption}
                    />
                  </div>
                )}

                {!isLogin ? (
                  <div className="my-rank">
                    <p>나의 랭킹</p>
                    <NoAccount curRank={curRank} onClick={onClickNoUser} />
                  </div>
                ) : myGitRank && curRank == 0 ? (
                  <div className="my-rank">
                    <p>나의 랭킹</p>
                    <div onClick={() => setClickMy(true)}>
                      <MainUserItem curRank={curRank} item={myGitRank} />
                    </div>
                  </div>
                ) : myBojRank && curRank == 1 ? (
                  <div className="my-rank">
                    <p>나의 랭킹</p>
                    <div onClick={() => setClickMy(true)}>
                      <MainUserItem curRank={curRank} item={myBojRank} />
                    </div>
                  </div>
                ) : isLogin && curRank == 1 && !selectedOption ? (
                  <div className="my-rank">
                    <p>나의 랭킹</p>
                    <NoAccount curRank={curRank} onClick={onClickNoUser} />
                  </div>
                ) : (
                  <div className="my-rank">
                    <p>나의 랭킹</p>
                    <NoAccount curRank={3} onClick={onClickNoUser} />
                  </div>
                )}
                <div className="all-rank">
                  <p ref={allRef}>전체 랭킹</p>
                  <ul className="rank-list">
                    {curRank == 0
                      ? gitRankList &&
                        gitRankList?.map((el, idx) => (
                          <li
                            key={idx}
                            onClick={() => {
                              goProfile(el.userId);
                            }}
                          >
                            <MainOtherItem curRank={curRank} item={el} />
                          </li>
                        ))
                      : bojRankList &&
                        bojRankList?.map((el, idx) => {
                          return (
                            <li
                              key={idx}
                              onClick={() => {
                                goProfile(el.userId);
                              }}
                            >
                              <MainOtherItem curRank={curRank} item={el} />
                            </li>
                          );
                        })}
                    {curRank == 0 && gitRankList == null && <NoAccount curRank={2} />}
                    {curRank == 1 && bojRankList == null && <NoAccount curRank={2} />}
                    {loading && <Spinner />}
                    <div ref={ref} className="observer-box"></div>
                  </ul>
                </div>
              </div>
            </>
          ) : (
            <div className="search-content-wrapper">
              <SearchBar
                setNoScroll={setNoScroll}
                curRank={curRank}
                setGitRankList={setGitRankList}
                setBojRankList={setBojRankList}
                getRankList={getRankList}
                size={size}
                setSearchClick={setSearchClick}
                setSelectedOption={setSelectedOption}
              />
            </div>
          )}
        </Wrapper>
        {/* {openSelect && <RankMenuSelectModal onClick={() => setOpenSelect(false)} onChangeCurRank={onChangeCurRank} />} */}
        {openLogin && <LoginModal onClick={() => setOpenLogin(false)} />}
        {opeBoj && <BojModal onClick={() => setOpenBoj(false)} />}
        {openFilter && (
          <FilterModal
            onClick={() => setOpenFilter(false)}
            curRank={curRank}
            size={size}
            nextRank={nextRank}
            getRankList={getRankList}
            setIsLangId={setIsLangId}
            setSelectedOption={setSelectedOption}
            selectedOption={selectedOption}
          />
        )}
        {openSetting && <SettingModal onClick={() => setOpenSetting(false)} />}
      </>
    );
  }
};

export default Main;
