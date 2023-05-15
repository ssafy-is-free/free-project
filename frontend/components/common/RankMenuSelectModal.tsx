import styled, { keyframes } from 'styled-components';
import { IRankMenuSelectProps } from './ICommon';
import { useSelector } from 'react-redux';
import { RootState } from '@/redux';

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
  z-index: 10;
`;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 20%;
  position: fixed;
  z-index: 15;
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
    color: ${(props) => props.theme.fontBlack};
  }
`;

const RankMenuSelectModal = (props: IRankMenuSelectProps) => {
  // 백준 여부
  const isBoj = useSelector<RootState>((selector) => selector.authChecker.isBoj);

  return (
    <>
      <DarkBg onClick={props.onClick} />
      <Wrapper>
        <button className="git" onClick={() => props.onChangeCurRank(0)}>
          깃허브
        </button>
        <button
          className="boj"
          onClick={() => {
            if (isBoj) {
              props.onChangeCurRank(1);
            } else {
              props.setOpenBoj(true);
            }
          }}
        >
          백준
        </button>
      </Wrapper>
    </>
  );
};

export default RankMenuSelectModal;
