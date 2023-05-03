/**
 * CancelOk 컴포넌트에 prop해줘야하는 값
 */
export interface ICancelOk {
  /**
   * 취소 행동의 버튼을 눌렀을 경우 작동하는 함수
   * @returns
   */
  cancel: () => void;
  /**
   * 확인 행동의 버튼을 눌렀을 경우 작동하는 함수
   * @returns
   */
  ok: () => void;
  /**
   * 취소 행동의 버튼에 들어갈 단어,
   * 기본값: '취소'
   */
  cancelWord?: string;
  /**
   * 확인 행동의 버튼에 들어갈 단어,
   * 기본값: '확인'
   */
  okWord?: string;
}

/**
 * 클릭한 버튼의 밑줄이 활성화 되는 nav바
 */
export interface ICustomNav {
  /**
   * 목록들
   */
  lists: string[];
  /**
   *
   * @param idx 선택된 요소의  idx
   * @returns
   */
  selectIdx: (idx: number) => void;
}

/**
 * 큰 버튼을 위한 props
 */
export interface IBigBtnProps {
  /**
   * button 안에 들어갈 text
   */
  text: string;

  /**
   *
   * 클릭 시 발생할 이벤트
   */
  onClick: () => void;
}

/**
 * 랭킹 페이지 메뉴 props
 */
export interface IRankMenuProps {
  /**
   *
   * 클릭시 발생할 이벤트
   */
  onClick?: () => void;

  /**
   * 현재 조회중인 rank (0 : 깃허브, 1: 백준)
   */
  curRank: number;

  /**
   * 현재 조회하는 랭킹 종류 교체하는 메소드
   */
  onChangeCurRank?: Function;

  setNoScroll?: React.Dispatch<React.SetStateAction<boolean>>;
}

/**
 * 랭킹 메뉴 클릭시 띄울 모달 props
 */

export interface IRankMenuSelectProps {
  /**
   * 모달 닫는 메소드
   */
  onClick: () => void;

  /**
   * 현재 조회하는 랭킹 종류 교체하는 메소드
   */
  onChangeCurRank: Function;
}

/**
 * spinner
 */
export interface ISpinner {
  /**
   * 가로세로 크기
   */
  size?: string;
  /**
   * 두께
   */
  borderWidth?: string;
  /**
   * 회전속도 숫자가 작을수록 빨라짐
   */
  duration?: number;
}
