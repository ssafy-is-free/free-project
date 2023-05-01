import styled, { keyframes } from 'styled-components';
import LogoPrimary from '../public/Icon/LogoWhite.svg';
import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { splashCheck } from '@/redux/splashSlice';

const moveUp = keyframes`
 from{
    transform: translateY(100px);
    opacity: 0;
  }
  to{
    transform: translateY(0px);
    opacity: 1;
  }
`;

const Wrapper = styled.div`
  width: 100vw;
  /* height: 100vh; */
  height: calc(var(--vh, 1vh) * 100);
  background-color: ${(props) => props.theme.primary};
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  z-index: 999;
`;

const StyledLogoPrimary = styled(LogoPrimary)`
  animation: 1s cubic-bezier(0.4, 0, 0.2, 1) 0s ${moveUp};
`;

const Splash = () => {
  return (
    <Wrapper>
      <StyledLogoPrimary />
    </Wrapper>
  );
};

export default Splash;
