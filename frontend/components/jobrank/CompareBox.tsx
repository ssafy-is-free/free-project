import styled, { css } from 'styled-components';
import { ICompareBoxProps, applicantInfoType } from './IJobrank';
import { useEffect, useState } from 'react';
import UserDefaultIcon from '../../public/Icon/UserDefaultIcon.svg';
import { getPostingsAllUsers } from '@/pages/api/jobRankAxios';
import ChartJobrank from './ChartJobrank';

const Wrapper = styled.div`
  width: 100%;
  margin-top: 24px;

  .content-wrapper {
    border-radius: 16px;
    width: 100%;

    .content-top {
      width: 100%;
      display: flex;
      /* justify-content: space-around; */

      .content-top-left,
      .content-top-right {
        width: 50%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;

        .my-img {
          border-radius: 50%;
          width: 64px;
          height: 64px;
        }

        p {
          margin-top: 16px;
          font-weight: bold;
          font-size: 14px;
        }
      }
    }

    .content {
      margin-top: 24px;
      width: 100%;
      background-color: ${(props) => props.theme.secondary2};
      border-radius: 8px;
      display: flex;
      flex-direction: column;
      padding: 2rem 1rem;

      .content-box {
        width: 100%;
        display: flex;
        flex-direction: column;
        justify-content: center;

        .label {
          font-weight: bold;
          text-align: center;
          color: ${(props) => props.theme.fontBlack};
          margin-bottom: 8px;
        }
        .bar-box {
          display: flex;
          width: 100%;
          margin-bottom: 24px;
          .bar-left {
            width: 50%;
            display: flex;
            flex-direction: column;
            align-items: end;
            padding-right: 8px;

            .number-label {
              width: 100%;
              text-align: left;
              margin-bottom: 4px;
              color: ${(props) => props.theme.fontGray};
              font-size: 12px;
            }
            .bar {
              width: 100%;
              height: 16px;
              background-color: ${(props) => props.theme.secondary};
              border-radius: 16px;
              display: flex;
              justify-content: end;

              .data-bar {
                /* width: 60%; */
                height: 16px;
                background-color: ${(props) => props.theme.primary};
                border-radius: 16px;
              }
            }
          }

          .bar-right {
            width: 50%;
            display: flex;
            flex-direction: column;
            align-items: start;
            padding-left: 8px;
            display: flex;
            justify-content: start;

            .number-label {
              width: 100%;
              text-align: right;
              margin-bottom: 4px;
              color: ${(props) => props.theme.fontGray};
              font-size: 12px;
            }
            .bar {
              width: 100%;
              height: 16px;
              background-color: ${(props) => props.theme.secondary};
              border-radius: 16px;

              .data-bar {
                width: 60%;
                height: 16px;
                background-color: ${(props) => props.theme.primary};
                border-radius: 16px;
              }
            }
          }
        }

        .chart-box {
          width: 100%;
          display: flex;
          flex-direction: row;
          background-color: ${(props) => props.theme.secondary2};

          .chart-subbox {
            width: 50%;
            display: flex;
            justify-content: center;
          }
        }
      }
    }
  }
`;

const CompareBox = (props: ICompareBoxProps) => {
  // 나와 전체 사용자 정보
  const [otherInfo, setOtherInfo] = useState<applicantInfoType>(null);
  const [myInfo, setMyInfo] = useState<applicantInfoType>(null);

  const [commitWidth, setCommitWidth] = useState<{
    myWidth: number;
    otherWidth: number;
  } | null>(null);
  const [starWidth, setStarWidth] = useState<{
    myWidth: number;
    otherWidth: number;
  } | null>(null);
  const [repoWidth, setRepoWidth] = useState<{
    myWidth: number;
    otherWidth: number;
  } | null>(null);

  const calc = (myNumber: number, otherNumber: number) => {
    let number = Math.max(myNumber, otherNumber);
    let digit = 0;
    while (number != 0) {
      number = Math.floor(number / 10);
      digit++;
    }

    let standard = Math.max(myNumber, otherNumber) + 10 ** (digit - 1);

    const data = {
      myWidth: Math.floor((myNumber / standard) * 100),
      otherWidth: Math.floor((otherNumber / standard) * 100),
    };

    return data;
  };

  useEffect(() => {
    (async () => {
      let data = await getPostingsAllUsers(props.jobPostingIdParam);
      setOtherInfo(data?.opponent);
      setMyInfo(data?.my);

      setCommitWidth(calc(data?.my.commit, data?.opponent.commit));
      setStarWidth(calc(data?.my.star, data?.opponent.star));
      setRepoWidth(calc(data?.my.repositories, data?.opponent.repositories));
    })();
  }, [props.curRank]);

  return (
    <Wrapper>
      <div className="content-wrapper">
        <div className="content-top">
          <div className="content-top-left">
            <img src={myInfo?.avatarUrl} className="my-img" />
            <p>{myInfo?.nickname}</p>
          </div>
          <div className="content-top-right">
            <UserDefaultIcon />
            <p>전체 사용자</p>
          </div>
        </div>
        <div className="content">
          <div className="content-box">
            <span className="label">Commits</span>
            <div className="bar-box">
              <div className="bar-left">
                <p className="number-label">{myInfo?.commit}</p>
                <div className="bar">
                  <div className="data-bar" style={{ width: `${commitWidth?.myWidth}%` }}></div>
                </div>
              </div>
              <div className="bar-right">
                <p className="number-label">{otherInfo?.commit}</p>
                <div className="bar">
                  <div className="data-bar" style={{ width: `${commitWidth?.otherWidth}%` }}></div>
                </div>
              </div>
            </div>
            <span className="label">Stars</span>
            <div className="bar-box">
              <div className="bar-left">
                <p className="number-label">{myInfo?.star}</p>
                <div className="bar">
                  <div className="data-bar" style={{ width: `${starWidth?.myWidth}%` }}></div>
                </div>
              </div>
              <div className="bar-right">
                <p className="number-label">{otherInfo?.star}</p>
                <div className="bar">
                  <div className="data-bar" style={{ width: `${starWidth?.otherWidth}%` }}></div>
                </div>
              </div>
            </div>
            <span className="label">Repositories</span>
            <div className="bar-box">
              <div className="bar-left">
                <p className="number-label">{myInfo?.repositories}</p>
                <div className="bar">
                  <div className="data-bar" style={{ width: `${repoWidth?.myWidth}%` }}></div>
                </div>
              </div>
              <div className="bar-right">
                <p className="number-label">{otherInfo?.repositories}</p>
                <div className="bar">
                  <div className="data-bar" style={{ width: `${repoWidth?.otherWidth}%` }}></div>
                </div>
              </div>
            </div>
            <span className="label">Languages</span>
            <div className="chart-box">
              <div className="chart-subbox">
                <ChartJobrank curRank={props.curRank} jobPostingIdParam={props.jobPostingIdParam} target={0} />
              </div>
              <div className="chart-subbox">
                <ChartJobrank curRank={props.curRank} jobPostingIdParam={props.jobPostingIdParam} target={1} />
              </div>
            </div>
          </div>
        </div>
      </div>
    </Wrapper>
  );
};

export default CompareBox;
