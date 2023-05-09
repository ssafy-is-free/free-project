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
  curRank: number;

  jobPostingIdParam: number;
}

/**
 * 전체 사용자 정보 조회 컴포넌트 props
 */
export interface IOtherInfoProps {
  curRank: number;

  jobPostingIdParam: number;
}

export interface IJobInfoProps {
  curRank: number;

  jobPostingIdParam: number;
}
