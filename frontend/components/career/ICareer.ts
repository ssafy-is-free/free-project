interface IHistory {
  jobHistoryId: number;
  companyName: string;
  dDayName: string;
  nextDate: string;
  dDay: string;
  status: string;
}

/** CareerListProps
 * openNew: 새로운
 */
interface ICareerListProps {
  openNew: () => void;
}
