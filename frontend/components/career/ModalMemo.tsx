import { useState } from 'react';
import styled from 'styled-components';
import CancelOk from '../common/CancelOk';
import { IMemoModalProps } from './ICareer';
import { DarkBg } from './SCareer';

const InputDiv = styled.div`
  padding: 1rem;
  div {
    margin-bottom: 0.2rem;
  }
  .input {
    background-color: ${(props) => props.theme.lightGray};
    color: ${(props) => props.theme.fontBlack};
    font-size: 1rem;
    padding: 0.5rem;
    border-radius: 0.5rem;
    width: 100%;
    border: 2px solid transparent;
    font-family: inherit;

    &:focus {
      outline: none;
      border-color: ${(props) => props.theme.primary};
    }
    &::placeholder {
      white-space: pre-wrap;
      text-align: center;
      color: ${(props) => props.theme.fontGray};
    }
  }
`;
const StatuModalDiv = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  z-index: 11;

  .darkBg {
    position: fixed;
    top: 0;
    left: 0;
    height: 100%;
    width: 100%;
    background-color: ${(props) => props.theme.modalGray};
  }

  .memocontent {
    width: 100vw;
    bottom: 0;
    left: 0;
    position: fixed;
    z-index: 10;
    background-color: white;
    .modalTitle {
      margin: 1rem;
      border-bottom: 2px solid ${(props) => props.theme.primary};
      text-align: center;
      font-size: large;
    }
  }
`;

const MemoModal = ({ close, result, defaultValue }: IMemoModalProps) => {
  const [memoValue, setMemoValue] = useState<string>(defaultValue);
  const handleMemoChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    // 👇️ access textarea value
    setMemoValue(event.target.value);
  };
  const ok = () => {
    result(memoValue);
    close();
  };

  return (
    <StatuModalDiv>
      <div className="darkBg" onClick={close}></div>
      <div className="memocontent">
        <div className="modalTitle">메모 변경하기</div>
        <InputDiv>
          <textarea
            className="input"
            value={memoValue}
            onChange={handleMemoChange}
            rows={4}
            placeholder="memo"
          ></textarea>
        </InputDiv>
        <CancelOk ok={ok} cancel={close}></CancelOk>
      </div>
    </StatuModalDiv>
  );
};

export default MemoModal;
