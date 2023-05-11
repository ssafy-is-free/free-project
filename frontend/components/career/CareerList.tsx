import styled from 'styled-components';
import CareerListItem from './CareerListItem';
import { useState, useEffect } from 'react';
import CustomNav from '../common/CustomNav';
import { Spinner } from '../common/Spinner';
import { deleteHistory, getHistory } from '@/pages/api/careerAxios';

const CareerListDiv = styled.div`
  margin: 1rem;

  .header {
    height: 2.5rem;
    margin-top: 2rem;
    margin-bottom: 2rem;
    display: flex;
    justify-content: space-between;
    align-items: center;

    img {
      height: 2.5rem;
    }

    .deleteBtn {
      color: ${(props) => props.theme.primary};
      font-size: larger;
      font-weight: bold;
    }
  }
  .cardlist {
    margin-top: 1rem;
    .card {
      width: 100%;
      margin-bottom: 1rem;
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
`;

interface IHistory {
  jobHistoryId: number;
  companyName: string;
  dDayName: string;
  nextDate: string;
  dDay: string;
  status: string;
}

const progressStatus = ['1', '2', '4', '6', '8', '10'];
const doneStatus = ['3', '5', '7', '9', '11', '12'];

interface ICareerListProps {
  openNew: () => void;
}

const CareerList = ({ openNew }: ICareerListProps) => {
  const [delMode, setDelMode] = useState<boolean>(false);
  const [progressData, setProgressData] = useState<IHistory[] | null>(null);
  const [doneData, setDoneData] = useState<IHistory[] | null>(null);
  const [selectedIdx, setSelectedIdx] = useState<number>(0);
  const [checkedItems, setCheckedItems] = useState<Set<number>>(new Set());

  const checkedItemHandler = (jobHistoryId: number, isChecked: boolean) => {
    const checkeds = new Set(checkedItems);

    if (isChecked) {
      checkeds.add(jobHistoryId);
    } else {
      checkeds.delete(jobHistoryId);
    }

    setCheckedItems(checkeds);
  };

  const getCareerData = async () => {
    const res = await getHistory(progressStatus);
    const res2 = await getHistory(doneStatus);
    setProgressData(res.data);
    setDoneData(res2.data);
  };

  const selectIdx = (idx: number) => {
    setSelectedIdx(idx);
  };

  const delapi = async () => {
    const res = await deleteHistory(Array.from(checkedItems));
    if (res.status === 'SUCCESS') {
      alert(res.message);
      setDelMode(false);
      getCareerData();
    } else {
      alert(res.message);
    }
  };

  useEffect(() => {
    getCareerData();
  }, []);

  if (!progressData) {
    return <Spinner></Spinner>;
  } else if (!doneData) {
    return <Spinner></Spinner>;
  } else {
    return (
      <CareerListDiv>
        <div className="header">
          <img
            src="/Icon/TrashIcon.svg"
            alt=""
            onClick={() => {
              setDelMode(!delMode);
              setCheckedItems(new Set());
            }}
          />
          {delMode ? (
            <div className="deleteBtn" onClick={delapi}>
              삭제하기
            </div>
          ) : (
            <img src="/Icon/AddIcon.svg" alt="" onClick={openNew} />
          )}
        </div>
        <CustomNav lists={['진행중', '종료']} selectIdx={selectIdx} defaultIdx={0}></CustomNav>
        {selectedIdx === 0 ? (
          <div className="cardlist">
            {progressData.map((item: IHistory) => (
              <div className="card" key={item.jobHistoryId}>
                <CareerListItem
                  cardId={item.jobHistoryId}
                  dDay={item.dDay}
                  delMode={delMode}
                  delCheck={(isChecked: boolean) => {
                    checkedItemHandler(item.jobHistoryId, isChecked);
                  }}
                ></CareerListItem>
              </div>
            ))}
          </div>
        ) : (
          <div className="cardlist">
            {doneData.map((item: IHistory) => (
              <div className="card" key={item.jobHistoryId}>
                {/* {delMode && CheckBox()} */}
                <CareerListItem
                  cardId={item.jobHistoryId}
                  dDay={item.dDay}
                  delMode={delMode}
                  delCheck={(isChecked: boolean) => {
                    checkedItemHandler(item.jobHistoryId, isChecked);
                  }}
                ></CareerListItem>
              </div>
            ))}
          </div>
        )}
      </CareerListDiv>
    );
  }
};

export default CareerList;
