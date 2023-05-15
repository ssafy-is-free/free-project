import { useEffect, useRef, useState } from 'react';
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
import { useInView } from 'react-intersection-observer';
import { logout, setNew } from '@/redux/authSlice';
import FilterOption from '@/components/rank/FilterOption';
import RSearchIcon from '../public/Icon/SearchingIcon.svg';
import SettingIcon from '../public/Icon/SettingIcon.svg';
import LogoIcon from '../public/Icon/LogoPrimaryHeader.svg';
import TopIcon from '../public/Icon/TopIcon.svg';

import Profile from '@/components/profile/Profile';
import SettingModal from '@/components/rank/SettingModal';
import { setFilter } from '@/redux/rankSlice';
import RankLoading from '@/components/rank/RankLoading';

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
        z-index: 2;
      }
      .rank-list {
        li {
          margin-bottom: 8px;
        }

        .space {
          border-radius: 8px;
          height: 100px;
          padding: 0px 14px;
          width: 100%;
        }
      }
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

const TopBtn = styled.div`
  width: 32px;
  height: 32px;
  background-color: ${(props) => props.theme.lightGray};
  border-radius: 50%;
  position: fixed;
  z-index: 2;
  display: none;
  align-items: center;
  justify-content: center;
  right: 32px;
  bottom: 100px;
  box-shadow: 0px 0px 10px #00000026;
`;

