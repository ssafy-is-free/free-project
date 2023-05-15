import styled, { css, keyframes } from 'styled-components';
import Mobile1 from '../public/Icon/Mobile1.svg';
import Mobile2 from '../public/Icon/Mobile3.svg';
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
    top: 20%;
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

    .subtext1 {
      font-size: 22px;
      line-height: 32px;
      color: ${(props) => props.theme.fontDarkGray};
    }

    .subtext2 {
      font-size: 24px;
      line-height: 32px;
      color: ${(props) => props.theme.fontDarkGray};
    }

    .space {
      height: 48px;
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
        : props.selected == 1
        ? css`
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
          `
        : css`
            top: 15%;
            right: 20%;
            z-index: 1;
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
        : props.selected == 0
        ? css`
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
  const [selected, setSelected] = useState<number>(-1);

  return (
    <Wrapper selected={selected}>
      <div className="circle"></div>
      <div className="text-box">
        <div className="header">
          <span>CH</span>ECK <br />
          YOUR <span>PO</span>SITION
        </div>
        <div className="subtext1">당신의 포지션을 체크하고, 취업관리도 해보세요!</div>
        <div className="subtext1">CHPO는 모바일만 지원합니다.</div>
        <div className="subtext1">브라우저 해상도 조절 또는 모바일 디바이스로 접속해주세요.</div>
        <div className="space"></div>
        {selected === 1 ? (
          <>
            <div className="subtext2">취업 관리 페이지에서 지원하는 회사 정보를 관리해보세요.</div>
            <div className="subtext2">취업 관리도 하고, 지원자들과 비교도 해보세요.</div>
          </>
        ) : (
          <>
            <div className="subtext2">랭킹 페이지에서는 깃허브, 백준 순위를 확인할 수 있어요.</div>
            <div className="subtext2">해당 유저를 클릭해서 유저 정보를 확인해보세요.</div>
          </>
        )}
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
