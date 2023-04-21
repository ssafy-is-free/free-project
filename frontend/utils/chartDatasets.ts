interface Language {
  name: string;
  percentage: number;
}

/**
 * languages를 chartjs에 쓸 data로 변환
 * @param languages
 * @returns
 */
export const myChartData = (languages: Language[]) => {
  const labels = languages.map((language: Language) => {
    return language.name;
  });
  const percentages = languages.map((language: Language) => {
    return language.percentage;
  });

  const data = {
    labels,
    datasets: [
      {
        data: percentages,
        backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56'],
        hoverBackgroundColor: ['#FF6384', '#36A2EB', '#FFCE56'],
      },
    ],
  };
  return data;
};
