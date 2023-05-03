import { useState } from 'react';
import styled from 'styled-components';

interface Idsummary {
  jobHistoryId: number;
  companyName: string;
  dDayName: string;
  nextDate: string;
  dDay: string;
  status: string;
}

interface Iddetail {
  postingId: number;
  postingName: string;
  companyName: string;
  status: string;
  startTime: string;
  endTime: string;
  memo: string;
  dDayName: string;
  nextDate: string;
  objective: string;
  applicantCount: number;
}

const dsummary = {
  jobHistoryId: 0,
  companyName: '삼성',
  dDayName: '코딩 테스트!',
  nextDate: '2023-04-16',
  dDay: '1',
  status: '서류 합격',
};

const ddetail = {
  postingId: 1,
  postingName: '뽑아요 뽑습니다',
  companyName: '삼성전자',
  status: '서류 합격',
  startTime: '2023-04-14 13:00',
  endTime: '2023-04-21 18:00',
  memo: '메모입니다~',
  dDayName: '서류 제출',
  nextDate: '2023-05-01',
  objective: '백엔드 개발자',
  applicantCount: 10,
};

const DetailCardDiv = styled.div`
  margin: 1rem;
  padding: 1rem;
  background-color: ${(props) => props.theme.lightGray};
  border-radius: 1rem;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  button {
    border-radius: 0.5rem;
    background-color: ${(props) => props.theme.primary};
    color: white;
    padding: 0.5rem;

    div {
      white-space: nowrap;
    }
  }

  .spreadIcon {
    right: 2rem;
    position: fixed;
    height: 1rem;
  }
  .memo {
    padding: 0.2rem;
    min-height: 3rem;
    white-space: pre-wrap;
    background-color: white;
    border-radius: 0.5rem;
    border: 1px solid;
  }
  .flexDiv {
    display: flex;
    justify-content: space-between;
    .upalignDiv {
      display: flex;
      .tag {
        white-space: nowrap;
      }
    }
  }
  .alignDiv {
    display: flex;
    align-items: center;
  }
`;

const SummaryCard = (dsummary: Idsummary) => {
  return (
    <div>
      <div>summary</div>
    </div>
  );
};

interface IDetailCardProps {
  ddetail: Iddetail;
  setSpread: () => void;
}

const DetailCard = ({ ddetail, setSpread }: IDetailCardProps) => {
  return (
    <DetailCardDiv>
      <img className="spreadIcon" src="/Icon/FilterArrowIcon.svg" alt="" onClick={setSpread} />
      <div>{ddetail.postingName}</div>
      <div>
        <h2>{ddetail.companyName}</h2>
      </div>
      <div>
        {ddetail.startTime} ~ {ddetail.endTime}
      </div>
      <div className="flexDiv">
        <button>다음일정: {ddetail.nextDate}</button>
        <button>{ddetail.dDayName}</button>
      </div>
      <div>
        <div>메모</div>
      </div>
      <div>
        <div className="memo">{ddetail.memo}</div>
      </div>
      <div className="flexDiv">
        <div>
          <div className="upalignDiv">
            <div className="tag">지원직무:&nbsp;</div>s<div>{ddetail.objective}</div>
          </div>
          <div>
            <span>지원자수: {ddetail.applicantCount}</span>
          </div>
        </div>
        <button>
          <div>지원자 정보</div>
          <div>보러가기</div>
        </button>
      </div>
    </DetailCardDiv>
  );
};

const CareerCard = () => {
  const [spread, setSpread] = useState<boolean>(true);
  if (spread) {
    return (
      <DetailCard
        ddetail={ddetail}
        setSpread={() => {
          setSpread(!spread);
        }}
      ></DetailCard>
    );
  } else {
    return SummaryCard(dsummary);
  }
};

export default CareerCard;
