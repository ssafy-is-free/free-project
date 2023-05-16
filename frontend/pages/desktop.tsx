import styled, { css, keyframes } from 'styled-components';
import Mobile1 from '../public/Icon/Mobile1.svg';
import Mobile2 from '../public/Icon/Mobile3.svg';
import QrIcon from '../public/Icon/QrIcon.svg';
import AppleIcon from '../public/Icon/AppleIcon.svg';
import DesktopIcon from '../public/Icon/DesktopIcon.svg';
import AndroidIcon from '../public/Icon/AndroidIcon.svg';
import { useEffect, useState } from 'react';

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
    top: 15%;
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
      display: flex;

      .qr-box {
        margin-top: 16px;
        width: 100px;
        height: 100px;
      }

      .menual-box {
        margin-top: 16px;
        margin-left: 24px;
        display: flex;
        flex-direction: column;

        .menual {
          display: flex;
          align-items: center;
          .device-icon {
            width: 20px;
            margin-right: 16px;
          }
          .device-text {
            font-size: 20px;
            color: ${(props) => props.theme.fontBlack};
          }
        }
      }
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
    width: 248px;
    position: absolute;

    ${(props) =>
      props.selected == 0
        ? css`
            top: 20%;
            right: 20%;
            z-index: 1;
            animation: ${front} 1s;
          `
        : props.selected == 1
        ? css`
            top: 12%;
            right: 10%;
            z-index: 0;
            filter: blur(2px);
            -webkit-filter: blur(2px);
            -moz-filter: blur(2px);
            -o-filter: blur(2px);
            /* transform: scale(0.9); */
            transition-delay: 0.5s;
            animation: ${back} 1s;
          `
        : css`
            top: 20%;
            right: 20%;
            z-index: 1;
          `};
  }

  .screen-box2 {
    cursor: pointer;
    width: 248px;
    position: absolute;
    ${(props) =>
      props.selected == 1
        ? css`
            top: 20%;
            right: 20%;
            z-index: 1;
            animation: ${front} 1s;
          `
        : props.selected == 0
        ? css`
            top: 12%;
            right: 10%;
            z-index: 0;
            filter: blur(2px);
            -webkit-filter: blur(2px);
            -moz-filter: blur(2px);
            -o-filter: blur(2px);
            /* transform: scale(0.9); */
            transition-delay: 0.5s;
            animation: ${back} 1s;
          `
        : css`
            top: 20%;
            right: 10%;
            z-index: 0;
            filter: blur(2px);
            -webkit-filter: blur(2px);
            -moz-filter: blur(2px);
            -o-filter: blur(2px);
            /* transform: scale(0.9); */
            transition-delay: 0.5s;
          `};
  }
`;

const front = keyframes`
  0%{
    top: 12%;
    right: 10%;
    z-index: 0;
    filter: blur(2px);
    -webkit-filter: blur(2px);
    -moz-filter: blur(2px);
    -o-filter: blur(2px);
    /* transform: scale(0.9); */
  }

  50%{
    top: 15%;
    right: 5%;
    z-index: 0;
    filter: blur(2px);
    -webkit-filter: blur(2px);
    -moz-filter: blur(2px);
    -o-filter: blur(2px);
    /* transform: scale(0.9); */
  }
  60%{
    z-index: 1;
    /* transform: scale(0.9); */
  }
  100%{
    top: 20%;
    right: 20%;
    z-index: 1;
  }
`;

const back = keyframes`
  0%{
    top: 20%;
    right: 20%;
    z-index: 1;
  }
  50%{
    top: 15%;
    right: calc(264px +    5%);
    z-index: 1;
    /* transform: scale(0.9); */
  }
  60%{
    z-index: 0;
    /* transform: scale(0.9); */
  }

  100%{
    top: 12%;
    right: 10%;
    z-index: 0;
    /* transform: scale(0.9); */
  }
`;

const Desktop = () => {
  const [selected, setSelected] = useState<number>(-1);

  useEffect(() => {
    const timer = setTimeout(() => {
      if (selected == 1) setSelected(0);
      else setSelected(1);
    }, 5000);
    return () => clearTimeout(timer);
  }, [selected]);

  return (
    <Wrapper selected={selected}>
      <div className="circle"></div>
      <div className="text-box">
        <div className="header">
          <span>CH</span>ECK <br />
          YOUR <span>PO</span>SITION
        </div>
        <div className="subtext1">당신의 포지션을 체크하고, 취업관리도 해보세요!</div>
        <div className="subtext1">CHPO는 모바일만 지원합니다. 아래 QR 코드로 접속해주세요.</div>
        <div className="subtext1">
          <div className="qr-box">
            <QrIcon />
          </div>
          <div className="menual-box">
            <div className="menual">
              <div className="device-icon">
                <DesktopIcon />
              </div>
              <div className="device-text">데스크탑 : F12 → 우측 상단 기기 툴바 변경(Ctrl+Shift+M)</div>
            </div>
            <div className="menual">
              <div className="device-icon">
                <AndroidIcon />
              </div>
              <div className="device-text">안드로이드 : QR 접속 → 메뉴 → 홈화면에 추가</div>
            </div>
            <div className="menual">
              <div className="device-icon">
                <AppleIcon />
              </div>
              <div className="device-text">IOS : QR 접속 → 공유하기 → 홈화면에 추가</div>
            </div>
          </div>
        </div>
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
