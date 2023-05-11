import styled from 'styled-components';
import { Spinner } from '../common/Spinner';
import { DarkBg } from './ICareer';

const BgLoadingDiv = styled(DarkBg)`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const BgLoading = () => {
  return (
    <BgLoadingDiv>
      <Spinner></Spinner>
    </BgLoadingDiv>
  );
};

export default BgLoading;
