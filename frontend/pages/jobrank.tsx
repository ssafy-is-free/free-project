import RankMenu from '@/components/common/RankMenu';
import { useEffect, useState } from 'react';
import styled from 'styled-components';
import TimeIcon from '../public/Icon/TimeIcon.svg';
import JobUserItem from '@/components/rank/JobUserItem';
import { resultInformation, resultMyInformation } from '@/components/rank/IRank';
import MainOtherItem from '@/components/rank/MainOtherItem';
import RankMenuSelectModal from '@/components/common/RankMenuSelectModal';
import { getBojRanking, getGithubRanking, getMyGitRanking, getPostingsAllUsers } from './api/jobRankAxios';
import Chart from 'react-apexcharts';
import CommitIcon from '../public/Icon/CommitIcon.svg';
import RepoIcon from '../public/Icon/RepoIcon.svg';
import StarIcon from '../public/Icon/StarIcon.svg';
import { Doughnut } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';

const Wrapper = styled.div<{ info: number }>`
  width: 100vw;
  height: 100vh;
  background-color: ${(props) => props.theme.bgWhite};
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  z-index: 4;
  overflow-y: scroll;

  .job-info-box {
    background-color: ${(props) => props.theme.primary};
    width: 100%;
    display: inline-block;
    padding: 16px 32px;
    display: flex;
    flex-direction: column;
    position: relative;

    .job-info {
      display: flex;
      flex-direction: column;
      color: ${(props) => props.theme.fontWhite};
      margin-top: 8px;
      margin-bottom: 32px;

      .posting-name {
        margin-bottom: 8px;
        font-size: 14px;
      }

      .enterprise-name {
        font-weight: bold;
        font-size: 20px;
        margin-bottom: 8px;
      }

      .period {
        display: flex;
        align-items: center;
        font-size: 14px;

        p {
          margin-left: 8px;
        }
      }
    }
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

    .space {
      border-radius: 8px;
      height: 56px;
      padding: 0px 14px;
      width: 100%;
    }
  }

  .chart-box {
    width: 100%;
    background-color: ${(props) => props.theme.bgWhite};
    border-radius: 8px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    .info-box {
      display: flex;
      width: 100%;
      margin-bottom: 16px;

      .commit-box {
        background-color: ${(props) => props.theme.bgWhite};
        border: 1px solid ${(props) => props.theme.secondary};
        width: 45%;
        margin-bottom: 8px;
        margin-right: 8px;
        display: flex;
        flex-direction: column;
        justify-content: end;
        padding: 14px 16px;
        border-radius: 16px;
        position: relative;

        .info-label {
          font-size: 12px;
          color: ${(props) => props.theme.fontDarkGray};
        }

        .data {
          font-size: 20px;
          font-weight: bold;
          color: ${(props) => props.theme.fontBlack};
          margin-bottom: 4px;
        }
      }

      .info-sub-box {
        display: flex;
        flex-direction: column;
        width: 55%;

        .star-box,
        .repo-box {
          background-color: ${(props) => props.theme.bgWhite};
          border: 1px solid ${(props) => props.theme.secondary};
          width: 100%;
          margin-bottom: 8px;
          display: flex;
          flex-direction: column;
          padding: 14px 16px;
          border-radius: 16px;
          position: relative;

          .info-label {
            font-size: 12px;
            color: ${(props) => props.theme.fontDarkGray};
          }

          .data {
            font-size: 20px;
            font-weight: bold;
            color: ${(props) => props.theme.fontBlack};
            margin-bottom: 4px;
          }
        }
      }

      .icon-box {
        width: 32px;
        height: 32px;
        position: absolute;
        top: 8px;
        right: 8px;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
      }
    }

    .label {
      font-weight: bold;
      margin-bottom: 16px;
      font-size: 16px;
      color: ${(props) => props.theme.fontBlack};
    }
  }

  .btn-box {
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
      /*    box-shadow: 0 0 5px #03e9f4,
                0 0 25px #03e9f4,
                0 0 50px #03e9f4,
                0 0 200px #03e9f4; */
      box-shadow: 0 0 5px #00000012, 0 0 10px #00000012, 0 0 15px #00000012, 0 0 20px #00000012;
    }
  }
`;

