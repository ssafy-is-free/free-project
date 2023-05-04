import styled, { css } from 'styled-components';
import { IRankMenuProps } from './ICommon';
import { useState } from 'react';

const Wrapper = styled.div<{ curMenu: number }>`
  display: flex;
  align-items: center;
  /* background-color: ${(props) => props.theme.menuBg}; */
  font-weight: bold;
  font-size: 16px;
  width: calc(100% - 64px);
  border-radius: 8px;
  padding: 12px 8px;
  margin-top: 16px;
  position: relative;

  .git-menu {
    width: 50%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
    z-index: 1;

    ${(props) => {
      if (props.curMenu == 1) {
        return css`
          color: ${(props) => props.theme.fontGray};
        `;
      } else {
        return css`
          color: ${(props) => props.theme.fontDarkGray};
        `;
      }
    }};
  }

  .boj-menu {
    width: 50%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
    z-index: 1;

    ${(props) => {
      if (props.curMenu == 0) {
        return css`
          color: ${(props) => props.theme.fontGray};
        `;
      } else {
        return css`
          color: ${(props) => props.theme.fontDarkGray};
        `;
      }
    }};
  }

  .selected-menu {
    background-color: ${(props) => props.theme.primary};
    width: calc(50% - 6px);
    /* height: calc(100% - 12px); */
    height: 4px;
    position: absolute;
    bottom: 0;
    z-index: 1;
    border-radius: 8px;
    transition: all 0.2s ease-in 0s;

    ${(props) => {
      if (props.curMenu == 1) {
        return css`
          transform: translateX(calc(100% - 6px));
        `;
      }
    }};
  }
`;

const RankMenu2 = (props: IRankMenuProps) => {
  const [curMenu, setCurMenu] = useState<number>(props.curRank);

  return (
    <Wrapper curMenu={curMenu}>
      <div
        className="git-menu"
        onClick={() => {
          if (props.onChangeCurRank && props.setNoScroll) {
            props.onChangeCurRank(0);
            setCurMenu(0);
            props.setNoScroll(false);
          }
        }}
      >
        깃허브
      </div>
      <div
        className="boj-menu"
        onClick={() => {
          if (props.onChangeCurRank && props.setNoScroll) {
            props.onChangeCurRank(1);
            setCurMenu(1);
            props.setNoScroll(false);
          }
        }}
      >
        백준
      </div>
      <div className="selected-menu"></div>
    </Wrapper>
  );
};

export default RankMenu2;
