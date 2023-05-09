export type applicantInfoType = {
  avatarUrl: string;
  commit: number;
  languages: {
    languageList: { name: string; percentage: number }[];
  };
  nickname: string;
  repositories: number;
  star: number;
} | null;

/**
 * chart props
 */
export interface IChartProps {
  /**
   * data
   */
  series: number[];

  /**
   * label
   */
  labels: string[];
}

/**
 * 전체 사용자 정보 조회 컴포넌트 props
 */
export interface IOtherInfoProps {
  otherInfo: applicantInfoType;
}

export interface IJobInfoProps {}
