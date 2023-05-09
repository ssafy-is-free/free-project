import styled, { css } from 'styled-components';
import { ISubMenuProps } from './IJobrank';

const Wrapper = styled.div<{ submenu: number }>`
  position: absolute;
  top: -28px;
  width: calc(100% - 64px);
  height: 56px;

  .submenu {
    display: flex;
    align-items: center;
    border-radius: 8px;
    padding: 12px 8px;
    position: relative;
    width: 100%;
    height: 100%;
    background-color: ${(props) => props.theme.bgWhite};
    box-shadow: 4px 4px 20px #00000026;

    .all-info {
      width: 50%;
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
      position: relative;
      z-index: 1;

      ${(props) => {
        if (props.submenu == 1) {
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

    .compare-info {
      width: 50%;
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
      position: relative;
      z-index: 1;

      ${(props) => {
        if (props.submenu == 0) {
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
      background-color: #00000012;
      width: calc(50% - 6px);
      height: calc(100% - 12px);
      position: absolute;
      bottom: 6px;
      z-index: 0;
      border-radius: 8px;
      transition: all 0.2s ease-in 0s;

      ${(props) => {
        if (props.submenu == 1) {
          return css`
            transform: translateX(calc(100% - 6px));
          `;
        }
      }};
    }
  }
`;

const SubMenu = (props: ISubMenuProps) => {
  return (
    <Wrapper submenu={props.submenu}>
      <div className="submenu">
        <div
          className="all-info"
          onClick={() => {
            props.setSubmenu(0);
          }}
        >
          전체 지원자
        </div>
        <div
          className="compare-info"
          onClick={() => {
            props.setSubmenu(1);
          }}
        >
          나와 비교
        </div>
        <div className="selected-menu"></div>
      </div>
    </Wrapper>
  );
};

export default SubMenu;
