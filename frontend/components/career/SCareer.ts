import styled, { keyframes } from 'styled-components';

export const fadeIn = keyframes`
from {
    opacity: 0;
}
to {
    opacity: 1;
}
`;

export const fadeIn2 = keyframes`
  0% {
    transform: translateX(500px);
    opacity: 0;
  }
  100% {
    transform: translateX(0);
    opacity: 1;
  }
`;

export const fadeIn2Reverse = keyframes`
  0% {
    transform: translateX(0);
    opacity: 1;
  }
  100% {
    transform: translateX(500px);
    opacity: 0;
  }
`;

export const DarkBg = styled.div`
  position: fixed;
  z-index: 10;
  width: 100vw;
  height: 100vh;
  background-color: ${(props) => props.theme.modalGray};
  animation: ${fadeIn} 0.5s;
`;
