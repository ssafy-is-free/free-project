import styled, { keyframes } from 'styled-components';
import RSearchIcon from '../../public/Icon/SearchingIcon.svg';
import SearchImg from '../../public/Icon/SearchImg.svg';
import { useEffect, useRef, useState } from 'react';
import { IRankSearchBarProps } from './IRank';
import { getSearchBojResult, getSearchBojUser, getSearchGitResult, getSearchGitUser } from '@/pages/api/rankAxios';
import { useRouter } from 'next/router';

const bounce = keyframes`
  70% { transform:translateY(0%); }
    80% { transform:translateY(-8%); }
    90% { transform:translateY(0%); }
    95% { transform:translateY(-5%); }
    97% { transform:translateY(0%); }
    99% { transform:translateY(-1%); }
    100% { transform:translateY(0); }
`;

const Wrapper = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: start;
  position: relative;
  z-index: 5;
  margin-top: 16px;

  .icon-box {
    position: absolute;
    left: 16px;
    width: 20px;
    height: 20px;
  }

  .search-box {
    width: 100%;
    display: flex;
    align-items: center;
  }

  .input-box {
    height: 40px;
    width: 85%;
    border-radius: 8px;
    padding-left: 44px;
    color: ${(props) => props.theme.fontBlack};
    background-color: ${(props) => props.theme.lightGray};
    outline: none;
    font-size: 14px;

    &::placeholder {
      color: ${(props) => props.theme.fontGray};
    }
  }

  .cancel {
    background-color: transparent;
    color: ${(props) => props.theme.fontDarkGray};
    font-size: 16px;
    /* font-weight: bold; */
    margin-left: 8px;
  }

  .search-results {
    margin-top: 24px;
    width: 100%;
    padding: 0 16px;
  }

  .related-wrapper {
    width: 100%;
    li {
      margin-bottom: 8px;
      color: ${(props) => props.theme.fontBlack};
      font-size: 16px;
      padding: 8px 0px 0px;
    }

    .user-li {
      cursor: pointer;
    }
  }

  .no-search {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;

    .img-box {
      width: 240px;
      height: 240px;
      animation: ${bounce} 1s ease;
    }

    .label {
      text-align: center;
      width: 100%;
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
    }

    // 검색 결과 api
    // if (searchKeyword) {
    //   if (props.curRank == 0) {
    //     // 깃허브 검색
    //     (async () => {
    //       const data = await getSearchGitUser(searchKeyword);
    //       if (data.data?.length > 0) {
    //         setSearchResults([...data.data]);
    //       } else {
    //         setSearchResults([data.message]);
    //       }
    //     })();
    //   } else {
    //     // 백준 검색
    //     (async () => {
    //       const data = await getSearchBojUser(searchKeyword);
    //       if (data.data?.length > 0) {
    //         setSearchResults([...data.data]);
    //       } else {
    //         setSearchResults([data.message]);
    //       }
    //     })();
    //   }

    //   if (searchBox.current) {
    //     // 스타일 속성 변경
    //     searchBox.current.style.boxShadow = '4px 4px 10px #00000040';
    //   }

    //   if (relatedWrapper.current) {
    //     relatedWrapper.current.style.borderTop = '1px solid #00000040';
    //   }
    // } else {
    //   if (searchBox.current) {
    //     searchBox.current.style.boxShadow = '';
    //   }

    //   if (relatedWrapper.current) {
    //     relatedWrapper.current.style.borderTop = '';
    //   }
    // }
  }, [searchKeyword]);

  const resetInput = () => {
    window.location.href = '/';
    // (document.querySelector('.input-box') as HTMLInputElement).value = '';
    // props.getRankList(props.size, 1);
    // props.setNoScroll(false);
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

    props.setOnSearchClick(false);
  };

  return (
    <Wrapper>
      <div className="search-box">
        <div className="icon-box">
          <RSearchIcon />
        </div>
        <input type="text" className="input-box" placeholder={text} onChange={(event) => onChange(event)} />
        <button className="cancel" onClick={resetInput}>
          취소
        </button>
      </div>

      <div className="search-results">
        {searchKeyword != undefined ? (
          searchResults?.map((el, idx) => {
            if (typeof el != 'string')
              return (
                <ul className="related-wrapper" ref={relatedWrapper}>
                  <li className="user-li" key={idx} onClick={() => onSearchNick(el.userId, el.nickname)}>
                    {el.nickname}
                  </li>
                </ul>
              );
            else
              return (
                <div className="no-search" key={idx}>
                  <div className="img-box">
                    <SearchImg />
                  </div>
                  <div className="label">
                    해당 사용자가 <br />
                    존재하지 않습니다.
                  </div>
                </div>
              );
          })
        ) : (
          <div className="no-search">
            {/* {el} */}
            <div className="img-box">
              <SearchImg />
            </div>
            <div className="label">
              조회할 사용자의 <br />
              아이디를 검색해보세요.
            </div>
          </div>
        )}
      </div>

      {/* <div className="search-box" ref={searchBox}>
        <div className="input-wrapper">
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
      </div> */}
    </Wrapper>
  );
};

export default RankSearchBar;
