import styled from 'styled-components';
import CareerCard from './CareerCard';

const CareerHeaderDiv = styled.div`
  margin: 1rem;
  display: flex;
  justify-content: space-between;
  height: 2.5rem;
  img {
  }
`;

const CareerList = () => {
  return (
    <div>
      <CareerHeaderDiv>
        <img src="/Icon/TrashIcon.svg" alt="" />
        <img src="/Icon/AddIcon.svg" alt="" />
      </CareerHeaderDiv>
      <CareerCard cardId={1}></CareerCard>
      <CareerCard cardId={2}></CareerCard>
      <CareerCard cardId={3}></CareerCard>
    </div>
  );
};

export default CareerList;
