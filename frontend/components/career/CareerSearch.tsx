import { useState } from 'react';
import styled from 'styled-components';

const CareerSearchDiv = styled.div`
  width: 100vw;
  height: 100vh;
  position: fixed;
  top: 0;
  z-index: 10;
  background-color: white;
  padding-bottom: max(10vh, 4rem);
  overflow: auto;
  .csheader {
    margin: 1rem;
    margin-top: 2rem;
  }
  .csintro {
    margin: 1rem;
    margin-bottom: 2rem;
    margin-top: 2rem;
  }
  .result {
    margin: 1rem;
    .csitem {
      border: 1px solid;
    }
  }
`;
const InputDiv = styled.div`
  background-color: ${(props) => props.theme.lightGray};
  border: 2px solid transparent;
  border-radius: 0.5rem;
  display: flex;
  align-items: center;

  img {
    margin-left: 0.5rem;
    height: 1.5rem;
    width: 1.5rem;
  }

  .csinput {
    color: ${(props) => props.theme.fontBlack};
    background-color: transparent;
    font-size: 1rem;
    padding: 0.5rem;
    font-family: inherit;

    &:focus {
      outline: none;
      border-color: ${(props) => props.theme.primary};
    }
    &::placeholder {
      white-space: pre-wrap;
      color: ${(props) => props.theme.fontGray};
    }
  }
`;

export interface ISearchResult {
  jobPostingId: number;
  companyName: string;
  postingName: string;
  startTime: string;
  endTime: string;
}
// dummy data
const ddata: ISearchResult[] = [
  {
    jobPostingId: 1,
    companyName: '삼성전자',
    postingName: '2023 상반기 어쩌고',
    startTime: '2023-04-03',
    endTime: '2023-04-06',
  },
  {
    jobPostingId: 2,
    companyName: '삼성전자',
    postingName: '2023 상반기 어쩌고',
    startTime: '2023-04-03',
    endTime: '2023-04-06',
  },
  {
    jobPostingId: 3,
    companyName: '삼성전자',
    postingName: '2023 상반기 어쩌고',
    startTime: '2023-04-03',
    endTime: '2023-04-06',
  },
  {
    jobPostingId: 4,
    companyName: '삼성전자',
    postingName: '2023 상반기 어쩌고',
    startTime: '2023-04-03',
    endTime: '2023-04-06',
  },
  {
    jobPostingId: 5,
    companyName: '삼성전자',
    postingName: '2023 상반기 어쩌고',
    startTime: '2023-04-03',
    endTime: '2023-04-06',
  },
];

interface ICareerSearchProps {
  close: () => void;
  result: (item: any) => void;
}

const CareerSearch = ({ close, result }: ICareerSearchProps) => {
  const [word, setWord] = useState<string>();
  const [data, setData] = useState<ISearchResult[]>(ddata);
  const searching = (value: string) => {
    // 취업 공고, 회사명 search api
    // 1996.11.22 미정인 경우 날짜가
    // searchapi(value)
    // setData(res)
  };
  return (
    <CareerSearchDiv>
      <div className="csheader" onClick={close}>
        <img src="/Icon/CloseIcon.svg" alt="#" />
      </div>
      <div className="csintro">
        <h2>기업명, 공고명을</h2>
        <h2>검색해서 추가해보세요</h2>
      </div>
      <div className="result">
        <InputDiv>
          <img src="Icon/SearchingIcon.svg" alt="#" />
          <input
            name="search"
            type="text"
            className="csinput"
            value={word}
            placeholder={'search...'}
            onChange={(e) => {
              setWord(e.target.value);
              searching(e.target.value);
            }}
          ></input>
        </InputDiv>
        {data.map((item) => (
          <div
            className="csitem"
            key={item.jobPostingId}
            onClick={() => {
              result(item);
              close();
            }}
          >
            <p>회사명 : {item.companyName}</p>
            <p>공고명 : {item.postingName}</p>
            <p>
              기간 : {item.startTime} ~ {item.endTime}
            </p>
          </div>
        ))}
      </div>
    </CareerSearchDiv>
  );
};

export default CareerSearch;
