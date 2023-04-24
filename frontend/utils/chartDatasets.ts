export interface ChartInput {
  name: string;
  percentage: number;
}

/**
 * data를 chartjs에 쓸 dataset로 변환
 * @param data
 * @returns
 */
export const myChartData = (data: ChartInput[]) => {
  const labels = data.map((data: ChartInput) => {
    return data.name;
  });
  const percentages = data.map((data: ChartInput) => {
    return data.percentage;
  });

  const dataset = {
    labels,
    datasets: [
      {
        data: percentages,
        backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56'],
        hoverBackgroundColor: ['#FF6384', '#36A2EB', '#FFCE56'],
      },
    ],
  };
  return dataset;
};
