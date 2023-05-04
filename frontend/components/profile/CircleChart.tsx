import React from 'react';
import { Doughnut } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { myChartData } from '@/utils/chartDatasets';
import { CircleChartProps } from './IProfile';
import styled from 'styled-components';

const ChartDiv = styled.div`
  canvas {
    margin-inline: auto;
    height: 80vw !important;
    width: 80vw !important;
  }
`;

const CircleChart = ({ data, fontsize, label }: CircleChartProps) => {
  ChartJS.register(ArcElement, Tooltip, Legend);
  const options = {
    responsive: true,
    // maintainAspectRatio: false,
    plugins: {
      legend: {
        display: label ? true : false,
        position: 'right' as const,
        labels: {
          font: {
            size: fontsize ? fontsize : 10,
          },
        },
      },
    },
  };

  return (
    <ChartDiv>
      <Doughnut options={options} data={myChartData(data)}></Doughnut>
    </ChartDiv>
  );
};
export default CircleChart;
