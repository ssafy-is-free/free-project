import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { Spinner } from '../common/Spinner';
import CheckBox from './CheckBox';
import StatusModal from './ModalStatus';
import DdayModal from './ModalDday';
import MemoModal from './ModalMemo';
import { getHistoryDtail, patchHistory } from '@/pages/api/careerAxios';
import { IHistoryDetail, ICareerListItemProps, ICardHeaderProps, ICardContentProps, ICareerStatus } from './ICareer';

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
      align-items: center;
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

interface IStatusBtnProps {
  colorProp: string;
}
const StatusButton = styled.button<IStatusBtnProps>`
  background-color: ${(props) =>
    props.colorProp === 'green'
      ? props.theme.stateGreen
      : props.colorProp === 'red'
      ? props.theme.stateRed
      : props.theme.stateIng} !important;
  color: ${(props) =>
    props.colorProp === 'green'
      ? props.theme.stateGreenFont
      : props.colorProp === 'red'
      ? props.theme.stateRedFont
      : props.theme.stateIngFont} !important;
`;

const CardHeader = ({ ddetail, spread, setSpread, ddayModal, statusModal }: ICardHeaderProps) => {
  const statusColor = () => {
    const word = ddetail.status.slice(-2);
    if (ddetail.status === '최종 합격') {
      return 'green';
    } else if (word === '접수') {
      return 'yellow';
    } else if (word === '탈락') {
      return 'red';
    } else {
      return 'green';
    }
  };

  const status = statusColor();

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
        <StatusButton className="statusbtn" colorProp={status} onClick={statusModal}>
          {ddetail.status}
        </StatusButton>
      </div>
    </div>
  );
};

const CardContent = ({ ddetail, memoModal }: ICardContentProps) => {
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
        <button>
          <div>지원자 정보</div>
          <div>보러가기</div>
        </button>
      </div>
    </div>
  );
};

const CareerListItem = ({ cardId, dDay, delMode, delCheck, updateList }: ICareerListItemProps) => {
  const [spread, setSpread] = useState<boolean>(false);
  const [detail, setDetail] = useState<IHistoryDetail | null>(null);
  const [ddayModal, setDdayModal] = useState<boolean>(false);
  const [statusModal, setStatusModal] = useState<boolean>(false);
  const [memoModal, setMemoModal] = useState<boolean>(false);

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
      ddayName: dday.ddayName,
    };
    const res = await patchHistory(cardId, data);

    if (res.status === 'SUCCESS') {
      alert(res.message);
      getDetail();
    } else {
      console.log(res.message);
    }
  };

  const modifyStatus = async (status: ICareerStatus) => {
    const data = {
      statusId: status.id,
    };
    const res = await patchHistory(cardId, data);

    if (res.status === 'SUCCESS') {
      alert(res.message);
      updateList();
      // getDetail();
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
        <div className="item">
          <Spinner></Spinner>
        </div>
      </DetailCardDiv>
    );
  } else {
    return (
      <DetailCardDiv>
        {delMode && <CheckBox handeler={(bChecked: boolean) => delCheck(bChecked)}></CheckBox>}
        <div className="item">
          <CardHeader
            ddetail={detail}
            setSpread={() => setSpread(!spread)}
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
