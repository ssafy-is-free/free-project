import { getJobPost } from '@/pages/api/careerAxios';
import { useState } from 'react';
import styled from 'styled-components';
import { ICareerSearchProps, ISearchResult } from './ICareer';

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
      border-bottom: 1px solid;
      margin-bottom: 0.5rem;
      .company {
        margin-bottom: 0.2rem;
      }
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

const CareerSearch = ({ close, result }: ICareerSearchProps) => {
  const [word, setWord] = useState<string>();
  const [data, setData] = useState<ISearchResult[] | null>(null);
  const [notFocus, setNotFocus] = useState<boolean>(true);

  const searching = async (value: string) => {
    if (value) {
      const res = await getJobPost(value);
      setData(res.data);
    }
  };
  return (
    <CareerSearchDiv>
      <div className="csheader" onClick={close}>
        <img src="/Icon/CloseIcon.svg" alt="#" />
      </div>
      {notFocus && (
        <div className="csintro">
          <h2>기업명, 공고명을</h2>
          <h2>검색해서 추가해보세요</h2>
        </div>
      )}
      <div className="result">
        <InputDiv>
          <img src="Icon/SearchingIcon.svg" alt="#" />
          <input
            name="search"
            type="text"
            className="csinput"
            value={word}
            placeholder={'search...'}
            autoComplete="off"
            onFocus={() => setNotFocus(false)}
            onChange={(e) => {
              setWord(e.target.value);
              searching(e.target.value);
            }}
          ></input>
        </InputDiv>
        <div className="cslist">
          {data &&
            data.map((item) => {
              return (
                <div
                  className="csitem"
                  key={item.jobPostingId}
                  onClick={() => {
                    result(item);
                    close();
                  }}
                >
                  <h2 className="company">{item.companyName}</h2>
                  <p>{item.postingName}</p>
                  <p>
                    접수기간 : {item.startTime === '1996-11-22' ? '미정' : item.startTime} ~{' '}
                    {item.endTime === '1996-11-22' ? '미정' : item.endTime}
                  </p>
                </div>
              );
            })}
        </div>
      </div>
    </CareerSearchDiv>
  );
};

export default CareerSearch;
