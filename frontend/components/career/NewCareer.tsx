import { useEffect, useRef, useState } from 'react';
import styled from 'styled-components';

const NewCareerDiv = styled.div`
  width: 100vw;
  height: 100vh;
  top: 0;
  position: fixed;
  background-color: white;
  padding-bottom: max(10vh, 4rem);
  z-index: 2;
  overflow: auto;
`;

export const InputDiv = styled.div`
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

interface Idata {
  [key: string]: number | string;
}
const ddata = {
  postingId: 1,
  postingName: '뽑아요 뽑습니다',
  companyName: '삼성전자',
  startTime: '2023-04-14 13:00',
  endTime: '2023-04-21 18:00',
  objective: '백엔드 개발자',
  status: '서류 합격',
  dDayName: '서류 제출',
  nextDate: '2023-05-01',
  memo: '메모입니다~',
  applicantCount: 10,
};

const dddata = {
  postingId: '',
  postingName: '',
  companyName: '',
  startTime: '',
  endTime: '',
  objective: '',
  status: '',
  dDayName: '',
  nextDate: '',
  memo: '',
  applicantCount: '',
};

const inputList = [
  { key: 'postingName', tag: '공고명', placeholder: '', readonly: true },
  { key: 'companyName', tag: '기업명', placeholder: '', readonly: true },
  { key: 'startTime', tag: '접수 시작일', placeholder: '', readonly: true },
  { key: 'endTime', tag: '접수 마감일', placeholder: '', readonly: true },
  { key: 'objective', tag: '지원 직무', placeholder: '', readonly: false },
  { key: 'status', tag: '현재 진행 상태', placeholder: '', readonly: true },
  {
    key: 'dDayName',
    tag: '다음 일정 이름',
    placeholder: 'ex) 1차 면접, 코딩테스트',
    readonly: false,
  },
  { key: 'nextDate', tag: '다음 일정', placeholder: '', readonly: true },
];

const HeaderDiv = styled.div`
  margin: 1rem;
  margin-top: 2rem;
  height: 3rem;
  display: flex;
  justify-content: space-between;

  img {
    height: 70%;
  }
  div {
    display: flex;
    align-items: center;
    h3 {
      font-size: large;
      color: ${(props) => props.theme.primary};
    }
  }
`;

interface INewCareer {
  close: () => void;
}

const NewCareer = ({ close }: INewCareer) => {
  const [data, setData] = useState<Idata>(dddata);

  const update = (key: string, e: React.ChangeEvent<HTMLInputElement>) => {
    setData((data) => {
      return { ...data, [key]: e.target.value };
    });
  };

  const careerPost = () => {
    // api
  };

  return (
    <NewCareerDiv>
      <HeaderDiv>
        <div>
          <img src="/Icon/CloseIcon.svg" alt="#" onClick={close} />
        </div>
        <div onClick={careerPost}>
          <h3>등록하기</h3>
        </div>
      </HeaderDiv>
      {inputList.map((item) => (
        <InputDiv key={item.tag}>
          <div>{item.tag}</div>
          <input
            type="text"
            className="input"
            placeholder={item.placeholder}
            value={data[item.key]}
            readOnly={item.readonly}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) => update(item.key, e)}
          ></input>
        </InputDiv>
      ))}
      <InputDiv>
        <div>메모</div>
        <textarea className="input" rows={4} placeholder=""></textarea>
      </InputDiv>
    </NewCareerDiv>
  );
};

export default NewCareer;
