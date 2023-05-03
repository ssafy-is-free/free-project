import { chartColors } from '@/public/chartcolors';

const defaultColors = [
  '#FF6384',
  '#36A2EB',
  '#FFCE56',
  '#FF6384',
  '#36A2EB',
  '#FFCE56',
  '#FF6384',
  '#36A2EB',
  '#FFCE56',
  '#FF6384',
  '#36A2EB',
  '#FFCE56',
  '#FF6384',
  '#36A2EB',
  '#FFCE56',
  '#FF6384',
  '#36A2EB',
  '#FFCE56',
];

const getColor = (language: string): string => {
  const languageCode = language.split(' ')[0].replace(/[1-9]/g, '');
  let defaultColorIdx = 0;
  if (chartColors[languageCode]) {
    return chartColors[languageCode].color;
  } else {
    defaultColorIdx += 1;
    return defaultColors[defaultColorIdx];
  }
};

export interface IChartInput {
  name: string;
  percentage?: string;
  passPercentage?: string;
  passCount?: number;
}

/**
 * data를 chartjs에 쓸 dataset로 변환
 * @param data
 * @returns
 */
export const myChartData = (data: IChartInput[]) => {
  let colors: string[] = [];
  const labels = data.map((item: IChartInput) => {
    colors.push(getColor(item.name));
    return item.name;
  });

  const percentages = data.map((item: IChartInput) => {
    if (item.percentage) {
      return parseFloat(item.percentage.replace('%', ''));
    } else if (item.passPercentage) {
      return item.passCount;
    }
  });

  const dataset = {
    labels,
    datasets: [
      {
        data: percentages,
        backgroundColor: colors,
        hoverBackgroundColor: colors,
      },
    ],
  };
  return dataset;
};
