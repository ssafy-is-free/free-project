import RankMenu from '@/components/common/RankMenu';
import { useEffect, useState } from 'react';
import styled, { css } from 'styled-components';
import JobUserItem from '@/components/rank/JobUserItem';
import { resultInformation, resultMyInformation } from '@/components/rank/IRank';
import MainOtherItem from '@/components/rank/MainOtherItem';
import RankMenuSelectModal from '@/components/common/RankMenuSelectModal';
import {
  getBojRanking,
  getGithubRanking,
  getMyBojRanking,
  getMyGitRanking,
  getPostingsAllUsers,
} from './api/jobRankAxios';
import ChartJobrank from '@/components/jobrank/ChartJobrank';
import { applicantInfoType } from '@/components/jobrank/IJobrank';
import OtherInfo from '@/components/jobrank/OtherInfo';
import JobInfo from '@/components/jobrank/JobInfo';
import SubMenu from '@/components/jobrank/Submenu';
import CompareBox from '@/components/jobrank/CompareBox';

const Wrapper = styled.div<{ info: number; submenu: number }>`
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
      box-shadow: 0 0 5px #00000012, 0 0 10px #00000012, 0 0 15px #00000012, 0 0 20px #00000012;
    }
  }
`;

const JobRank = () => {
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

  // 서브메뉴
  const [submenu, setSubmenu] = useState<number>(0);

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
      (async () => {
        let data = await getMyBojRanking(jobPostingIdParam);
        setUserRankInfo(data);
      })();
    }
  }, [curRank]);

  return (
    <>
      <Wrapper info={info} submenu={submenu}>
        <div className="job-info-box">
          <RankMenu curRank={curRank} onClick={() => setOpenSelect(true)} />
          <JobInfo curRank={curRank} jobPostingIdParam={jobPostingIdParam} />
        </div>
        <div className="all-rank">
          {info == 0 ? (
            <>
              <ul className="rank-list">
                <JobUserItem curRank={curRank} item={userRankInfo} />
                {otherRankInfo &&
                  otherRankInfo.map((el, idx) => (
                    <li key={idx}>
                      <MainOtherItem curRank={curRank} item={el} selectedOption={null} />
                    </li>
                  ))}
              </ul>
            </>
          ) : (
            <>
              <div className="content-wrapper">
                <SubMenu submenu={submenu} setSubmenu={setSubmenu} />
                {submenu == 0 ? (
                  <div className="chart-box">
                    <OtherInfo curRank={curRank} jobPostingIdParam={jobPostingIdParam} />
                    <div className="label">Languages</div>
                    <ChartJobrank curRank={curRank} jobPostingIdParam={jobPostingIdParam} target={1} />
                  </div>
                ) : (
                  <CompareBox curRank={curRank} jobPostingIdParam={jobPostingIdParam} />
                )}
              </div>
            </>
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
