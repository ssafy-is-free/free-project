import { ChartInput } from '@/utils/chartDatasets';

/**
 * github profile data
 */
export interface IGithubProfile {
  githubId: number;
  nickname: string;
  /**
   * github 링크
   */
  profileLink: string;
  avatarUrl: string;
  commit: number;
  star: number;
  followers: number;
  repositories: {
    id: number;
    name: string;
    link: string;
  }[];
  languages: {
    name: string;
    percentage: number;
  }[];
}

/**
 * githubInfo에 prop 해야할 값
 */
export interface IGithubInfo {
  github: IGithubProfile;
}

/**
 * 백준 프로필
 */
export interface IBojProfile {
  bojId: string;
  tierUrl: string;
  languages: {
    name: string;
    percentage: number;
  }[];
  pass: number;
  tryFail: number;
  submit: number;
  fail: number;
}

/**
 * bojInfo에 prop 해야할 값
 */
export interface IBojInfo {
  boj: IBojProfile;
}

/**
 * 차트 생성시 필요한 값
 */
export interface CircleChartProps {
  data: ChartInput[];
  fontsize?: number;
  label?: boolean;
}
