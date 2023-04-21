import styled from 'styled-components';
import SearchIcon from '../../public/Icon/searchIcon.svg';
import { useEffect, useState } from 'react';

interface IProps {
  curRank: number;
}

const Wrapper = styled.div`
  width: 85%;
  /* height: 40px; */
  display: flex;
  align-items: start;

  .search-box {
    background-color: ${(props) => props.theme.bgWhite};
    width: 80%;
    display: flex;
    flex-direction: column;
    align-items: center;
    position: relative;
    justify-content: space-around;
    margin-right: 16px;
    border-radius: 8px;
    padding: 0 16px;
  }

  .input-wrapper {
    display: flex;
    align-items: center;
    width: 100%;
    height: 40px;
  }

  .input-box {
    height: 100%;
    width: 90%;
    border-radius: 8px;
    padding: 0px 8px;
    color: ${(props) => props.theme.fontBlack};
    outline: none;
    font-size: 14px;

    &::placeholder {
      color: ${(props) => props.theme.fontGray};
    }
  }

  .cancel {
    margin-top: 4px;
    background-color: transparent;
    color: ${(props) => props.theme.fontWhite};
    font-size: 20px;
    font-weight: bold;
    cursor: pointer;
  }

  .related-wrapper {
    background-color: ${(props) => props.theme.bgWhite};
    width: 100%;
    padding-left: 10%;
    border-top: 1px solid ${(props) => props.theme.footerGray};
    padding-top: 8px;

    li {
      margin-bottom: 8px;
      color: ${(props) => props.theme.fontBlack};
      font-size: 14px;
      /* padding: 8px; */
    }
  }
`;

const RankSearchBar = (props: IProps) => {
  const [text, setText] = useState<string>('깃허브');

  useEffect(() => {
    if (props.curRank == 0) {
      setText('깃허브 아이디를 검색해보세요.');
    } else {
      setText('백준 아이디를 검색해보세요.');
    }
  }, [props.curRank]);

  return (
    <Wrapper>
      <div className="search-box">
        <div className="input-wrapper">
          <SearchIcon />
          <input type="text" className="input-box" placeholder={text} />
        </div>
        {/* <ul className="related-wrapper">
          <li>키워드</li>
          <li>키워드</li>
        </ul> */}
      </div>
      <button className="cancel">취소</button>
    </Wrapper>
  );
};

export default RankSearchBar;
