import styled, { keyframes } from 'styled-components';
import MenuArrowIcon from '../../public/Icon/MenuArrowIcon.svg';
import Link from 'next/link';
import { IRankMenuSelectProps } from './ICommon';

const moveUp = keyframes`
 from{
    transform: translateY(180px);
    opacity: 0;
  }
  to{
    transform: translateY(0px);
    opacity: 1;
  }
`;

const DarkBg = styled.div`
  position: fixed;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: ${(props) => props.theme.modalGray};
`;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 20%;
  position: fixed;
  bottom: 0;
  background-color: ${(props) => props.theme.bgWhite};
  animation: 0.4s ease-in-out 0s ${moveUp};

  .git,
  .boj {
    background-color: transparent;
    width: 100%;
    padding: 8px 0px;
    font-size: 20px;
    font-weight: 500;
  }
`;

const RankMenuSelectModal = (props: IRankMenuSelectProps) => {
  return (
    <>
      <DarkBg onClick={props.onClick} />
      <Wrapper>
        <button className="git" onClick={() => props.onChangeCurRank(0)}>
          깃허브 랭킹
        </button>
        <button className="boj" onClick={() => props.onChangeCurRank(1)}>
          백준 랭킹
        </button>
      </Wrapper>
    </>
  );
};

export default RankMenuSelectModal;
