import styled, { css } from 'styled-components';
import { IRankMenuProps } from './ICommon';
import { useState } from 'react';

const Wrapper = styled.div<{ curMenu: boolean }>`
  display: flex;
  align-items: center;
  background-color: ${(props) => props.theme.menuBg};
  font-weight: bold;
  font-size: 16px;
  width: calc(100% - 64px);
  border-radius: 8px;
  padding: 12px 8px;
  margin-top: 16px;
  position: relative;
  color: ${(props) => props.theme.fontDarkGray};

  .git-menu {
    width: 50%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
    z-index: 2;
  }
  .boj-menu {
    width: 50%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
    z-index: 2;
  }

  .selected-menu {
    background-color: ${(props) => props.theme.bgWhite};
    width: calc(50% - 6px);
    height: calc(100% - 12px);
    position: absolute;

    z-index: 1;
    border-radius: 8px;
    transition: all 0.2s ease-in 0s;

    ${(props) => {
      if (!props.curMenu) {
        return css`
          transform: translateX(calc(100% - 6px));
        `;
      }
    }};
  }
`;

const RankMenu2 = (props: IRankMenuProps) => {
  const [curMenu, setCurMenu] = useState<boolean>(false);

  return (
    <Wrapper curMenu={curMenu}>
      <div
        className="git-menu"
        onClick={() => {
          if (props.onChangeCurRank) {
            props.onChangeCurRank(0);
            setCurMenu(true);
          }
        }}
      >
        깃허브
      </div>
      <div
        className="boj-menu"
        onClick={() => {
          if (props.onChangeCurRank) {
            props.onChangeCurRank(1);
            setCurMenu(false);
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
