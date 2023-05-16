import styled, { keyframes } from 'styled-components';
import { useRouter } from 'next/router';
import Image from 'next/image';

const bounce = keyframes`
 		20% { transform: rotate(15deg); }	
	40% { transform: rotate(-10deg); }
	60% { transform: rotate(5deg); }	
	80% { transform: rotate(-5deg); }	
	100% { transform: rotate(0deg); }
`;

const Wrapper = styled.div`
  width: 100vw;
  height: 100vh;
  /* height: calc(var(--vh, 1vh) * 100); */
  background-color: ${(props) => props.theme.fontBlack};
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: relative;
  z-index: 10;

  .img-box {
    width: 50%;
    transform: rotate(-20deg);
    margin-bottom: 24px;
    animation: ${bounce} 2s ease infinite;
    display: flex;
    align-items: center;
    justify-content: center;
    /* height: 24px; */
    /* border: 1px solid red; */
  }

  .img-txt {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 50%;
  }
`;

const Button = styled.button`
  background-color: ${(props) => props.theme.primary};
  border-radius: 56px;
  font-size: 20px;
  color: ${(props) => props.theme.fontWhite};
  padding: 14px 56px;
  position: absolute;
  bottom: 56px;
`;

const Error = () => {
  const route = useRouter();
  return (
    <Wrapper>
      <div className="img-box">
        <Image src="/Icon/ErrorIcon.svg" alt="error" height={200} width={200}></Image>
      </div>
      <div className="img-txt">
        <Image src="/Icon/ErrorTextIcon.svg" alt="errortext" height={200} width={200}></Image>
      </div>
      <Button onClick={() => route.push('/')}>메인페이지로 이동</Button>
    </Wrapper>
  );
};

export default Error;
