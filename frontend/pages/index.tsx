import { useRouter } from 'next/router';
import { useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import Splash from './splash';
import RankMenu from '@/components/common/RankMenu';
import SearchBar from '@/components/rank/RankSearchBar';
import FilterIcon from '../public/Icon/FilterIcon.svg';
import MainUserItem from '@/components/rank/MainUserItem';
import MainOtherItem from '@/components/rank/MainOtherItem';
import NoAccount from '@/components/rank/NoAccount';
import RankMenuSelectModal from '@/components/common/RankMenuSelectModal';
import LoginModal from '@/components/login/LoginModal';
import BojModal from '@/components/login/BojModal';
import FilterModal from '@/components/rank/FilterModal';
import { useDispatch, useSelector } from 'react-redux';
import { SplashState, splashCheck } from '@/redux/splashSlice';
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

const Wrapper = styled.div`
  width: 100vw;
  height: 100vh;
  background-color: ${(props) => props.theme.primary};
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;

  .content-wrapper {
    /* position: relative;   */
    position: absolute;
    z-index: 1;
    bottom: 0;
    /* margin-top: 32px; */
    /* padding: 72px 32px 32px; */
    padding: 3rem 2rem 2rem;
    width: 100%;
    /* height: 672px; */
    height: 83vh;
    background-color: ${(props) => props.theme.bgWhite};
    border-radius: 50px 50px 0px 0px;
    display: flex;
    flex-direction: column;

    .filter-box {
      position: absolute;
      top: 2rem;
      right: 2rem;
      cursor: pointer;
    }

    .my-rank {
      p {
        font-size: 20px;
        font-weight: bold;
        margin-bottom: 16px;
      }
      margin-bottom: 32px;
    }

    .all-rank-label {
      font-size: 20px;
      font-weight: bold;
      margin-bottom: 16px;
    }

    .rank-list {
      /* p {
        font-size: 20px;
        font-weight: bold;
        margin-bottom: 16px;
      } */

      overflow-y: scroll;

      li {
        margin-bottom: 8px;
      }
    }
  }
`;

const Main = () => {
  // login 상태값 가져오기
  const isLogin = useSelector<RootState>((selector) => selector.authChecker.isLogin);
  const [login, setLogin] = useState<boolean>(false);
  // isnew 상태값 가져오기
  const isNew = useSelector<RootState>((selector) => selector.authChecker.isNew);

  // splash 상태관리
  const splashState = useSelector<RootState>((selector) => selector.splashChecker.check);
  const dispatch = useDispatch();
  // splash screen 적용하기
  // const [splash, setSplash] = useState<boolean>(false);
  const [splash, setSplash] = useState<boolean>(false);

  // 랭크 menu select 모달 열기
  const [openSelect, setOpenSelect] = useState<boolean>(false);
  // 로그인 모달 열기
  const [openLogin, setOpenLogin] = useState<boolean>(false);
  // 백준 모달 열기
  const [opeBoj, setOpenBoj] = useState<boolean>(false);
  // filter 모달 열기
  const [openFilter, setOpenFilter] = useState<boolean>(false);

  // 무한 스크롤 구현하기
  const [ref, inView] = useInView();
  const [inViewFirst, setInViewFirst] = useState<boolean>(false);
  const [loading, setLoading] = useState<boolean>(false);
  const [size, setSize] = useState<number>(5);
  const [nextRank, setNextRank] = useState<number>(1);
  const [isLangId, setIsLangId] = useState<number>(0); // 필터링 적용한 경우 무한스크롤 분기위해 추가
  const [noMore, setNoMore] = useState<boolean>(false);

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

  /**
   * splash check useEffect
   */
  useEffect(() => {
    const splashStorage = localStorage.getItem('splash');
    if (splashStorage) {
      setSplash(true);
    }

    if (isNew) {
      setOpenBoj(true);
    }

    const accessToken = localStorage.getItem('accessToken');
    if (accessToken) {
      setLogin(true);
    }

    const timer = setTimeout(() => {
      // setSplash(true);
      dispatch(splashCheck());
      localStorage.setItem('splash', 'true');
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

  // nouserItem 클릭시
  const onClickNoUser = () => {
    const accessToken = localStorage.getItem('accessToken');

    if (curRank == 0) {
      // 깃허브 페이지 일 때
      // 로그인 모달 띄우기
      setOpenLogin(true);
    } else if (curRank == 1) {
      // 백준 페이지 일 때

      if (accessToken) {
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
    if (!noMore) {
      if (inView && !inViewFirst) {
        // inView가 true 일때만 실행한다.
        setInViewFirst(true);
      }

      if (inView && inViewFirst) {
        // inView가 true 일때만 실행한다.
        setNextRank((prev) => prev + size);
      }
    }
  }, [inView]);

  useEffect(() => {
    if (isLangId > 0) {
      getRankList(size, nextRank);
    } else {
      getRankList(size, nextRank, isLangId);
    }
  }, [nextRank, curRank]);

  useEffect(() => {
    setNoMore(false);
  }, [curRank]);

  useEffect(() => {}, [gitRankList]);

  // 랭킹 정보 가져오기
  const getRankList = (sizeParam: number, nextRankParam: number, languageIdParam?: number) => {
    const accessToken = localStorage.getItem('accessToken');
    try {
      if (curRank == 0) {
        // 깃허브 랭크 가져오기 => rank 갱신할 때마다 rank값 수정해서 보내기

        (async () => {
          let data;

          if (nextRank == 1) {
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

                data = await getGithubRankingFilter(sizeParam, nextRankParam - 1, languageIdParam, userId, score);
              }
            } else {
              // 필터 적용 X
              if (gitRankList) {
                setIsLangId(0);

                const userId = gitRankList[gitRankList?.length - 1]?.userId;
                const score = gitRankList[gitRankList?.length - 1]?.score;

                data = await getGithubRanking(sizeParam, nextRankParam - 1, userId, score);
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

              setGitRankList([...oldArr, ...newArr]);
            }
          }
        })();

        // 나의 깃허브 랭킹 가져오기
        if (accessToken) {
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

          if (nextRank == 1) {
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

                data = await getBojRankingFilter(sizeParam, nextRankParam - 1, languageIdParam, userId, score);
              }
            } else {
              // 필터 적용 X
              if (bojRankList) {
                const userId = bojRankList[bojRankList?.length - 1]?.userId;
                const score = bojRankList[bojRankList?.length - 1]?.score;

                data = await getBojRanking(sizeParam, nextRankParam - 1, userId, score);
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

              setBojRankList([...oldArr, ...newArr]);
            }
          }
        })();

        // 나의 백준 랭킹 가져오기
        if (accessToken) {
          (async () => {
            let data;
            if (languageIdParam) {
              data = await getMyBojRanking(languageIdParam);
            } else {
              data = await getMyBojRanking();
            }

            if (data?.data) setMyBojRank(data?.data);
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

  if (!splash && !splashState) {
    // if (splashStorage) {
    return <Splash />;
  } else {
    return (
      <>
        <Wrapper>
          <RankMenu onClick={() => setOpenSelect(true)} curRank={curRank} />
          <SearchBar curRank={curRank} setGitRankList={setGitRankList} setBojRankList={setBojRankList} />
          <div className="content-wrapper">
            <div className="filter-box">
              <FilterIcon onClick={() => setOpenFilter(true)} />
            </div>
            {myGitRank ? (
              <div className="my-rank">
                <p>나의 랭킹</p>
                {myGitRank && curRank == 0 ? (
                  <MainUserItem curRank={curRank} item={myGitRank} />
                ) : myBojRank && curRank == 1 ? (
                  <MainUserItem curRank={curRank} item={myBojRank} />
                ) : null}
              </div>
            ) : !login ? (
              <div className="my-rank">
                <p>나의 랭킹</p>
                <NoAccount curRank={curRank} onClick={onClickNoUser} />
              </div>
            ) : null}
            <p className="all-rank-label">전체 랭킹</p>
            <ul className="rank-list">
              {curRank == 0
                ? gitRankList &&
                  gitRankList?.map((el, idx) => (
                    <li key={idx}>
                      <MainOtherItem curRank={curRank} item={el} />
                    </li>
                  ))
                : bojRankList &&
                  bojRankList?.map((el, idx) => {
                    return (
                      <li key={idx}>
                        <MainOtherItem curRank={curRank} item={el} />
                      </li>
                    );
                  })}
              {curRank == 0 && gitRankList == null && <NoAccount curRank={3} />}
              {curRank == 1 && bojRankList == null && <NoAccount curRank={3} />}
              {loading && <Spinner />}
              <div ref={ref}></div>
            </ul>
          </div>
        </Wrapper>
        {openSelect && <RankMenuSelectModal onClick={() => setOpenSelect(false)} onChangeCurRank={onChangeCurRank} />}
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
          />
        )}
      </>
    );
  }
};

export default Main;
