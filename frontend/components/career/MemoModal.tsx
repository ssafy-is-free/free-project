import { useState, useEffect } from 'react';
import styled from 'styled-components';
import CancelOk from '../common/CancelOk';

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
const DarkBg = styled.div`
  position: fixed;
  z-index: 5;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: ${(props) => props.theme.modalGray};
`;
const StatuModalDiv = styled.div`
  .memocontent {
    width: 100vw;
    bottom: 0;
    left: 0;
    position: fixed;
    z-index: 10;
    background-color: white;
  }
`;

interface IMemoModalProps {
  close: () => void;
  result: (memo: string) => void;
  defaultValue: string;
}

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
      <DarkBg onClick={close}></DarkBg>
      <div className="memocontent">
        <InputDiv>
          <div>메모</div>
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
