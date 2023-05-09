import { useState } from 'react';
import { Doughnut } from 'react-chartjs-2';
import styled from 'styled-components';
import { IChartProps } from './IJobrank';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';

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
    labels: props.labels,
    datasets: [
      {
        data: props.series,
        backgroundColor: ['#ff7f7f', '#65a3ff', '#63f672', '#d28fff', '#ffec74'],
      },
    ],
  };

  return (
    <Wrapper>
      <Doughnut data={data} options={options} />
      <div className="top-language">
        <p className="top-name">{props.labels[0]}</p>
        <p className="top-percent">{props.series[0]} %</p>
      </div>
    </Wrapper>
  );
};

export default ChartJobrank;
