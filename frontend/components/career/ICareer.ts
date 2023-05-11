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

// Props //
// Props //
// Props //

/**
 * openNew(fn)
 */
export interface ICareerListProps {
  /**
   * openNew(fn): 추가하기 버튼클릭시 실행
   */
  openNew: () => void;
}
