/**
 * 취업지원현황조회 data
 */
export interface IHistory {
  jobHistoryId: number;
  companyName: string;
  dDayName: string;
  nextDate: string;
  dDay: string;
  status: string;
}

/**
 * 취업지원Detail
 */
export interface IHistoryDetail {
  postingId: number;
  postingName: string;
  companyName: string;
  status: string;
  startTime: string;
  endTime: string;
  memo: string;
  nextDate: string;
  objective: string;
  applicantCount: number;
  ddayName: string;
}

export interface ICareerStatus {
  id: number;
  name: string;
}

export interface ISearchResult {
  jobPostingId: number;
  companyName: string;
  postingName: string;
  startTime: string;
  endTime: string;
}

// Props //
// Props //
// Props //

export interface ICareerListProps {
  /**
   * openNew(fn): 추가하기 버튼클릭시 실행
   */
  openNew: () => void;
}

export interface ICareerListItemProps {
  cardId: number;
  /**
   * 삭제 체크박스 표시여부
   */
  delMode: boolean;
  dDay: string;
  /**
   * 삭제 체크박스 클릭시 실행
   * @param isChecked 체크여부
   * @returns
   */
  delCheck: (isChecked: boolean) => void;
  updateList: () => void;
}

export interface ICardHeaderProps {
  ddetail: IHistoryDetail;
  /**
   * 카드 자세히보기 눌렀는지 여부
   */
  spread: boolean | null;
  setSpread: () => void;
  ddayModal: () => void;
  statusModal: () => void;
}

export interface ICardContentProps {
  ddetail: IHistoryDetail;
  memoModal: () => void;
}

export interface ICheckBoxProps {
  handeler: (bChecked: boolean) => void;
}
export interface IDefaultDate {
  year: number;
  month: number;
  day: number;
}

export interface IDatePickerProps {
  updateDate: (date: string) => void;
  defaultDate: IDefaultDate;
}

export interface IDdayModalProps {
  close: () => void;
  result: (res: any) => void;
  defaultDate: IDefaultDate;
}

export interface IMemoModalProps {
  close: () => void;
  result: (memo: string) => void;
  defaultValue: string;
}

export interface IStatusModalProps {
  close: () => void;
  result: (status: ICareerStatus) => void;
}

export interface INewCareerProps {
  close: () => void;
}

export interface ICareerSearchProps {
  close: () => void;
  result: (item: any) => void;
}
