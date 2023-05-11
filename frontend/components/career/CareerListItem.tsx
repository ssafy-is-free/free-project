import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { Spinner } from '../common/Spinner';
import { getHistoryDtail, patchHistory } from '@/pages/api/careerAxios';
import CheckBox from './CheckBox';
import StatusModal, { IStatus } from './StatusModal';
import DdayModal from './DdayModal';
import MemoModal from './MemoModal';
import { useRouter } from 'next/router';

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
      .statusbtn {
      }
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
  ddayModal: () => void;
  statusModal: () => void;
}
interface ICardContentProps {
  ddetail: Iddetail;
  memoModal: () => void;
}

const CardHeader = ({ ddetail, spread, setSpread, ddayModal, statusModal }: ICardHeaderProps) => {
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
        <button onClick={ddayModal}>
          {ddetail.ddayName}: {ddetail.nextDate}
        </button>
        <button className="statusbtn" onClick={statusModal}>
          {ddetail.status}
        </button>
      </div>
    </div>
  );
};

const CardContent = ({ ddetail, memoModal }: ICardContentProps) => {
  const router = useRouter();

  return (
    <div>
      <div>메모</div>
      <div className="memo" onClick={memoModal}>
        {ddetail.memo}
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
        <button
          onClick={() => {
            router.push(
              {
                pathname: '/jobrank',
                query: {
                  postingId: ddetail.postingId,
                  companyName: ddetail.companyName,
                  postingName: ddetail.postingName,
                  startTime: ddetail.startTime,
                  endTime: ddetail.endTime,
                },
              },
              '/jobrank'
            );
          }}
        >
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
  const [ddayModal, setDdayModal] = useState<boolean>(false);
  const [statusModal, setStatusModal] = useState<boolean>(false);
  const [memoModal, setMemoModal] = useState<boolean>(false);

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

  const modifyDday = async (dday: any) => {
    const data = {
      nextDate: dday.date,
      dDayName: dday.ddayName,
    };
    const res = await patchHistory(cardId, data);

    if (res.status === 'SUCCESS') {
      alert(res.message);
      getDetail();
    } else {
      console.log(res.message);
    }
  };
  const modifyStatus = async (status: IStatus) => {
    const data = {
      statusId: status.id,
    };
    const res = await patchHistory(cardId, data);

    if (res.status === 'SUCCESS') {
      alert(res.message);
      getDetail();
    } else {
      console.log(res.message);
    }
  };
  const modifyMemo = async (memoValue: string) => {
    const data = {
      memo: memoValue,
    };
    const res = await patchHistory(cardId, data);

    if (res.status === 'SUCCESS') {
      alert(res.message);
      getDetail();
    } else {
      console.log(res.message);
    }
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
          <CardHeader
            ddetail={detail}
            setSpread={headerClick}
            spread={spread}
            ddayModal={() => setDdayModal(true)}
            statusModal={() => setStatusModal(true)}
          />
          {spread && <CardContent ddetail={detail} memoModal={() => setMemoModal(true)} />}
        </div>
        {ddayModal && (
          <DdayModal
            close={() => setDdayModal(false)}
            result={(dday) => {
              modifyDday(dday);
            }}
          ></DdayModal>
        )}
        {statusModal && (
          <StatusModal
            close={() => setStatusModal(false)}
            result={(status) => {
              modifyStatus(status);
            }}
          ></StatusModal>
        )}
        {memoModal && (
          <MemoModal
            close={() => setMemoModal(false)}
            result={(memoValue) => modifyMemo(memoValue)}
            defaultValue={detail.memo}
          ></MemoModal>
        )}
      </DetailCardDiv>
    );
  }
};

export default CareerListItem;
