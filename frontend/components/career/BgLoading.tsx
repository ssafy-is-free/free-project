import styled from 'styled-components';
import { Spinner } from '../common/Spinner';

const Wrapper = styled.div`
  position: fixed;
  z-index: 5;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: ${(props) => props.theme.modalGray};
  display: flex;
  justify-content: center;
  align-items: center;
`;

const BgLoading = () => {
  return (
    <Wrapper>
      <Spinner></Spinner>
    </Wrapper>
  );
};

export default BgLoading;
