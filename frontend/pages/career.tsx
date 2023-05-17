import CareerList from '@/components/career/CareerList';
// import NewCareer from '@/components/career/NewCareer';
import { useState } from 'react';
import styled from 'styled-components';
import dynamic from 'next/dynamic';

const NewCareer = dynamic(() => import('@/components/career/NewCareer'), { ssr: false });

const CareerDiv = styled.div`
  margin-bottom: max(4rem, 10vh);
`;

const Career = () => {
  const [newOpen, setNewOpen] = useState<boolean>(false);
  return (
    <CareerDiv>
      <CareerList openNew={() => setNewOpen(true)}></CareerList>
      {newOpen && <NewCareer close={() => setNewOpen(false)}></NewCareer>}
    </CareerDiv>
  );
};

export default Career;
