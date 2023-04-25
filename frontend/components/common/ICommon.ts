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
