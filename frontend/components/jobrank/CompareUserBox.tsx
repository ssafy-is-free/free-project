import styled, { css } from 'styled-components';
import { ICompareUserBoxProps, applicantBojInfoType, applicantGitInfoType, widthType } from './IJobrank';
import { useEffect, useState } from 'react';
import UserDefaultIcon from '../../public/Icon/UserDefaultIcon.svg';
import ChartJobrank from './ChartJobrank';
import { getBojCompareUser, getGitCompareUser } from '@/pages/api/jobRankAxios';

const Wrapper = styled.div`
  width: 100%;
  background-color: ${(props) => props.theme.bgWhite};

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
                width: 0%;
                transition: 1s;
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
                width: 0%;
                transition: 1s;
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

const CompareUserBox = (props: ICompareUserBoxProps) => {
  // 나와 전체 사용자 정보
  const [otherGitInfo, setOtherGitInfo] = useState<applicantGitInfoType>(null);
  const [myGitInfo, setMyGitInfo] = useState<applicantGitInfoType>(null);
  const [otherBojInfo, setOtherBojInfo] = useState<applicantBojInfoType>(null);
  const [myBojInfo, setMyBojInfo] = useState<applicantBojInfoType>(null);

  const [commitWidth, setCommitWidth] = useState<widthType>(null);
  const [starWidth, setStarWidth] = useState<widthType>(null);
  const [repoWidth, setRepoWidth] = useState<widthType>(null);

  const [passWidth, setPassWidth] = useState<widthType>(null);
  const [failWidth, setFailWidth] = useState<widthType>(null);
  const [submitWidth, setSubmitWidth] = useState<widthType>(null);
  const [tryfailWidth, setTryfailWidth] = useState<widthType>(null);

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
      if (props.curRank == 0) {
        let data = await getGitCompareUser(props.userId);
        setOtherGitInfo(data?.opponent);
        setMyGitInfo(data?.my);

        setCommitWidth(calc(data?.my.commit, data?.opponent.commit));
        setStarWidth(calc(data?.my.star, data?.opponent.star));
        setRepoWidth(calc(data?.my.repositories, data?.opponent.repositories));
      } else {
        let data = await getBojCompareUser(props.userId);
        setOtherBojInfo(data?.opponent);
        setMyBojInfo(data?.my);

        setPassWidth(calc(data?.my.pass, data?.opponent.pass));
        setFailWidth(calc(data?.my.fail, data?.opponent.fail));
        setSubmitWidth(calc(data?.my.submit, data?.opponent.submit));
        setTryfailWidth(calc(data?.my.tryFail, data?.opponent.tryFail));
      }
    })();
  }, [props.curRank]);

  return (
    <Wrapper>
      <div className="content-wrapper">
        <div className="content-top">
          {props.curRank == 0 ? (
            <>
              <div className="content-top-left">
                <img src={myGitInfo?.avatarUrl} className="my-img" />
                <p>{myGitInfo?.nickname}</p>
              </div>
              <div className="content-top-right">
                <img src={otherGitInfo?.avatarUrl} className="my-img" />
                <p>{otherGitInfo?.nickname}</p>
              </div>
            </>
          ) : (
            <>
              <div className="content-top-left">
                <img src={myBojInfo?.tierUrl} className="my-img" style={{ borderRadius: '0' }} />
                <p>{myBojInfo?.bojId}</p>
              </div>
              <div className="content-top-right">
                <img src={otherBojInfo?.tierUrl} className="my-img" style={{ borderRadius: '0' }} />
                <p>{otherBojInfo?.bojId}</p>
              </div>
            </>
          )}
        </div>
        <div className="content">
          <div className="content-box">
            {props.curRank == 0 ? (
              <>
                <span className="label">커밋</span>
                <div className="bar-box">
                  <div className="bar-left">
                    <p className="number-label">{myGitInfo?.commit}</p>
                    <div className="bar">
                      <div className="data-bar" style={{ width: `${commitWidth?.myWidth}%` }}></div>
                    </div>
                  </div>
                  <div className="bar-right">
                    <p className="number-label">{otherGitInfo?.commit}</p>
                    <div className="bar">
                      <div className="data-bar" style={{ width: `${commitWidth?.otherWidth}%` }}></div>
                    </div>
                  </div>
                </div>
                <span className="label">스타</span>
                <div className="bar-box">
                  <div className="bar-left">
                    <p className="number-label">{myGitInfo?.star}</p>
                    <div className="bar">
                      <div className="data-bar" style={{ width: `${starWidth?.myWidth}%` }}></div>
                    </div>
                  </div>
                  <div className="bar-right">
                    <p className="number-label">{otherGitInfo?.star}</p>
                    <div className="bar">
                      <div className="data-bar" style={{ width: `${starWidth?.otherWidth}%` }}></div>
                    </div>
                  </div>
                </div>
                <span className="label">레포지토리</span>
                <div className="bar-box">
                  <div className="bar-left">
                    <p className="number-label">{myGitInfo?.repositories}</p>
                    <div className="bar">
                      <div className="data-bar" style={{ width: `${repoWidth?.myWidth}%` }}></div>
                    </div>
                  </div>
                  <div className="bar-right">
                    <p className="number-label">{otherGitInfo?.repositories}</p>
                    <div className="bar">
                      <div className="data-bar" style={{ width: `${repoWidth?.otherWidth}%` }}></div>
                    </div>
                  </div>
                </div>
                <span className="label">사용 언어</span>
                <div className="chart-box">
                  <div className="chart-subbox">
                    <ChartJobrank curRank={props.curRank} userId={props.userId} target={0} />
                  </div>
                  <div className="chart-subbox">
                    <ChartJobrank curRank={props.curRank} userId={props.userId} target={1} />
                  </div>
                </div>
              </>
            ) : (
              <>
                <span className="label">맞은 문제</span>
                <div className="bar-box">
                  <div className="bar-left">
                    <p className="number-label">{myBojInfo?.pass}</p>
                    <div className="bar">
                      <div className="data-bar" style={{ width: `${passWidth?.myWidth}%` }}></div>
                    </div>
                  </div>
                  <div className="bar-right">
                    <p className="number-label">{otherBojInfo?.pass}</p>
                    <div className="bar">
                      <div className="data-bar" style={{ width: `${passWidth?.otherWidth}%` }}></div>
                    </div>
                  </div>
                </div>
                <span className="label">틀린 문제</span>
                <div className="bar-box">
                  <div className="bar-left">
                    <p className="number-label">{myBojInfo?.fail}</p>
                    <div className="bar">
                      <div className="data-bar" style={{ width: `${failWidth?.myWidth}%` }}></div>
                    </div>
                  </div>
                  <div className="bar-right">
                    <p className="number-label">{otherBojInfo?.fail}</p>
                    <div className="bar">
                      <div className="data-bar" style={{ width: `${failWidth?.otherWidth}%` }}></div>
                    </div>
                  </div>
                </div>
                <span className="label">제출한 문제</span>
                <div className="bar-box">
                  <div className="bar-left">
                    <p className="number-label">{myBojInfo?.submit}</p>
                    <div className="bar">
                      <div className="data-bar" style={{ width: `${submitWidth?.myWidth}%` }}></div>
                    </div>
                  </div>
                  <div className="bar-right">
                    <p className="number-label">{otherBojInfo?.submit}</p>
                    <div className="bar">
                      <div className="data-bar" style={{ width: `${submitWidth?.otherWidth}%` }}></div>
                    </div>
                  </div>
                </div>
                <span className="label">시도했지만 틀린 문제</span>
                <div className="bar-box">
                  <div className="bar-left">
                    <p className="number-label">{myBojInfo?.tryFail}</p>
                    <div className="bar">
                      <div className="data-bar" style={{ width: `${tryfailWidth?.myWidth}%` }}></div>
                    </div>
                  </div>
                  <div className="bar-right">
                    <p className="number-label">{otherBojInfo?.tryFail}</p>
                    <div className="bar">
                      <div className="data-bar" style={{ width: `${tryfailWidth?.otherWidth}%` }}></div>
                    </div>
                  </div>
                </div>
                <span className="label">사용 언어</span>
                <div className="chart-box">
                  <div className="chart-subbox">
                    <ChartJobrank curRank={props.curRank} userId={props.userId} target={0} />
                  </div>
                  <div className="chart-subbox">
                    <ChartJobrank curRank={props.curRank} userId={props.userId} target={1} />
                  </div>
                </div>
              </>
            )}
          </div>
        </div>
      </div>
    </Wrapper>
  );
};

export default CompareUserBox;
