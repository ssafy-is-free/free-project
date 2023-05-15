import styled from 'styled-components';
import { IOtherInfoProps, applicantBojInfoType, applicantGitInfoType } from './IJobrank';
import CommitIcon from '../../public/Icon/CommitIcon.svg';
import RepoIcon from '../../public/Icon/RepoIcon.svg';
import StarIcon from '../../public/Icon/StarIcon.svg';
import PassIcon from '../../public/Icon/PassIcon.svg';
import FailIcon from '../../public/Icon/FailIcon.svg';
import SendIcon from '../../public/Icon/SendIcon.svg';
import TryfailIcon from '../../public/Icon/TryfailIcon.svg';
import { useEffect, useState } from 'react';
import { getPostingsAllBojUsers, getPostingsAllGitUsers } from '@/pages/api/jobRankAxios';
import OtherInfoLoading from './OtherInfoLoading';

const Wrapper = styled.div`
  display: flex;
  width: 100%;
  margin-bottom: 48px;

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
      font-size: 14px;
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
        font-size: 14px;
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

  .boj-info-box {
    display: flex;
    flex-direction: column;
    width: 100%;

    .boj-info-top,
    .boj-info-bottom {
      display: flex;
      justify-content: space-around;
      width: 100%;

      > div {
        width: 48%;
        height: 100px;
        background-color: ${(props) => props.theme.bgWhite};
        border: 1px solid ${(props) => props.theme.secondary};
        margin-bottom: 8px;
        display: flex;
        flex-direction: column;
        justify-content: end;
        padding: 14px 16px;
        border-radius: 16px;
        position: relative;

        .info-label {
          font-size: 14px;
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
  }
`;

const OtherInfo = (props: IOtherInfoProps) => {
  // 나와 전체 사용자 정보
  const [otherGitInfo, setGitOtherInfo] = useState<applicantGitInfoType>(null);
  const [otherBojInfo, setBojOtherInfo] = useState<applicantBojInfoType>(null);

  // loading
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    // 전체 사용자 정보 가져오기
    (async () => {
      setLoading(true);
      if (props.curRank == 0) {
        let data = await getPostingsAllGitUsers(props.jobPostingIdParam);

        if (data.status === 'SUCCESS') {
          setGitOtherInfo(data?.data.opponent);
          setTimeout(() => setLoading(false), 500);
        } else {
          // alert(data.message);
        }
      } else {
        let data = await getPostingsAllBojUsers(props.jobPostingIdParam);

        if (data.status === 'SUCCESS') {
          setBojOtherInfo(data?.data.other);
          setTimeout(() => setLoading(false), 500);
        } else {
          alert(data.message);
        }
      }
    })();
  }, [props.curRank]);

  return (
    <Wrapper>
      {loading ? (
        <OtherInfoLoading />
      ) : props.curRank == 0 ? (
        <>
          <div className="commit-box">
            {otherGitInfo?.commit && <span className="data">{otherGitInfo?.commit}</span>}
            <span className="info-label">커밋</span>
            <div className="icon-box" style={{ backgroundColor: '#ff8a60' }}>
              <CommitIcon />
            </div>
          </div>
          <div className="info-sub-box">
            <div className="star-box">
              {otherGitInfo?.star && <span className="data">{otherGitInfo?.star}</span>}
              <span className="info-label">스타</span>
              <div className="icon-box" style={{ backgroundColor: '#ffdd60' }}>
                <StarIcon />
              </div>
            </div>
            <div className="repo-box">
              {otherGitInfo?.repositories && <span className="data">{otherGitInfo?.repositories}</span>}
              <span className="info-label">레포지토리</span>
              <div className="icon-box" style={{ backgroundColor: '#60e7ff' }}>
                <RepoIcon />
              </div>
            </div>
          </div>
        </>
      ) : (
        <>
          <div className="boj-info-box">
            <div className="boj-info-top">
              <div className="pass-box">
                {otherBojInfo?.pass && <span className="data">{otherBojInfo?.pass}</span>}
                <span className="info-label">맞은 문제</span>
                <div className="icon-box" style={{ backgroundColor: '#90ff60' }}>
                  <PassIcon />
                </div>
              </div>
              <div className="fail-box">
                {otherBojInfo?.fail && <span className="data">{otherBojInfo?.fail}</span>}
                <span className="info-label">틀린 문제</span>
                <div className="icon-box" style={{ backgroundColor: '#ff8a60' }}>
                  <FailIcon />
                </div>
              </div>
            </div>
            <div className="boj-info-bottom">
              <div className="submit-box">
                {otherBojInfo?.submit && <span className="data">{otherBojInfo?.submit}</span>}
                <span className="info-label">제출</span>
                <div className="icon-box" style={{ backgroundColor: '#60e7ff' }}>
                  <SendIcon />
                </div>
              </div>
              <div className="tryfail-box">
                {otherBojInfo?.tryFail && <span className="data">{otherBojInfo?.tryFail}</span>}
                <span className="info-label">
                  시도했지만 <br />
                  맞지 못한 문제
                </span>
                <div className="icon-box" style={{ backgroundColor: '#ffdd60' }}>
                  <TryfailIcon />
                </div>
              </div>
            </div>
          </div>
        </>
      )}
    </Wrapper>
  );
};

export default OtherInfo;
