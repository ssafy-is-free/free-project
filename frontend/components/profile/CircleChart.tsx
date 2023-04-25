import React from 'react';
import { Doughnut } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { myChartData } from '@/utils/chartDatasets';
import { CircleChartProps } from './IProfile';

export default function CircleChart({ data, fontsize, label }: CircleChartProps) {
  ChartJS.register(ArcElement, Tooltip, Legend);
  const options = {
    responsive: true,
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
    <div>
      <Doughnut options={options} data={myChartData(data)}></Doughnut>
    </div>
  );
}
