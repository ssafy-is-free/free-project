import React from 'react';
import styled from 'styled-components';
import { myChartData } from '@/utils/chartDatasets';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { Doughnut } from 'react-chartjs-2';

/**
 * dummydata
 */
const data = {
  githubId: 1,
  nickname: 'hyejoo',
  profileLink: 'https://~~',
  avatarUrl: 'https://~~~',
  commit: 100,
  star: 20,
  followers: 5,
  repositories: [
    {
      id: 1,
      name: 'ssafy-name',
      link: 'https://~~~',
    },
  ],
  languages: [
    {
      name: 'java',
      percentage: 98,
    },
    {
      name: 'python',
      percentage: 2,
    },
  ],
};

const ChartDiv = styled.div`
  height: auto;
`;

export default function CircleChart() {
  ChartJS.register(ArcElement, Tooltip, Legend);
  const options = {
    // 옵션 (1)
    responsive: true,
    // 옵션 (2)
    interaction: {},
    // 옵션 (4)
    plugins: {
      legend: {
        display: true,
        position: 'right' as const,
        labels: {
          font: {
            size: 30,
          },
        },
      },
    },
  };

  return (
    <ChartDiv>
      <Doughnut options={options} data={myChartData(data.languages)}></Doughnut>
    </ChartDiv>
  );
}
