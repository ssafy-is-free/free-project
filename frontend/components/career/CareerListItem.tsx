import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { Spinner } from '../common/Spinner';
import NewCareerModal from './NewCareerModal';

interface Iddetail {
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
  dDayName: string;
}
const ddetail = {
  postingId: 1,
  postingName: '뽑아요 뽑습니다',
  companyName: '삼성전자',
  status: '서류 합격',
  startTime: '2023-04-14 13:00',
  endTime: '2023-04-21 18:00',
  memo: '메모입니다~메모입니다~메모입니다~메모입니다~메모입니다\n~메모입니다~\n메모입니다~메모입니다~메모입니다~메모입니다~메모입니다~메모입니다~메모입니다~메모입니다~메모입니다~메모입니다~메모입니다~메모입니다~메모입니다~메모입니다~메모입니다~메모입니다~메모입니다~메모입니다~',
  dDayName: '코딩테스트',
  nextDate: '2023-05-01',
  objective: '백엔드 개발자',
  applicantCount: 10,
};

const DetailCardDiv = styled.div`
  padding: 1rem;
  width: 100%;
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
  .title {
    display: flex;
    justify-content: space-between;
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
    margin-top: 0.5rem;
    margin-bottom: 0.5rem;
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
  modalOpen: (tag: string) => void;
}
interface ICardContentProps {
  ddetail: Iddetail;
}

const CardHeader = ({ ddetail, spread, setSpread, modalOpen }: ICardHeaderProps) => {
  return (
    <div>
      {spread && <div>{ddetail.postingName}</div>}
      <div className="title">
        <h2>{ddetail.companyName}</h2>
        <img className="spreadIcon" src="/Icon/FilterArrowIcon.svg" alt="" onClick={setSpread} />
      </div>
      {spread && (
        <div>
          {ddetail.startTime} ~ {ddetail.endTime}
        </div>
      )}
      <div className="flexDiv">
        <button onClick={() => modalOpen('dDayName')}>
          {ddetail.dDayName}: {ddetail.nextDate}
        </button>
        <button>{ddetail.status}</button>
      </div>
    </div>
  );
};

const CardContent = ({ ddetail }: ICardContentProps) => {
  return (
    <div>
      <div>메모</div>
      <div className="memo">{ddetail.memo}</div>
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

const CareerListItem = ({ cardId }: IDetailCardProps) => {
  const [spread, setSpread] = useState<boolean>(false);
  const [detail, setDetail] = useState<Iddetail | null>(null);

  const headerClick = () => {
    setSpread(!spread);
  };

  const modalOpen = (tag: string) => {
    console.log(tag);
  };

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
        <CardHeader ddetail={detail} setSpread={headerClick} spread={spread} modalOpen={modalOpen} />
        {spread && <CardContent ddetail={detail} />}
      </DetailCardDiv>
    );
  }
};

export default CareerListItem;
