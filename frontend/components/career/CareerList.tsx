import styled from 'styled-components';
import CareerCard from './CareerCard';
import { useState } from 'react';
import CustomNav from '../common/CustomNav';
import NewCareer from './NewCareer';

const CareerListDiv = styled.div`
  margin: 1rem;

  .header {
    height: 2.5rem;
    margin-top: 2rem;
    margin-bottom: 2rem;
    display: flex;
    justify-content: space-between;
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

const CheckBox = () => {
  return (
    <div>
      <div>check</div>
    </div>
  );
};

const idList = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
const idList2 = [1, 2, 3];

interface ICareerList {
  openNew: () => void;
}

const CareerList = ({ openNew }: ICareerList) => {
  const [delMode, setDelMode] = useState<boolean>(false);
  const [list, setList] = useState(idList2);
  return (
    <CareerListDiv>
      <div className="header">
        <img
          src="/Icon/TrashIcon.svg"
          alt=""
          onClick={() => {
            // setDelMode(!delMode);
            setList(idList);
          }}
        />
        <img src="/Icon/AddIcon.svg" alt="" onClick={openNew} />
      </div>
      <CustomNav lists={['진행중', '종료']} selectIdx={() => {}}></CustomNav>
      <div className="cardlist">
        {list.map((id: number) => (
          <div className="card" key={id}>
            {delMode && CheckBox()}
            <CareerCard cardId={id}></CareerCard>
          </div>
        ))}
      </div>
    </CareerListDiv>
  );
};

export default CareerList;
