export interface ChartInput {
  name: string;
  percentage?: string;
  passPercentage?: string;
}

/**
 * data를 chartjs에 쓸 dataset로 변환
 * @param data
 * @returns
 */
export const myChartData = (data: ChartInput[]) => {
  const labels = data.map((item: ChartInput) => {
    return item.name;
  });
  const percentages = data.map((item: ChartInput) => {
    if (item.percentage) {
      return parseFloat(item.percentage.replace('%', ''));
    } else if (item.passPercentage) {
      return parseFloat(item.passPercentage.replace('%', ''));
    }
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
