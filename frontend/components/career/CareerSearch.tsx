import { getJobPost } from '@/pages/api/career';
import { useState } from 'react';
import styled from 'styled-components';

const CareerSearchDiv = styled.div`
  width: 100vw;
  height: 100vh;
  position: fixed;
  top: 0;
  z-index: 10;
  background-color: white;
  overflow: auto;
  .csheader {
    margin: 1rem;
    margin-top: 2rem;
  }
  .cslist {
    max-height: 70vh;
    overflow: auto;
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
    jobPostingId: 0,
    companyName: '',
    postingName: '',
    startTime: '',
    endTime: '',
  },
];

interface ICareerSearchProps {
  close: () => void;
  result: (item: any) => void;
}

const CareerSearch = ({ close, result }: ICareerSearchProps) => {
  const [word, setWord] = useState<string>();
  const [data, setData] = useState<ISearchResult[]>(ddata);

  const searching = async (value: string) => {
    // 취업 공고, 회사명 search api
    // 1996.11.22 미정인 경우 날짜가
    // searchapi(value)
    if (value) {
    }
    const res = await getJobPost(value);
    setData(res.data);
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
        <div className="cslist">
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
      </div>
    </CareerSearchDiv>
  );
};

export default CareerSearch;
