import { useRef, useState } from 'react';
import styled from 'styled-components';

const InputDiv = styled.div`
  padding: 1rem;

  .title {
    span {
      color: red;
    }
  }

  .input {
    background-color: ${(props) => props.theme.lightGray};
    color: ${(props) => props.theme.fontBlack};
    font-size: 1rem;
    padding: 0.5rem;
    border-radius: 0.5rem;
    width: 100%;
    margin-bottom: 1rem;

    &::placeholder {
      text-align: center;
      color: ${(props) => props.theme.fontGray};
    }
  }

  .textinput {
    background-color: ${(props) => props.theme.lightGray};
    padding: 0.2rem;
    textarea {
      background-color: transparent;
      width: 100%;
      border-radius: 0.5rem;
    }
  }
`;

interface IContentInput {
  label: string;
  content: (e: string) => void;
}

const ContentInput = ({ label, content }: IContentInput) => {
  const [text, setText] = useState<string>('');
  const inputRef = useRef<any>();
  const onChange = (event: any) => {
    setText(event.target.value);
    content(event.target.value);
  };

  return (
    <InputDiv>
      <div className="title">
        {label} <span>*</span>
      </div>
      <input
        type="text"
        className="input"
        placeholder="기업명을 입력해주세요"
        onChange={(event) => onChange(event)}
        ref={inputRef}
        value={text}
      />
    </InputDiv>
  );
};

const NewCareer = () => {
  const [text, setText] = useState<string | null>(null);
  return (
    <div>
      <div>newcareer</div>
      <ContentInput label={'기업명'} content={(e: string) => setText(e)}></ContentInput>
    </div>
  );
};

export default NewCareer;
