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
