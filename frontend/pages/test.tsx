import CareerCard from '@/components/career/CareerCard';
import CareerList from '@/components/career/CareerList';
import NewCareer from '@/components/career/NewCareer';
import styled from 'styled-components';

const TestDiv = styled.div`
  margin-bottom: max(4rem, 10vh);
`;

const Test = () => {
  return (
    <TestDiv>
      <CareerList></CareerList>
      {/* <NewCareer></NewCareer> */}
    </TestDiv>
  );
};

export default Test;
