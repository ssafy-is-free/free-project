import RankMenu from '@/components/common/RankMenu';
import { useEffect, useState } from 'react';
import styled from 'styled-components';
import JobUserItem from '@/components/jobrank/JobUserItem';
import { resultInformation, resultMyInformation } from '@/components/rank/IRank';
import MainOtherItem from '@/components/rank/MainOtherItem';
import RankMenuSelectModal from '@/components/common/RankMenuSelectModal';
import { getBojRanking, getGithubRanking, getMyBojRanking, getMyGitRanking } from './api/jobRankAxios';
import ChartJobrank from '@/components/jobrank/ChartJobrank';
import OtherInfo from '@/components/jobrank/OtherInfo';
import JobInfo from '@/components/jobrank/JobInfo';
import SubMenu from '@/components/jobrank/Submenu';
import CompareBox from '@/components/jobrank/CompareBox';
import CompareUserBox from '@/components/jobrank/CompareUserBox';
import BackIcon from '../public/Icon/BackIcon.svg';
import { useInView } from 'react-intersection-observer';
import { useRouter } from 'next/router';
import RankingIcon from '../public/Icon/RankingIcon.svg';
import PieIcon from '../public/Icon/PieIcon.svg';
import RankLoading from '@/components/rank/RankLoading';
import JobUerItemLoading from '@/components/jobrank/JobUserItemLoading';
import BojModal from '@/components/login/BojModal';
import { useSelector } from 'react-redux';
import { RootState } from '@/redux';

const Wrapper = styled.div<{ info: number; submenu: number; clickBtn: boolean }>`
  width: 100vw;
  height: 100vh;
  background-color: ${(props) => props.theme.bgWhite};
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  overflow-y: scroll;

  .button {
    width: ${(props) => (props.clickBtn ? '80%' : '48px')};
    height: 48px;
    background-color: ${(props) => props.theme.primary};
    border-radius: ${(props) => (props.clickBtn ? '14px' : '50%')};
    position: fixed;
    z-index: 2;
    right: 32px;
    bottom: 100px;
    box-shadow: 0px 0px 10px #00000026;
    transition: 0.5s;
    color: ${(props) => props.theme.fontWhite};
    display: flex;
    align-items: center;
    justify-content: center;

    .icon-box {
      display: flex;
      align-items: center;
      position: absolute;
      right: 16px;
    }
    p {
      opacity: ${(props) => (props.clickBtn ? '1' : '0')};
      visibility: ${(props) => (props.clickBtn ? 'visibile' : 'collapse')};
      transition-delay: ${(props) => (props.clickBtn ? '0.3s;' : 'none')};
    }
  }

  .job-info-box {
    background-color: ${(props) => props.theme.primary};
    width: 100%;
    display: inline-block;
    padding: 16px 32px;
    display: flex;
    flex-direction: column;
    position: relative;
  }

  .all-rank {
    width: 100%;
    padding: 48px 2rem 1rem;
    /* padding: ${(props) => (props.info == 0 ? '48px 2rem 1rem' : '1rem 2rem')}; */
    position: relative;

    .rank-list {
      li {
        margin-bottom: 8px;
      }
    }

    .back-icon {
      position: absolute;
      top: 32px;
      left: 32px;
    }
    .space {
      border-radius: 8px;
      height: 56px;
      padding: 0px 14px;
      width: 100%;
    }
  }

  .content-wrapper {
    .chart-box {
      width: 100%;
      background-color: ${(props) => props.theme.bgWhite};
      border-radius: 8px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      margin-top: 24px;

      .label {
        width: 100%;
        font-weight: bold;
        margin-bottom: 16px;
        font-size: 20px;
        color: ${(props) => props.theme.fontBlack};
      }
    }
  }

  /* .btn-box {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    position: fixed;
    bottom: 0px;
    padding: 24px 0px 24px;

    .average-btn {
      color: ${(props) => props.theme.fontWhite};
      background-color: ${(props) => props.theme.primary};
      font-size: 16px;
      border-radius: 14px;
      padding: 14px 20%;
      box-shadow: 0 0 5px #00000012, 0 0 10px #00000012, 0 0 15px #00000012, 0 0 20px #00000012;
    }
  } */
`;

