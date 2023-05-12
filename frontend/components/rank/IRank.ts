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
  | null;
export type resultMyInformation = {
  avatarUrl: string;
  nickname: string;
  rank: number;
  rankUpDown: number;
  score: number;
  userId: number;
  tierUrl?: string;
} | null;

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
   * api 메소드
   */
  getRankList: Function;

  setNoMore: React.Dispatch<React.SetStateAction<boolean>>;
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

  isJob?: boolean;
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
  } | null;
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

  setRankInfo: React.Dispatch<React.SetStateAction<resultInformation | null>>;

  // 스크롤 막기
  setNoScroll: React.Dispatch<React.SetStateAction<boolean>>;

  /**
   * api 메소드
   */
  getRankList: Function;

  setInViewFirst: React.Dispatch<React.SetStateAction<boolean>>;

  setSearchClick: React.Dispatch<React.SetStateAction<boolean>>;

  setNoMore: React.Dispatch<React.SetStateAction<boolean>>;

  setMyRankInfo: React.Dispatch<React.SetStateAction<resultMyInformation | null>>;

  setLoading: React.Dispatch<React.SetStateAction<boolean>>;
}

export interface IFilterOptionProps {
  /**
   * 클릭 했을 때 발생할 이벤트
   */
  onClick?: () => void;

  /**
   * optioo 정보
   */
  item?: {
    languageId: number;
    name: string;
  };

  isInFilter?: boolean;

  isInMain?: boolean;

  /**
   * api 메소드
   */
  getRankList?: Function;

  setNoMore?: React.Dispatch<React.SetStateAction<boolean>>;
}

export interface ISettingModalProps {
  /**
   *
   * 모달창 닫는 메소드
   */
  onClick: () => void;
}

/**
 * 로그인 유저의  랭킹 item(채용 정보 페이지)
 */
export interface IJobUserItemProps {
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
