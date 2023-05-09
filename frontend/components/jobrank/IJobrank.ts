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

  /**
   * 0 : 나
   * 1 : 다른 사용자들(all)
   */
  target: number;
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

export interface ISubMenuProps {
  submenu: number;
  setSubmenu: React.Dispatch<React.SetStateAction<number>>;
}

export interface ICompareBoxProps {
  curRank: number;
  jobPostingIdParam: number;
}
