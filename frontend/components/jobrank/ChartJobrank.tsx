import { useEffect, useState } from 'react';
import { Doughnut } from 'react-chartjs-2';
import styled from 'styled-components';
import { IChartProps } from './IJobrank';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { getPostingsAllUsers } from '@/pages/api/jobRankAxios';

const Wrapper = styled.div`
  position: relative;

  .top-language {
    position: absolute;
    top: 130px;
    left: 130px;

    .top-name {
      color: #ff7f7f;
      font-weight: bold;
      font-size: 20px;
    }

    .top-percent {
      color: ${(props) => props.theme.fontDarkGray};
      font-size: 16px;
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
  }, [props.curRank]);

  ChartJS.register(ArcElement, Tooltip, Legend);

  const options = {
    plugins: {
      legend: {
        display: false,
        // position: 'bottom',
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
