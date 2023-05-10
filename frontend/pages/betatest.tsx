import CareerList from '@/components/career/CareerList';
import NewCareer from '@/components/career/NewCareer';
import { useState } from 'react';
import styled from 'styled-components';

const TestDiv = styled.div`
  margin-bottom: max(4rem, 10vh);
`;

const Test = () => {
  const [newOpen, setNewOpen] = useState<boolean>(false);
  const closeNew = () => {
    setNewOpen(false);
  };
  const openNew = () => {
    setNewOpen(true);
  };
  return (
    <TestDiv>
      <CareerList openNew={openNew}></CareerList>
      {newOpen && <NewCareer close={closeNew}></NewCareer>}
    </TestDiv>
  );
};

export default Test;
