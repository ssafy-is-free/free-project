import { ChartInput } from '@/utils/chartDatasets';

export interface IRepository {
  id: number;
  name: string;
  link: string;
}

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
  repositories: IRepository[];
  languages: {
    name: string;
    percentage: number;
  }[];
}

/**
 * githubInfo에 prop 해야할 값
 */
export interface IGithubInfo {
  githubId: number;
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
  bojId: number;
}

/**
 * 차트 생성시 필요한 값
 */
export interface CircleChartProps {
  data: ChartInput[];
  fontsize?: number;
  label?: boolean;
}

/**
 * 아바타 데이터
 */
export interface IAvatarData {
  avatarUrl: string;
  name: string;
}

/**
 * Avatar 컴포넌트에 prop해야할 값
 */
export interface IAvatar {
  /**
   * isCircle : 아바타를 원 형태로 바꿀지
   */
  isCircle: boolean;
  data: IAvatarData;
}

/**
 * Profile 컴포넌트에 prop해야할 값
 */
export interface IProfile {
  isGithub: boolean;
  userId: number;
  // back: () => void;
}
