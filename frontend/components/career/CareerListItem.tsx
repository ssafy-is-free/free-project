import { useEffect, useState } from 'react';
import styled, { keyframes } from 'styled-components';
import { Spinner } from '../common/Spinner';
import CheckBox from './CheckBox';
import StatusModal from './ModalStatus';
import DdayModal from './ModalDday';
import MemoModal from './ModalMemo';
import { getHistoryDtail, patchHistory } from '@/pages/api/careerAxios';
import {
  IHistoryDetail,
  ICareerListItemProps,
  ICardHeaderProps,
  ICardContentProps,
  ICareerStatus,
  IDefaultDate,
} from './ICareer';
import { useRouter } from 'next/router';

import Swal from 'sweetalert2';

const RotateUp = keyframes`
  0% {
    transform:rotate(0deg);
  }
  100%{
    transform:rotate(180deg);
  }
`;
const RotateDown = keyframes`
  0% {
    transform:rotate(180deg);
  }
  100%{
    transform:rotate(0deg);
  }
`;
const SmoothAppear = keyframes`
  from {
    opacity: 0;
    transform: translateY(-5%);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
`;

interface ISpreadImgProps {
  spread: boolean | null;
}
const SpreadImg = styled.img<ISpreadImgProps>`
  animation: ${(props) => (props.spread === null ? null : props.spread ? RotateUp : RotateDown)} 0.1s linear forwards;
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

const DetailCardDiv = styled.div`
  width: 100%;
  display: flex;
  animation: ${SmoothAppear} 0.5s;
  /* filter: drop-shadow(2px 2px 4px rgba(0, 0, 0, 0.25)); */

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

    .fadein {
      animation: ${SmoothAppear} 0.5s;
    }
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
    .cardcontent {
      animation: ${SmoothAppear} 0.5s;
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

const CardHeader = ({ ddetail, dDay, spread, setSpread, ddayModal, statusModal }: ICardHeaderProps) => {
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
    <div onClick={setSpread}>
      {spread && <div className="fadein">{ddetail.postingName}</div>}
      <div className="title">
        <h2>{ddetail.companyName}</h2>
        <SpreadImg spread={spread} className="spreadIcon" src="/Icon/FilterArrowIcon.svg" alt="" onClick={setSpread} />
      </div>
      {spread && (
        <div className="fadein">
          {ddetail.startTime} ~ {ddetail.endTime}
        </div>
      )}
      <div className="flexDiv">
        <button
          onClick={(event) => {
            event.stopPropagation();
            ddayModal();
          }}
        >
          {ddetail.ddayName}: {ddetail.nextDate === '1996-11-22' ? '미정' : ddetail.nextDate}
        </button>
        <StatusButton
          className="statusbtn"
          colorProp={status}
          onClick={(event) => {
            event.stopPropagation();
            statusModal();
          }}
        >
          {ddetail.status}
        </StatusButton>
      </div>
    </div>
  );
};

const CardContent = ({ ddetail, memoModal }: ICardContentProps) => {
  const router = useRouter();

  return (
    <div className="cardcontent">
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

const CareerListItem = ({ cardId, dDay, delMode, delCheck, updateList, category }: ICareerListItemProps) => {
  const [spread, setSpread] = useState<boolean | null>(null);
  const [detail, setDetail] = useState<IHistoryDetail | null>(null);
  const [ddayModal, setDdayModal] = useState<boolean>(false);
  const [statusModal, setStatusModal] = useState<boolean>(false);
  const [memoModal, setMemoModal] = useState<boolean>(false);
  const [defaultDate, setDefaultDate] = useState<IDefaultDate>();

  const getDetail = async () => {
    const res = await getHistoryDtail(cardId);
    if (res.status == 'SUCCESS') {
      setDetail(res.data);
    } else {
    }
  };

  const modifyDday = async (dday: any) => {
    const data = {
      nextDate: dday.date,
      ddayName: dday.ddayName,
    };
    const res = await patchHistory(cardId, data);

    if (res.status === 'SUCCESS') {
      Swal.fire({
        text: '다음일정 변경완료',
        icon: 'success',
      });
      getDetail();
    } else {
      Swal.fire({
        title: 'Error!',
        text: res.message,
        icon: 'error',
      });
    }
  };

  const modifyStatus = async (status: ICareerStatus) => {
    const data = {
      statusId: status.id,
    };
    const res = await patchHistory(cardId, data);

    if (res.status === 'SUCCESS') {
      Swal.fire({
        text: '상태 변경완료',
        icon: 'success',
      });
      updateList();
      getDetail();
      // 종료 쪽으로 이동
      category(status.id);
    } else {
      Swal.fire({
        title: 'Error!',
        text: res.message,
        icon: 'error',
      });
    }
  };

  const modifyMemo = async (memoValue: string) => {
    const data = {
      memo: memoValue,
    };
    const res = await patchHistory(cardId, data);

    if (res.status === 'SUCCESS') {
      Swal.fire({
        text: '메모 수정완료',
        icon: 'success',
      });
      getDetail();
    } else {
      Swal.fire({
        title: 'Error!',
        text: res.message,
        icon: 'error',
      });
    }
  };

  useEffect(() => {
    getDetail();
  }, []);

  // dday 수정 할때 기본 날짜 입력값 갱신
  useEffect(() => {
    if (detail) {
      const date = detail.nextDate;
      let year, month, day;
      [year, month, day] = date.split('-');
      if (year === '1996') {
        const today = new Date();
        year = today.getFullYear() - 2020;
        month = today.getMonth();
        day = today.getDate() - 1;
        setDefaultDate({
          year,
          month,
          day,
        });
      } else {
        setDefaultDate({
          year: parseInt(year) - 2020,
          month: parseInt(month) - 1,
          day: parseInt(day) - 1,
        });
      }
    }
  }, [detail]);

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
            dDay={dDay}
            setSpread={() => setSpread(!spread)}
            spread={spread}
            ddayModal={() => setDdayModal(true)}
            statusModal={() => setStatusModal(true)}
          />
          {spread && <CardContent ddetail={detail} memoModal={() => setMemoModal(true)} />}
        </div>
        {ddayModal && defaultDate && (
          <DdayModal
            close={() => setDdayModal(false)}
            result={(dday) => {
              modifyDday(dday);
            }}
            defaultDate={defaultDate}
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
