import styled from 'styled-components';
import SearchIcon from '../../public/Icon/searchIcon.svg';
import { useEffect, useRef, useState } from 'react';
import { IRankSearchBarProps } from './IRank';
import { getSearchBojResult, getSearchBojUser, getSearchGitResult, getSearchGitUser } from '@/pages/api/rankAxios';

const Wrapper = styled.div`
  width: 85%;
  /* height: 5vh; */
  display: flex;
  align-items: start;
  position: relative;
  z-index: 5;

  .search-box {
    /* box-shadow: 4px 4px 10px #00000040; */

    background-color: ${(props) => props.theme.bgWhite};
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    position: relative;
    justify-content: space-around;
    /* margin-right: 16px; */
    border-radius: 8px;
    padding: 0 16px;
  }

  .input-wrapper {
    display: flex;
    align-items: center;
    width: 100%;
    /* height: 40px; */
    height: 5vh;
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
    margin-top: 8px;
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

    /* border-top: 1px solid ${(props) => props.theme.footerGray}; */
    /* padding-top: 8px; */
    li {
      margin-bottom: 8px;
      color: ${(props) => props.theme.fontBlack};
      font-size: 14px;
      padding: 8px 0px 0px;
    }

    .user-li {
      cursor: pointer;
    }
  }
`;

const RankSearchBar = (props: IRankSearchBarProps) => {
  const [text, setText] = useState<string>('깃허브');

  useEffect(() => {
    if (props.curRank == 0) {
      setText('깃허브 아이디를 검색해보세요.');
    } else {
      setText('백준 아이디를 검색해보세요.');
    }
  }, [props.curRank]);

  // 검색어
  const [searchKeyword, setSearchKeyword] = useState<string | undefined>();

  // TODO : type 넣을 떄 좀더 깔끔하게 하는법 찾기..
  // type Information = {
  //   userId: number;
  //   nickname: string;
  // };
  const [searchResults, setSearchResults] = useState<
    | string[]
    | {
        userId: number;
        nickname: string;
      }[]
  >();

  // 검색창 검색할 떄마다 호출
  const onChange = (event: any) => {
    setSearchKeyword(event.target.value);
  };

  // style 속성 변경 위한 검색창 ref
  const searchBox = useRef<HTMLDivElement | null>(null);
  const relatedWrapper = useRef<any>();

  useEffect(() => {
    // 검색 결과 api
    if (searchKeyword) {
      if (props.curRank == 0) {
        // 깃허브 검색
        (async () => {
          const data = await getSearchGitUser(searchKeyword);
          if (data.data?.length > 0) {
            setSearchResults([...data.data]);
          } else {
            setSearchResults([data.message]);
          }
        })();
      } else {
        // 백준 검색
        (async () => {
          const data = await getSearchBojUser(searchKeyword);
          if (data.data?.length > 0) {
            setSearchResults([...data.data]);
          } else {
            setSearchResults([data.message]);
          }
        })();
      }

      if (searchBox.current) {
        // 스타일 속성 변경
        searchBox.current.style.boxShadow = '4px 4px 10px #00000040';
      }

      if (relatedWrapper.current) {
        relatedWrapper.current.style.borderTop = '1px solid #00000040';
      }
    } else {
      if (searchBox.current) {
        searchBox.current.style.boxShadow = '';
      }

      if (relatedWrapper.current) {
        relatedWrapper.current.style.borderTop = '';
      }
    }
  }, [searchKeyword]);

  const resetInput = () => {
    (document.querySelector('.input-box') as HTMLInputElement).value = '';
    props.getRankList(props.size, 1);
    props.setNoScroll(false);
  };

  // 닉네임 검색 결과
  const onSearchNick = async (userId: number, nickName: string) => {
    props.setNoScroll(true);
    (document.querySelector('.input-box') as HTMLInputElement).value = `${nickName}`;
    setSearchResults([]);

    if (searchBox.current) {
      searchBox.current.style.boxShadow = '';
    }

    if (props.curRank == 0) {
      const data = await getSearchGitResult(userId);
      props.setGitRankList((prev) => [data.data.githubRankingCover]);
    } else {
      const data = await getSearchBojResult(userId);
      props.setBojRankList((prev) => [data.data]);
    }
  };

  return (
    <Wrapper>
      <div className="search-box" ref={searchBox}>
        <div className="input-wrapper">
          <SearchIcon />
          <input type="text" className="input-box" placeholder={text} onChange={(event) => onChange(event)} />
        </div>
        <ul className="related-wrapper" ref={relatedWrapper}>
          {searchKeyword != '' &&
            searchResults?.map((el, idx) => {
              if (typeof el != 'string')
                return (
                  <li className="user-li" key={idx} onClick={() => onSearchNick(el.userId, el.nickname)}>
                    {el.nickname}
                  </li>
                );
              else
                return (
                  <li className="nouser-li" key={idx}>
                    {el}
                  </li>
                );
            })}
        </ul>
      </div>
      {/* <button className="cancel" onClick={resetInput}>
        취소
      </button> */}
    </Wrapper>
  );
};

export default RankSearchBar;
