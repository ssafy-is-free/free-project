import CareerList from '@/components/career/CareerList';
import DatePicker from '@/components/career/DatePicker';
import DdayModal from '@/components/career/DdayModal';
import NewCareer from '@/components/career/NewCareer';
import NewCareerModal from '@/components/career/NewCareerModal';
import { useState } from 'react';
import styled from 'styled-components';

const TestDiv = styled.div`
  margin-bottom: max(4rem, 10vh);
`;

const Test = () => {
  const [newOpen, setNewOpen] = useState<boolean>(false);
  const [modal, setModal] = useState<boolean>(false);
  const closeNew = () => {
    setNewOpen(false);
  };
  const openNew = () => {
    setNewOpen(true);
  };
  const closeModal = () => {
    setModal(false);
  };
  const openModal = () => {
    setModal(true);
  };
  const updateDate = (date: string) => {
    console.log(date);
  };
  return (
    <TestDiv>
      <CareerList openNew={openNew}></CareerList>
      {newOpen && <NewCareer close={closeNew}></NewCareer>}

      {/* {<NewCareerModal></NewCareerModal>} */}
      {/* <DatePicker updateDate={updateDate}></DatePicker> */}
      {/* <DdayModal></DdayModal> */}
    </TestDiv>
  );
};

export default Test;
