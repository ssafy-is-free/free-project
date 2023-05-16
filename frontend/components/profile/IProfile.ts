import { IChartInput } from '@/utils/chartDatasets';

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
  mine: boolean;
  repositories: IRepository[] | null;
  languages: {
    name: string;
    percentage: string;
  }[];
}

/**
 * githubInfo에 prop 해야할 값
 */
export interface IGithubInfo {
  userId: string;
  // githubData: IGithubProfile;
  my?: boolean;
  setOpenCompare?: React.Dispatch<React.SetStateAction<boolean>>;
}

/**
 * 백준 프로필
 */
export interface IBojProfile {
  bojId: string;
  tierUrl: string;
  languages: {
    name: string;
    passPercentage: string;
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
  userId: string;
  bojData?: IBojProfile;
  my?: boolean;
  setOpenCompare?: React.Dispatch<React.SetStateAction<boolean>>;
}

/**
 * 차트 생성시 필요한 값
 */
export interface CircleChartProps {
  data: IChartInput[];
  fontsize?: number;
  label?: boolean;
}

/**
 * 아바타 데이터
 */
export interface IAvatarData {
  avatarUrl: string;
  name: string;
  profileLink?: string;
}

/**
 * 아바타 데이터
 */
export interface IBojAvatar {
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
  my?: boolean;
  curRank?: number;
  userId?: number;
  setOpenCompare?: React.Dispatch<React.SetStateAction<boolean>>;
}

/**
 * Profile 컴포넌트에 prop해야할 값
 */
export interface IProfile {
  isGithub: boolean;
  userId: number;
  // back: () => void;
}