const JobRank = () => {
  ChartJS.register(ArcElement, Tooltip, Legend);

  const [curRank, setCurRank] = useState<number>(0);
  const [openSelect, setOpenSelect] = useState<boolean>(false);
  const onChangeCurRank = (el: number) => {
    setCurRank(el);
    setOpenSelect(false);
  };

  const [info, setInfo] = useState<number>(0); // 0 : 랭킹, 1 : 통계

  const [size, setSize] = useState<number>(20);
  const [nextRank, setNextRank] = useState<number>(1);

  // TODO : jobPostingIdParam 임시로 1처리
  const jobPostingIdParam = 1;

  const [userRankInfo, setUserRankInfo] = useState<resultMyInformation | null>(null);
  const [otherRankInfo, setOtherRankInfo] = useState<resultInformation | null>(null);

  // 나와 전체 사용자 정보
  const [myInfo, setMyInfo] = useState<{
    avatarUrl: string;
    commit: number;
    languages: {
      languageList: { name: string; percentage: number }[];
    };
    nickname: string;
    repositories: number;
    star: number;
  } | null>(null);

  const [otherInfo, setOtherInfo] = useState<{
    avatarUrl: string;
    commit: number;
    languages: {
      languageList: { name: string; percentage: number }[];
    };
    nickname: string;
    repositories: number;
    star: number;
  } | null>(null);

  // 차트
  const [series, setSeries] = useState<number[]>([]);
  const [labels, setLabels] = useState<string[]>([]);
  const options = {
    chart: {
      id: 'apexchart-example',
      // width: '80%',
    },
    labels: labels,
    legend: {
      show: false,
    },
    tooltip: {
      enabled: true,
    },
  };

  const data = {
    labels: labels,
    datasets: [
      {
        data: series,
        backgroundColor: ['#ff7f7f', '#65a3ff', '#63f672', '#d28fff', '#ffec74'],
      },
    ],
  };

  useEffect(() => {
    // TODO : 취업 상세정보 조회

    // 랭킹 정보 가져오기
    if (curRank == 0) {
      // 깃허브 정보 불러오기
      (async () => {
        let data = await getGithubRanking(size, jobPostingIdParam);

        setOtherRankInfo(data);
      })();

      // 내 깃허브 정보 불러오기
      (async () => {
        let data = await getMyGitRanking(jobPostingIdParam);

        setUserRankInfo(data);
      })();
    } else if (curRank == 1) {
      // 백준 정보 불러오기
      (async () => {
        let data = await getBojRanking(size, jobPostingIdParam);

        setOtherRankInfo(data);
      })();

      // 내 백준 정보 불러오기
    }

    // 전체 사용자 정보 가져오기
    (async () => {
      let data = await getPostingsAllUsers(jobPostingIdParam);
      setOtherInfo(data?.opponent);
      setMyInfo(data?.my);

      //TODO : 코드 더 깔끔하게 쓰는 법?
      let arr = data?.opponent.languages.languageList;
      let scores = new Array();
      let labels = new Array();
      arr?.map((el: any) => {
        scores.push(el.percentage);
        labels.push(el.name);
      });
      setSeries([...scores]);
      setLabels([...labels]);
    })();
  }, [curRank]);

  return (
    <>
      <Wrapper info={info}>
        <div className="job-info-box">
          <RankMenu curRank={curRank} onClick={() => setOpenSelect(true)} />
          <div className="job-info">
            <div className="posting-name">2023 상반기 신입/Junior 공개 채용 </div>
            <div className="enterprise-name">안랩</div>
            <div className="period">
              <TimeIcon />
              <p> 2023.03.31 14:00 ~ 2023.04.16 23:59</p>
            </div>
          </div>
          <JobUserItem curRank={curRank} item={userRankInfo} />
          {/* {userRankInfo && <JobUserItem curRank={curRank} item={userRankInfo} />} */}
        </div>
        <div className="all-rank">
          {info == 0 ? (
            <>
              <ul className="rank-list">
                {otherRankInfo &&
                  otherRankInfo.map((el, idx) => (
                    <li key={idx}>
                      <MainOtherItem curRank={curRank} item={el} selectedOption={null} />
                    </li>
                  ))}
              </ul>
            </>
          ) : (
            <div className="chart-box">
              <div className="info-box">
                <div className="commit-box">
                  {otherInfo?.commit && <span className="data">{Math.floor(otherInfo?.commit)}</span>}
                  <span className="info-label">Commits</span>
                  <div className="icon-box" style={{ backgroundColor: '#ff8a60' }}>
                    <CommitIcon />
                  </div>
                </div>
                <div className="info-sub-box">
                  <div className="star-box">
                    {otherInfo?.star && <span className="data">{Math.floor(otherInfo?.star)}</span>}
                    <span className="info-label">Stars</span>
                    <div className="icon-box" style={{ backgroundColor: '#ffdd60' }}>
                      <StarIcon />
                    </div>
                  </div>
                  <div className="repo-box">
                    {otherInfo?.repositories && <span className="data">{Math.floor(otherInfo?.repositories)}</span>}
                    <span className="info-label">Repositories</span>
                    <div className="icon-box" style={{ backgroundColor: '#60e7ff' }}>
                      <RepoIcon />
                    </div>
                  </div>
                </div>
              </div>
              <div className="label">Languages</div>
              {/* <Chart options={options} series={series} type="donut" /> */}
              <Doughnut data={data} />
            </div>
          )}
          <div className="space"></div>
        </div>
        <div className="btn-box">
          <button
            className="average-btn"
            onClick={() => {
              if (info == 0) setInfo(1);
              else setInfo(0);
            }}
          >
            {info == 0 ? '지원자 평균 보러가기' : '지원자 랭킹 보러가기'}
          </button>
        </div>
      </Wrapper>
      {openSelect && <RankMenuSelectModal onClick={() => setOpenSelect(false)} onChangeCurRank={onChangeCurRank} />}
    </>
  );
};

export default JobRank;
