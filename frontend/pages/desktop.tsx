import styled, { css, keyframes } from 'styled-components';
import Mobile1 from '../public/Icon/Mobile1.svg';
import Mobile2 from '../public/Icon/Mobile2.svg';
import { useState } from 'react';

const Wrapper = styled.div<{ selected: number }>`
  width: 100vw;
  height: 100vh;
  background-color: ${(props) => props.theme.bgWhite};
  display: flex;
  flex-direction: row;
  align-items: center;
  /* justify-content: center; */
  position: relative;
  z-index: 10;
  overflow: hidden;

  .circle {
    width: 800px;
    height: 800px;
    border-radius: 50%;
    background-color: ${(props) => props.theme.secondary};
    position: absolute;
    top: -20%;
    right: -100px;
  }

  .text-box {
    width: 50%;
    position: absolute;
    top: 30%;
    left: 10%;
    display: flex;
    flex-direction: column;

    .header {
      font-size: 72px;
      font-weight: bold;
      margin-bottom: 32px;
      color: ${(props) => props.theme.fontBlack};
      span {
        font-weight: bold;
        color: ${(props) => props.theme.primary};
      }
    }

    .subtext {
      font-size: 24px;
      line-height: 32px;
      color: ${(props) => props.theme.fontDarkGray};
    }
  }

  .screen-box1 {
    cursor: pointer;
    width: 264px;
    position: absolute;
    ${(props) =>
      props.selected == 0
        ? css`
            top: 15%;
            right: 20%;
            z-index: 1;
            animation: ${front} 1s;
          `
        : css`
            top: 7%;
            right: 10%;
            z-index: 0;
            filter: blur(2px);
            -webkit-filter: blur(2px);
            -moz-filter: blur(2px);
            -o-filter: blur(2px);
            transform: scale(0.9);
            transition-delay: 0.5s;
            animation: ${back} 1s;
          `};
  }

  .screen-box2 {
    cursor: pointer;
    width: 264px;
    position: absolute;
    ${(props) =>
      props.selected == 1
        ? css`
            top: 15%;
            right: 20%;
            z-index: 1;
            animation: ${front} 1s;
          `
        : css`
            top: 7%;
            right: 10%;
            z-index: 0;
            filter: blur(2px);
            -webkit-filter: blur(2px);
            -moz-filter: blur(2px);
            -o-filter: blur(2px);
            transform: scale(0.9);
            transition-delay: 0.5s;
            animation: ${back} 1s;
          `};
  }
`;

const front = keyframes`
  0%{
    top: 7%;
    right: 10%;
    z-index: 0;
    filter: blur(2px);
    -webkit-filter: blur(2px);
    -moz-filter: blur(2px);
    -o-filter: blur(2px);
    transform: scale(0.9);
  }

 

  50%{
    top: 10%;
    right: 5%;
    z-index: 1;
    filter: blur(2px);
    -webkit-filter: blur(2px);
    -moz-filter: blur(2px);
    -o-filter: blur(2px);
    transform: scale(0.9);
  }
  100%{
    top: 15%;
    right: 20%;
    z-index: 1;
  }
`;

const back = keyframes`
  0%{
    top: 15%;
    right: 20%;
    z-index: 0;
  }
  50%{
    top: 10%;
    right: calc(264px +    5%);
    z-index: 0;
    transform: scale(0.9);
  }
  100%{
    top: 7%;
    right: 10%;
    z-index: 0;
    transform: scale(0.9);
  }
`;

const Desktop = () => {
  const [selected, setSelected] = useState<number>(0);

  return (
    <Wrapper selected={selected}>
      <div className="circle"></div>
      <div className="text-box">
        <div className="header">
          <span>CH</span>ECK <br />
          YOUR <span>PO</span>SITION
        </div>
        <div className="subtext">당신의 포지션을 체크하고, 취업관리도 해보세요!</div>
        <div className="subtext">CHPO는 모바일만 지원합니다.</div>
      </div>
      <div className="screen-box1" onClick={() => setSelected(0)}>
        <Mobile1 />
      </div>
      <div className="screen-box2" onClick={() => setSelected(1)}>
        <Mobile2 />
      </div>
    </Wrapper>
  );
};

export default Desktop;
