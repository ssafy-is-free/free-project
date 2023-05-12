import { useEffect, useState } from 'react';
import { Doughnut } from 'react-chartjs-2';
import styled from 'styled-components';
import { IChartProps } from './IJobrank';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import {
  getBojCompareUser,
  getGitCompareUser,
  getPostingsAllBojUsers,
  getPostingsAllGitUsers,
} from '@/pages/api/jobRankAxios';
import ChartJobrankLoading from './ChartJobrankLoading';

const Wrapper = styled.div`
  position: relative;
  width: 95%;
  display: flex;
  justify-content: center;

  .top-language {
    position: absolute;
    top: 0px;
    text-align: center;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;

    .top-name {
      color: #ff7f7f;
      font-weight: bold;
      font-size: 0.8em;
      margin-bottom: 4px;
    }

    .top-percent {
      color: ${(props) => props.theme.fontDarkGray};
      font-size: 0.5em;
    }
  }
`;

const ChartJobrank = (props: IChartProps) => {
  // 차트
  const [series, setSeries] = useState<number[]>([]);
  const [labels, setLabels] = useState<string[]>([]);

  // 로딩
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    setLoading(true);

    if (props.jobPostingIdParam) {
      // 전체 사용자 정보 가져오기
      (async () => {
        if (props.curRank == 0) {
          if (props.jobPostingIdParam) {
            let data = await getPostingsAllGitUsers(props.jobPostingIdParam);

            if (data.status === 'SUCCESS') {
              let arr =
                props.target == 0 ? data.data?.my.languages.languageList : data.data?.opponent.languages.languageList;
              let scores = new Array();
              let labels = new Array();
              arr?.map((el: any) => {
                scores.push(el.percentage);
                labels.push(el.name);
              });
              setSeries([...scores]);
              setLabels([...labels]);
              setLoading(false);
            } else {
              alert(data.message);
            }
          }
        } else {
          if (props.jobPostingIdParam) {
            let data = await getPostingsAllBojUsers(props.jobPostingIdParam);

            if (data.status === 'SUCCESS') {
              let arr = props.target == 0 ? data.data?.my.languages : data.data?.other.languages;
              let scores = new Array();
              let labels = new Array();
              arr?.map((el: any) => {
                scores.push(el.passCount);
                labels.push(el.name);
              });
              setSeries([...scores]);
              setLabels([...labels]);
              setLoading(false);
            } else {
              alert(data.message);
            }
          }
        }
      })();
    } else {
      (async () => {
        if (props.curRank == 0) {
          if (props.userId) {
            let data = await getGitCompareUser(props.userId);

            if (data.status === 'SUCCESS') {
              let arr =
                props.target == 0 ? data.data?.my.languages.languageList : data.data?.opponent.languages.languageList;
              let scores = new Array();
              let labels = new Array();
              arr?.map((el: any) => {
                scores.push(el.percentage);
                labels.push(el.name);
              });
              setSeries([...scores]);
              setLabels([...labels]);
              setLoading(false);
            } else {
              alert(data.message);
            }
          }
        } else {
          if (props.userId) {
            let data = await getBojCompareUser(props.userId);

            if (data.status === 'SUCCESS') {
              let arr = props.target == 0 ? data.data?.my.languages : data.data?.opponent.languages;
              let scores = new Array();
              let labels = new Array();
              arr?.map((el: any) => {
                scores.push(el.passCount);
                labels.push(el.name);
              });
              setSeries([...scores]);
              setLabels([...labels]);
              setLoading(false);
            } else {
              alert(data.message);
            }
          }
        }
      })();
    }
  }, [props.curRank]);

  ChartJS.register(ArcElement, Tooltip, Legend);

  const options = {
    plugins: {
      legend: {
        display: false,
      },
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

  return (
    <>
      {loading ? (
        <ChartJobrankLoading />
      ) : (
        <Wrapper>
          <div className="top-language">
            <p className="top-name">{labels[0]}</p>
            <p className="top-percent">
              {series[0]} {props.curRank == 0 ? '%' : '개'}
            </p>
          </div>
          <Doughnut data={data} options={options} />
        </Wrapper>
      )}
    </>
  );
};

export default ChartJobrank;
