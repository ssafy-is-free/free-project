import { useEffect, useState } from 'react';
import { Doughnut } from 'react-chartjs-2';
import styled from 'styled-components';
import { IChartProps } from './IJobrank';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { getPostingsAllUsers } from '@/pages/api/jobRankAxios';

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

  useEffect(() => {
    // 전체 사용자 정보 가져오기
    (async () => {
      let data = await getPostingsAllUsers(props.jobPostingIdParam);

      let arr = props.target == 0 ? data?.my.languages.languageList : data?.opponent.languages.languageList;
      let scores = new Array();
      let labels = new Array();
      arr?.map((el: any) => {
        scores.push(el.percentage);
        labels.push(el.name);
      });
      setSeries([...scores]);
      setLabels([...labels]);
    })();
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
    <Wrapper>
      <Doughnut data={data} options={options} />
      <div className="top-language">
        <p className="top-name">{labels[0]}</p>
        <p className="top-percent">{series[0]} %</p>
      </div>
    </Wrapper>
  );
};

export default ChartJobrank;
