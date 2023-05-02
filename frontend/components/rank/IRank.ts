// 임시로 만듬 => interface로 만들 방법 생각
export type resultInformation =
  | {
      avatarUrl: string;
      nickname: string;
      rank: number;
      rankUpDown: number;
      score: number;
      userId: number;
      tierUrl?: string;
    }[]
  | undefined;
export type resultMyInformation =
  | {
      avatarUrl: string;
      nickname: string;
      rank: number;
      rankUpDown: number;
      score: number;
      userId: number;
      tierUrl?: string;
    }
  | undefined;

/**
 * 필터 모달 props
 */
export interface IFilterModalProps {
  /**
   *
   * 모달창 닫는 메소드
   */
  onClick: () => void;

  /**
   *  현재 조회중인 랭킹 종류(0: 깃허브, 1: 백준)
   */
  curRank: number;

  /**
   * size
   */
  size: number;

  /**
   * nextRank
   */
  nextRank: number;

  /**
   * api 메소드
   */
  getRankList: Function;

  /**
   * isLangId useState set 메소드
   */
  setIsLangId: React.Dispatch<React.SetStateAction<number>>;
}

/**
 * 로그인 유저외의  랭킹 item
 */
export interface IMainOtherItemProps {
  /**
   *  현재 조회중인 랭킹 종류(0: 깃허브, 1: 백준)
   */
  curRank: number;

  /**
   * 조회할 item 정보
   */
  item: {
    avatarUrl: string;
    nickname: string;
    rank: number;
    rankUpDown: number;
    score: number;
    userId: number;
    tierUrl?: string;
  };
}

/**
 * 로그인 유저의  랭킹 item
 */
export interface IMainUserItemProps {
  /**
   *  현재 조회중인 랭킹 종류(0: 깃허브, 1: 백준)
   */
  curRank: number;

  /**
   * 조회할 item 정보
   */
  item: {
    userId: number;
    nickname: string;
    rank: number;
    score: number;
    avatarUrl: string;
    rankUpDown: number;
    tierUrl?: string;
  };
}

/**
 * 비회원일 때 띄울 item
 */
export interface INoAccountProps {
  /**
   *  현재 조회중인 랭킹 종류(0: 깃허브, 1: 백준)
   */
  curRank: number;

  /**
   *
   * 클릭시 발생시킬 이벤트
   */
  onClick?: () => void;
}

/**
 * 검색창 props => placeholder 값 셋팅
 */
export interface IRankSearchBarProps {
  /**
   *  현재 조회중인 랭킹 종류(0: 깃허브, 1: 백준)
   */
  curRank: number;

  /**
   * 자식에서 부모컴포넌트로 data 받기()
   */
  setGitRankList: React.Dispatch<React.SetStateAction<resultInformation | null>>;
  setBojRankList: React.Dispatch<React.SetStateAction<resultInformation | null>>;
}

export interface IFilterOptionProps {
  /**
   * 클릭 했을 때 발생할 이벤트
   */
  onClick: () => void;

  item: {
    languageId: number;
    name: string;
  };
}