const Main = () => {
  const dispatch = useDispatch();

  const accesstoken = localStorage.getItem('accessToken');

  // login 상태값 가져오기
  const isLogin = useSelector<RootState>((selector) => selector.authChecker.isLogin);
  // isnew 상태값 가져오기
  const isNew = useSelector<RootState>((selector) => selector.authChecker.isNew);

  // splash 상태관리
  const splashState = useSelector<RootState>((selector) => selector.splashChecker.check);

  // 백준 여부
  const isBoj = useSelector<RootState>((selector) => selector.authChecker.isBoj);

  // 옵션
  const filter = useSelector<RootState>((selector) => selector.rankChecker.filter);
  // TODO : 이렇게 따로따로 접근 가능한건가?
  const filterName = useSelector<RootState>((selector) => selector.rankChecker.filter?.name);
  const filterId = useSelector<RootState>((selector) => selector.rankChecker.filter?.languageId);

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
  const [ref, inView] = useInView({
    threshold: 0,
  });
  const [size, setSize] = useState<number>(20);
  const [nextRank, setNextRank] = useState<number>(0);
  const [inViewFirst, setInViewFirst] = useState<boolean>(false);
  const [noMore, setNoMore] = useState<boolean>(false);

  // 검색후 무한 스크롤 방지하기
  const [noScroll, setNoScroll] = useState<boolean>(false);

  /**
   * 깃허브 랭크 리스트, 백준 랭크 리스트
   */
  const [rankInfo, setRankInfo] = useState<resultInformation>(null);

  /**
   *  나의 깃허브 랭킹, 나의 백준 랭킹
   */
  const [myRankInfo, setMyRankInfo] = useState<resultMyInformation>(null);

  // 검색 아이콘 클릭 여부
  const [searchClick, setSearchClick] = useState<boolean>(false);

  // 로딩 여부
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    // TODO : 문제 => 가입후 다른 페이지 갔다가 뒤로가기 누르면 isNew가 false가아닌 true로뜬다...

    if (!accesstoken) {
      console.log('토큰 없음');
      dispatch(logout());
    }

    if (isLogin && isNew) {
      setOpenBoj(true);
      dispatch(setNew());
    }

    const timer = setTimeout(() => {
      dispatch(splashCheck());
    }, 1100);
    return () => {
      clearTimeout(timer);
      // 언마운트 시 filter null로 초기화
      dispatch(setFilter(null));
    };
  }, []);

  // 깃허브인지 백준인지 상태값 0: 깃허브, 1: 백준
  const [curRank, setCurRank] = useState<number>(0);
  const onChangeCurRank = (el: number) => {
    setNextRank(1); // 등수 초기화
    setCurRank(el); // curRank 갱신
    dispatch(setFilter(null)); // filter null값으로
  };

  // 상세정보 열기 ===================================================
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
  // ================================================================

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

  // 무한 스크롤 구현하기
  useEffect(() => {
    if (!noMore && !noScroll) {
      if (inView && !inViewFirst) {
        // inView가 true 일때만 실행한다.
        setInViewFirst(true);
      }

      if (inView && inViewFirst) {
        // inView가 true 일때만 실행한다.
        if (filter) {
          // 필터 걸린게 있다면?
          getRankList(nextRank, Number(filterId));
        } else {
          getRankList(nextRank);
        }
      }
    }
  }, [inView]);

  useEffect(() => {
    setNoMore(false);
    getRankList(0);
  }, [curRank]);

  // 랭킹 정보 가져오기
  const getRankList = (nextRankParam: number, languageIdParam?: number) => {
    setLoading(true);
    if (curRank == 0) {
      // 깃허브 정보 가져오기
      if (nextRankParam == 0) {
        // 처음 정보 불러올 때
        (async () => {
          let data = languageIdParam
            ? await getGithubRankingFilter(size, languageIdParam)
            : await getGithubRanking(size);

          if (data.status === 'SUCCESS') {
            if (data.data.ranks.length !== 0) {
              setRankInfo(data.data.ranks);
              setNextRank(data.data.ranks[data.data.ranks?.length - 1]?.rank);
            } else {
              setRankInfo(null);
            }
            setLoading(false);
          } else {
            alert(data.message);
          }
        })();
      } else {
        // 그 이후 정보 불러올 때

        (async () => {
          if (rankInfo) {
            const userId = rankInfo[rankInfo?.length - 1]?.userId;
            const score = rankInfo[rankInfo?.length - 1]?.score;

            let data = languageIdParam
              ? await getGithubRankingFilter(size, languageIdParam, nextRank, userId, score)
              : await getGithubRanking(size, nextRank, userId, score);

            if (data.status === 'SUCCESS') {
              if (data.data.ranks.length == 0) {
                // 더이상 조회할 데이터가 없음
                setNoMore(true);
              } else {
                setRankInfo([...rankInfo, ...data.data.ranks]);
                setNextRank(data.data.ranks[data.data.ranks?.length - 1]?.rank);
              }
              setLoading(false);
            } else {
              alert(data.message);
            }
          }
        })();
      }

      // 내 깃허브 정보 가져오기
      if (isLogin) {
        (async () => {
          let data = languageIdParam ? await getMyGitRanking(languageIdParam) : await getMyGitRanking();

          if (data.status === 'SUCCESS') {
            if (data.data?.githubRankingCover) setMyRankInfo(data.data?.githubRankingCover);
            else setMyRankInfo(null);
            setLoading(false);
          } else {
            alert(data.message);
          }
        })();
      }
    } else {
      // 백준 정보 가져오기
      if (nextRankParam == 0) {
        // 처음 정보 불러올 때
        (async () => {
          let data = languageIdParam ? await getBojRankingFilter(size, languageIdParam) : await getBojRanking(size);

          if (data.status === 'SUCCESS') {
            if (data?.data.length !== 0) {
              setRankInfo(data?.data);
              setNextRank(data?.data[data?.data?.length - 1]?.rank);
            } else {
              setRankInfo(null);
            }
            setLoading(false);
          } else {
            alert(data.message);
          }
        })();
      } else {
        // 그 이후 정보 불러올 때
        (async () => {
          if (rankInfo) {
            const userId = rankInfo[rankInfo?.length - 1]?.userId;
            const score = rankInfo[rankInfo?.length - 1]?.score;

            let data = languageIdParam
              ? await getBojRankingFilter(size, languageIdParam, nextRank, userId, score)
              : await getBojRanking(size, nextRank, userId, score);

            if (data.status === 'SUCCESS') {
              if (data.data.length == 0) {
                // 더이상 조회할 데이터가 없음
                setNoMore(true);
              } else {
                setRankInfo([...rankInfo, ...data.data]);
                setNextRank(data.data[data.data?.length - 1]?.rank);
              }
              setLoading(false);
            } else {
              alert(data.message);
            }
          }
        })();
      }

      // 내 백준 정보 가져오기
      if (isLogin) {
        (async () => {
          let data = languageIdParam ? await getMyBojRanking(languageIdParam) : await getMyBojRanking();

          if (data.status === 'SUCCESS') {
            if (data?.data?.userId != null) setMyRankInfo(data?.data);
            else setMyRankInfo(null);
            setLoading(false);
          } else {
            alert(data.message);
          }
        })();
      }
    }
  };

  // 전체 랭킹 fixed event + Scroll event
  const allRef = useRef<HTMLDivElement | null>(null);
  const topRef = useRef<HTMLDivElement | null>(null);
  const wrapperRef = useRef<HTMLDivElement | null>(null);

  const handleScroll = (event: any) => {
    const el = document.querySelector('.my-rank');
    if (el instanceof HTMLElement && allRef.current && topRef.current) {
      const target = el?.offsetHeight + el.clientHeight;
      const scrollPoint = event.currentTarget.scrollTop;

      if (scrollPoint >= target) {
        allRef.current.style.position = 'sticky';
        allRef.current.style.top = '0px';
        topRef.current.style.display = 'flex';
      } else {
        allRef.current.style.position = '';
        allRef.current.style.top = '';
        topRef.current.style.display = 'none';
      }
    }
  };

  const scrollToTop = () => {
    if (wrapperRef.current) {
      wrapperRef.current.scrollTo({ top: 0, behavior: 'smooth' });
    }
  };

  if (!splashState) {
    return <Splash />;
  } else {
    return (
      <>
        {!searchClick && (
          <Header>
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
              <div
                className="icon-box"
                onClick={() => {
                  setSearchClick(true);
                  dispatch(setFilter(null));
                }}
              >
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
                {filter != null && (
                  <div className="option-box">
                    <FilterOption
                      item={{ languageId: Number(filterId), name: String(filterName) }}
                      isInMain={true}
                      getRankList={getRankList}
                      setNoMore={setNoMore}
                    />
                  </div>
                )}

                {loading ? (
                  <div className="my-rank">
                    <p>나의 랭킹</p>
                    <RankLoading />
                  </div>
                ) : !isLogin || !localStorage.getItem('accessToken') ? (
                  <div className="my-rank">
                    <p>나의 랭킹</p>
                    <NoAccount curRank={0} onClick={onClickNoUser} />
                  </div>
                ) : myRankInfo ? (
                  <div className="my-rank">
                    <p>나의 랭킹</p>
                    <div onClick={() => setClickMy(true)}>
                      <MainUserItem curRank={curRank} item={myRankInfo} />
                    </div>
                  </div>
                ) : curRank == 1 && !isBoj ? (
                  <div className="my-rank">
                    <p>나의 랭킹</p>
                    <NoAccount curRank={1} onClick={onClickNoUser} />
                  </div>
                ) : filter ? (
                  <div className="my-rank">
                    <p>나의 랭킹</p>
                    <NoAccount curRank={3} onClick={onClickNoUser} />
                  </div>
                ) : null}
                <div className="all-rank">
                  <p ref={allRef}>전체 랭킹</p>
                  <ul className="rank-list">
                    {rankInfo ? (
                      rankInfo?.map((el, idx) => (
                        <li
                          key={idx}
                          onClick={() => {
                            goProfile(el.userId);
                          }}
                        >
                          {loading ? <RankLoading /> : <MainOtherItem curRank={curRank} item={el} />}
                        </li>
                      ))
                    ) : (
                      <NoAccount curRank={2} />
                    )}
                    {/* {rankInfo == null && <NoAccount curRank={2} />} */}
                    <div className="space" ref={ref}></div>
                  </ul>
                </div>
              </div>
            </>
          ) : (
            <SearchBar
              setNoScroll={setNoScroll}
              setInViewFirst={setInViewFirst}
              curRank={curRank}
              getRankList={getRankList}
              setRankInfo={setRankInfo}
              setSearchClick={setSearchClick}
              setNoMore={setNoMore}
              setMyRankInfo={setMyRankInfo}
              setLoading={setLoading}
            />
          )}
        </Wrapper>
        <TopBtn ref={topRef} onClick={scrollToTop}>
          <TopIcon />
        </TopBtn>
        {openLogin && <LoginModal onClick={() => setOpenLogin(false)} />}
        {opeBoj && <BojModal onClick={() => setOpenBoj(false)} />}
        {openFilter && (
          <FilterModal
            onClick={() => setOpenFilter(false)}
            curRank={curRank}
            getRankList={getRankList}
            setNoMore={setNoMore}
          />
        )}
        {openSetting && <SettingModal onClick={() => setOpenSetting(false)} />}
        {openProfile && (
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
        )}
      </>
    );
  }
};

export default Main;
