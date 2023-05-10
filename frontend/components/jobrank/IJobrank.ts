export type applicantGitInfoType = {
  avatarUrl: string;
  commit: number;
  languages: {
    languageList: { name: string; percentage: number }[];
  };
  nickname: string;
  repositories: number;
  star: number;
} | null;

export type applicantBojInfoType = {
  bojId?: string;
  fail: number;
  languages: {
    name: string;
    passPercentage: string;
    passCount: number;
  }[];
  pass: number;
  submit: number;
  tierUrl: string;
  tryFail: number;
} | null;

export type postingType = {
  postingId: number;
  postingName: string;
  companyName: string;
  status: string;
  startTime: string;
  endTime: string;
  memo: string;
  dDayName: string;
  nextDate: string;
  objective: string;
  applicantCount: number;
} | null;

export type widthType = {
  myWidth: number;
  otherWidth: number;
} | null;

/**
 * chart props
 */
export interface IChartProps {
  curRank: number;
  jobPostingIdParam?: number;
  userId?: number;

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

export interface ICompareUserBoxProps {
  curRank: number;
  userId: number;
}
