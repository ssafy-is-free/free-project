import styled, { keyframes } from 'styled-components';
import DevelopIcon from '../public/Icon/DevelopIcon.svg';

const upDown = keyframes`
    from{
    transform: translateY(0px);
  }
  to{
    transform: translateY(-10px);
  }
`;

const Wrapper = styled.div`
  width: 100vw;
  height: 100vh;
  /* height: calc(var(--vh, 1vh) * 100); */
  background-color: ${(props) => props.theme.primary};
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: relative;
  z-index: 1;
  font-size: 1.2rem;
  color: ${(props) => props.theme.fontWhite};
  text-align: center;
  line-height: 1.8rem;
`;

const StyledDevelopIcon = styled(DevelopIcon)`
  margin-bottom: 24px;
  animation: 1.4s infinite ease-in-out alternate ${upDown};
`;

const Temp = () => {
  return (
    <Wrapper>
      <StyledDevelopIcon />
      현재 개발 중인 페이지 입니다. <br />
      2차 배포 때 출시 예정
    </Wrapper>
  );
};

export default Temp;
