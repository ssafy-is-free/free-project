import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { Spinner } from '../common/Spinner';
import { getHistoryDtail } from '@/pages/api/careerAxios';
import CheckBox from './CheckBox';

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
  ddayName: string;
}

const DetailCardDiv = styled.div`
  width: 100%;
  display: flex;

  .checkbox {
    width: 2rem;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .item {
    flex-grow: 1;
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
  }
`;

interface ICareerListItemProps {
  cardId: number;
  delMode: boolean;
  dDay: string;
  delCheck: (isChecked: boolean) => void;
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
          {ddetail.ddayName}: {ddetail.nextDate}
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

const CareerListItem = ({ cardId, dDay, delMode, delCheck }: ICareerListItemProps) => {
  const [spread, setSpread] = useState<boolean>(false);
  const [detail, setDetail] = useState<Iddetail | null>(null);

  const headerClick = () => {
    setSpread(!spread);
  };

  const getDetail = async () => {
    const res = await getHistoryDtail(cardId);
    if (res.status == 'SUCCESS') {
      setDetail(res.data);
    } else {
      console.log(res.message);
    }
  };

  const modalOpen = (tag: string) => {
    console.log(tag);
  };

  useEffect(() => {
    getDetail();
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
        {delMode && <CheckBox handeler={(bChecked: boolean) => delCheck(bChecked)}></CheckBox>}
        <div className="item">
          <CardHeader ddetail={detail} setSpread={headerClick} spread={spread} modalOpen={modalOpen} />
          {spread && <CardContent ddetail={detail} />}
        </div>
      </DetailCardDiv>
    );
  }
};

export default CareerListItem;
