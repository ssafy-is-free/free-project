import styled from 'styled-components';
import CareerListItem from './CareerListItem';
import { useState, useEffect } from 'react';
// import CustomNav from '../common/CustomNav';
import { Spinner } from '../common/Spinner';
import { deleteHistory, getHistory } from '@/pages/api/careerAxios';
import { IHistory, ICareerListProps } from './ICareer';
import Swal from 'sweetalert2';
import CareerNav from './CareerNav';
import CareerListItemDefault from './CareerListItemDefault';

const CareerListDiv = styled.div`
  .header {
    background-color: ${(props) => props.theme.primary};
    height: 2.5rem;
    padding: 1rem;
    padding-top: 3rem;
    padding-bottom: 2rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
    .title {
      color: ${(props) => props.theme.secondary};
      font-size: large;
    }
    img {
      height: 2rem;
    }

    .deleteBtn {
      color: ${(props) => props.theme.secondary};
      font-size: larger;
      font-weight: bold;
    }
  }
  .nav {
    margin: 1rem;
  }
  .content {
    margin: 1rem;
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

const progressStatus = ['1', '2', '4', '6', '8', '10'];
const doneStatus = ['3', '5', '7', '9', '11', '12'];

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
      Swal.fire({
        text: '삭제 완료',
        icon: 'success',
      });
      setDelMode(false);
      getCareerData();
    } else {
      Swal.fire({
        title: 'Error!',
        text: res.message,
        icon: 'error',
      });
    }
  };

  const afterChangeStatus = (idx: number) => {
    const idxString = idx.toString();
    if (progressStatus.includes(idxString)) {
      setSelectedIdx(0);
    } else {
      setSelectedIdx(1);
    }
  };

  useEffect(() => {
    getCareerData();
  }, []);

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
        <div className="title">취업지원이력</div>
        {delMode ? (
          <img src="/Icon/CheckIcon.svg" alt="" onClick={delapi} />
        ) : (
          <img src="/Icon/AddNewIcon.svg" alt="" onClick={openNew} />
        )}
      </div>
      <div className="nav">
        <CareerNav selectedIdx={selectedIdx} lists={['진행중', '종료']} selectIdx={selectIdx}></CareerNav>
      </div>
      {progressData && doneData ? (
        <div className="content">
          {selectedIdx === 0 ? (
            <div className="cardlist">
              {progressData.length > 0 ? (
                progressData.map((item: IHistory) => (
                  <div className="card" key={item.jobHistoryId}>
                    <CareerListItem
                      cardId={item.jobHistoryId}
                      dDay={item.dday}
                      delMode={delMode}
                      delCheck={(isChecked: boolean) => {
                        checkedItemHandler(item.jobHistoryId, isChecked);
                      }}
                      updateList={getCareerData}
                      category={(idx: number) => afterChangeStatus(idx)}
                    ></CareerListItem>
                  </div>
                ))
              ) : (
                <CareerListItemDefault category={0}></CareerListItemDefault>
              )}
            </div>
          ) : (
            <div className="cardlist">
              {doneData.length > 0 ? (
                doneData.map((item: IHistory) => (
                  <div className="card" key={item.jobHistoryId}>
                    {/* {delMode && CheckBox()} */}
                    <CareerListItem
                      cardId={item.jobHistoryId}
                      dDay={item.dday}
                      delMode={delMode}
                      delCheck={(isChecked: boolean) => {
                        checkedItemHandler(item.jobHistoryId, isChecked);
                      }}
                      updateList={getCareerData}
                      category={(idx: number) => afterChangeStatus(idx)}
                    ></CareerListItem>
                  </div>
                ))
              ) : (
                <CareerListItemDefault category={1}></CareerListItemDefault>
              )}
            </div>
          )}
        </div>
      ) : (
        <Spinner></Spinner>
      )}
    </CareerListDiv>
  );
};

export default CareerList;
