import styled from 'styled-components';
import { ICancelOk } from './ICommon';

const CancelOkDiv = styled.div`
  display: flex;
  width: 100%;
  height: 10vh;
`;
const CancelDiv = styled.div`
  flex: 1;
  background-color: ${(props) => props.theme.btnGray};
  display: flex;
  align-items: center;
  justify-content: center;
  span {
    margin-top: 0.2rem;
    margin-bottom: 0.3rem;
    font-size: 200%;
    color: ${(props) => props.theme.fontWhite};
  }
`;
const OkDiv = styled.div`
  flex: 1;
  background-color: ${(props) => props.theme.primary};
  display: flex;
  align-items: center;
  justify-content: center;
  span {
    margin-top: 0.2rem;
    margin-bottom: 0.3rem;
    font-size: 200%;
    color: ${(props) => props.theme.fontWhite};
  }
`;

export default function CancelOk(props: ICancelOk) {
  return (
    <CancelOkDiv>
      <CancelDiv onClick={props.cancel}>
        <span>{props.cancelWord ? props.cancelWord : '취소'}</span>
      </CancelDiv>
      <OkDiv onClick={props.ok}>
        <span>{props.okWord ? props.okWord : '확인'}</span>
      </OkDiv>
    </CancelOkDiv>
  );
}