const JobRank = () => {
  const [curRank, setCurRank] = useState<number>(0);
  const [openSelect, setOpenSelect] = useState<boolean>(false);
  const onChangeCurRank = (el: number) => {
    setNextRank(1);
    setCurRank(el);
    setOpenSelect(false);
  };

  // 유저 비교 컴포넌트 보이기 여부
  const [openCompare, setOpenCompare] = useState<boolean>(false);
  const [userId, setUserId] = useState<number>(0);

  // 0 : 랭킹, 1 : 통계
  const [info, setInfo] = useState<number>(0);

  // 무한 스크롤 구현하기
  const [ref, inView, entry] = useInView({
    threshold: 0,
  });
  const [size, setSize] = useState<number>(20);
  const [nextRank, setNextRank] = useState<number>(1);
  // 더이상 데이터가 없을 때
  const [noMore, setNoMore] = useState<boolean>(false);
  // inView 처음 봤을 떄
  const [inViewFirst, setInViewFirst] = useState<boolean>(true);

  const router = useRouter();
  const [jobPostingIdParam, setJobPostingIdParam] = useState<number>(Number(router.query.postingId));

  // 랭킹 정보
  const [userRankInfo, setUserRankInfo] = useState<resultMyInformation | null>(null);
  const [otherRankInfo, setOtherRankInfo] = useState<resultInformation | null>(null);

  // 서브메뉴
  const [submenu, setSubmenu] = useState<number>(0);

  // 로딩
  const [loading, setLoading] = useState<boolean>(true);

  // 모달 열기
  const [openBoj, setOpenBoj] = useState<boolean>(false);

  // 백준 여부
  const isBoj = useSelector<RootState>((selector) => selector.authChecker.isBoj);

  // 무한 스크롤 구현하기
  useEffect(() => {
    if (!noMore) {
      if (inView && inViewFirst) {
        setInViewFirst(false);
      } else if (inView && !inViewFirst) {
        onGetRankData(nextRank);
      }
    }
  }, [inView]);

  useEffect(() => {
    setNoMore(false);
    onGetRankData(1);
  }, [curRank]);

  const onGetRankData = (nextRankParam: number) => {
    setLoading(true);
    // 랭킹 정보 가져오기
    if (curRank == 0) {
      // 처음 가져올 때(nextRank가 1인 상태)
      if (nextRankParam == 1) {
        // 깃허브 정보 불러오기
        (async () => {
          let data = await getGithubRanking(size, jobPostingIdParam);

          if (data.status === 'SUCCESS') {
            setOtherRankInfo(data.data?.ranks);
            setNextRank(data.data?.ranks[data.data?.ranks?.length - 1].rank);
            setLoading(false);
          } else {
            alert(data.message);
          }
        })();
      } else {
        // 깃허브 정보 불러오기
        (async () => {
          if (otherRankInfo) {
            const userId = otherRankInfo[otherRankInfo?.length - 1]?.userId;
            const score = otherRankInfo[otherRankInfo?.length - 1]?.score;

            let data = await getGithubRanking(size, jobPostingIdParam, nextRank, userId, score);

            if (data.status === 'SUCCESS') {
              if (data.data?.ranks.length == 0) {
                // 더이상 조회할 데이터가 없음
                setNoMore(true);
              } else {
                setOtherRankInfo([...otherRankInfo, ...data.data?.ranks]);
              }
              setLoading(false);
            } else {
              alert(data.message);
            }
          }
        })();
      }

      // 내 깃허브 정보 불러오기
      (async () => {
        let data = await getMyGitRanking(jobPostingIdParam);
        if (data.status === 'SUCCESS') {
          setUserRankInfo(data.data?.githubRankingCover);
          setLoading(false);
        } else {
          alert(data.message);
        }
      })();
    } else if (curRank == 1) {
      // 백준 정보 불러오기
      if (nextRankParam == 1) {
        (async () => {
          let data = await getBojRanking(size, jobPostingIdParam);

          if (data.status === 'SUCCESS') {
            setOtherRankInfo(data.data);
            setNextRank(data.data[data.data?.length - 1]?.rank);
            setLoading(false);
          } else {
            alert(data.message);
          }
        })();
      } else {
        (async () => {
          if (otherRankInfo) {
            const userId = otherRankInfo[otherRankInfo?.length - 1]?.userId;
            const score = otherRankInfo[otherRankInfo?.length - 1]?.score;

            let data = await getBojRanking(size, jobPostingIdParam, nextRank, userId, score);

            if (data.status === 'SUCCESS') {
              if (data.data.length == 0) {
                // 더이상 조회할 데이터가 없음
                setNoMore(true);
              } else {
                setOtherRankInfo([...otherRankInfo, ...data.data]);
              }
              setLoading(false);
            } else {
              alert(data.message);
            }
          }
        })();
      }

      // 내 백준 정보 불러오기
      (async () => {
        let data = await getMyBojRanking(jobPostingIdParam);

        if (data.status === 'SUCCESS') {
          setUserRankInfo(data.data);
          setLoading(false);
        } else {
          alert(data.message);
        }
      })();
    }
  };

  const onUserCompare = (userId: number) => {
    setUserId(userId);
    setOpenCompare(true);
  };

  // 버튼 클릭 시
  const [clickBtn, setClickBtn] = useState<boolean>(false);

  const onClickBtn = () => {
    setClickBtn(!clickBtn);

    if (clickBtn) {
      if (info == 0) {
        if (curRank == 1 && !isBoj) {
          alert('백준 로그인 후 이용 가능합니다.');
        } else {
          setInfo(1);
          setOpenCompare(false);
          setSubmenu(0);
        }
      } else {
        setInfo(0);
        setOpenCompare(false);
        setSubmenu(0);
      }
    }
  };

  return (
    <>
      <Wrapper info={info} submenu={submenu} clickBtn={clickBtn}>
        <div className="button" onClick={onClickBtn}>
          <p> {info == 0 ? '지원자 평균 보러가기' : '지원자 랭킹 보러가기'} </p>
          {!clickBtn && info == 0 ? (
            <div className="icon-box">
              <PieIcon />
            </div>
          ) : !clickBtn && info == 1 ? (
            <div className="icon-box">
              <RankingIcon />
            </div>
          ) : null}
        </div>
        <div className="job-info-box">
          <RankMenu curRank={curRank} onClick={() => setOpenSelect(true)} />
          <JobInfo
            curRank={curRank}
            companyName={String(router.query.companyName)}
            postingName={String(router.query.postingName)}
            startTime={String(router.query.startTime)}
            endTime={String(router.query.endTime)}
          />
        </div>
        <div className="all-rank">
          {info == 0 ? (
            <>
              {!openCompare ? (
                <ul className="rank-list">
                  {loading ? (
                    <JobUerItemLoading />
                  ) : userRankInfo ? (
                    <JobUserItem curRank={curRank} item={userRankInfo} />
                  ) : (
                    <JobUserItem curRank={curRank} item={null} />
                  )}
                  {otherRankInfo &&
                    otherRankInfo.map((el, idx) => (
                      <li
                        key={idx}
                        onClick={() => {
                          if (userRankInfo?.userId != el.userId) {
                            onUserCompare(el.userId);
                          }
                        }}
                      >
                        {loading ? <RankLoading /> : <MainOtherItem curRank={curRank} item={el} />}
                      </li>
                    ))}
                </ul>
              ) : (
                <>
                  <div
                    className="back-icon"
                    onClick={() => {
                      setOpenCompare(false);
                    }}
                  >
                    <BackIcon />
                  </div>
                  <CompareUserBox curRank={curRank} userId={userId} />
                </>
              )}
            </>
          ) : (
            <>
              <div className="content-wrapper">
                <SubMenu submenu={submenu} setSubmenu={setSubmenu} />
                {submenu == 0 ? (
                  <div className="chart-box">
                    <OtherInfo curRank={curRank} jobPostingIdParam={jobPostingIdParam} />
                    <div className="label">사용 언어</div>
                    <ChartJobrank curRank={curRank} jobPostingIdParam={jobPostingIdParam} target={1} />
                  </div>
                ) : (
                  <CompareBox curRank={curRank} jobPostingIdParam={jobPostingIdParam} />
                )}
              </div>
            </>
          )}
          <div className="space" ref={ref}></div>
        </div>
        {/* <div className="btn-box">
          <button
            className="average-btn"
            onClick={() => {
              if (info == 0) {
                setInfo(1);
                setOpenCompare(false);
                setSubmenu(0);
              } else {
                setInfo(0);
                setOpenCompare(false);
                setSubmenu(0);
              }
            }}
          >
            {info == 0 ? '지원자 평균 보러가기' : '지원자 랭킹 보러가기'}
          </button>
        </div> */}
      </Wrapper>
      {openSelect && (
        <RankMenuSelectModal
          onClick={() => setOpenSelect(false)}
          onChangeCurRank={onChangeCurRank}
          setOpenBoj={setOpenBoj}
        />
      )}
      {openBoj && <BojModal onClick={() => setOpenBoj(false)} />}
    </>
  );
};

export default JobRank;
