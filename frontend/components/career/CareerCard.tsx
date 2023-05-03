import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { Spinner } from '../common/Spinner';

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

interface IDetailCardProps {
  cardId: number;
}
interface ICardHeaderProps {
  ddetail: Iddetail;
  spread: boolean;
  setSpread: () => void;
}
interface ICardContentProps {
  ddetail: Iddetail;
}

const CardHeader = ({ ddetail, spread, setSpread }: ICardHeaderProps) => {
  return (
    <div>
      <img className="spreadIcon" src="/Icon/FilterArrowIcon.svg" alt="" onClick={setSpread} />
      {spread && <div>{ddetail.postingName}</div>}
      <div>
        <h2>{ddetail.companyName}</h2>
      </div>
      {spread && (
        <div>
          {ddetail.startTime} ~ {ddetail.endTime}
        </div>
      )}
      <div className="flexDiv">
        <button>다음일정: {ddetail.nextDate}</button>
        <button>{ddetail.dDayName}</button>
      </div>
    </div>
  );
};

const CardContent = ({ ddetail }: ICardContentProps) => {
  return (
    <div>
      <div>
        <div>메모</div>
      </div>
      <div>
        <div className="memo">{ddetail.memo}</div>
      </div>
      <div className="flexDiv">
        <div>
          <div className="upalignDiv">
            <div className="tag">지원직무:&nbsp;</div>
            <div>{ddetail.objective}</div>
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
    </div>
  );
};

const CareerCard = ({ cardId }: IDetailCardProps) => {
  const [spread, setSpread] = useState<boolean>(false);
  const [detail, setDetail] = useState<Iddetail | null>(null);

  useEffect(() => {
    // api
    setTimeout(() => {
      setDetail(ddetail);
    }, 1000);
  }, []);

  if (!detail) {
    return (
      <DetailCardDiv>
        <Spinner></Spinner>
      </DetailCardDiv>
    );
  } else {
    return (
      <DetailCardDiv>
        <CardHeader
          ddetail={detail}
          setSpread={() => {
            setSpread(!spread);
          }}
          spread={spread}
        />
        {spread && <CardContent ddetail={detail} />}
      </DetailCardDiv>
    );
  }
};

export default CareerCard;
