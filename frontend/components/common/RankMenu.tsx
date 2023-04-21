import styled from 'styled-components';
import MenuArrowIcon from '../../public/Icon/MenuArrowIcon.svg';

interface IProps {
  onClick: () => void;
  curRank: number;
}

const Wrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  color: ${(props) => props.theme.fontWhite};
  font-weight: bold;
  font-size: 20px;
  width: 100%;
  height: 100px;
  padding: 48px 0px 24px;

  .menu {
    cursor: pointer;
    display: flex;
    align-items: center;
  }

  p {
    margin-right: 8px;
  }
`;

const RankMenu = (props: IProps) => {
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
