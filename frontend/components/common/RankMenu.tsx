import styled from 'styled-components';
import MenuArrowIcon from '../../public/Icon/MenuArrowIcon.svg';
import { IRankMenuProps } from './ICommon';

const Wrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  color: ${(props) => props.theme.fontWhite};
  font-weight: bold;
  font-size: 16px;
  width: 100%;
  height: 8vh;
  /* padding: 48px 0px 24px; */
  padding: 2vh 0px 2vh;

  .menu {
    cursor: pointer;
    display: flex;
    align-items: center;
  }

  p {
    margin-right: 8px;
  }
`;

const RankMenu = (props: IRankMenuProps) => {
  return (
    <Wrapper onClick={props.onClick}>
      <div className="menu">
        {props.curRank == 0 ? <p>깃허브 랭킹</p> : <p>백준 랭킹</p>}
        <MenuArrowIcon />
      </div>
    </Wrapper>
  );
};

export default RankMenu;
