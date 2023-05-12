import styled from 'styled-components';
import { Spinner } from '../common/Spinner';
// import { DarkBg } from './SCareer';

// const BgLoadingDiv = styled(DarkBg)`
//   display: flex;
//   justify-content: center;
//   align-items: center;
// `;
const BgLoadingDiv = styled.div`
  position: fixed;
  z-index: 10;
  width: 100vw;
  height: 100vh;
  background-color: ${(props) => props.theme.modalGray};
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
