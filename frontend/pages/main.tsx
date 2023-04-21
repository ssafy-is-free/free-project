import { useRouter } from 'next/router';
import { useEffect, useState } from 'react';
import styled from 'styled-components';
import Splash from './splash';
import RankMenu from '@/components/common/RankMenu';
import SearchBar from '@/components/rank/RankSearchBar';
import FilterIcon from '../public/Icon/FilterIcon.svg';
import MainUserItem from '@/components/rank/MainUserItem';
import MainOtherItem from '@/components/rank/MainOtherItem';
import NoAccount from '@/components/rank/NoAccount';
import RankMenuSelectModal from '@/components/common/RankMenuSelectModal';
import LoginModal from '@/components/login/LoginModal';

const Wrapper = styled.div`
  width: 100vw;
  height: 100vh;
  background-color: ${(props) => props.theme.primary};
  display: flex;
  flex-direction: column;
  align-items: center;

  .content-wrapper {
    position: relative;
    margin-top: 32px;
    padding: 72px 32px 32px;
    width: 100%;
    height: 672px;
    background-color: ${(props) => props.theme.bgWhite};
    border-radius: 50px 50px 0px 0px;
    display: flex;
    flex-direction: column;

    .filter-box {
      position: absolute;
      top: 40px;
      right: 40px;
      cursor: pointer;
    }

    .my-rank {
      p {
        font-size: 20px;
        font-weight: bold;
        margin-bottom: 16px;
      }
      margin-bottom: 32px;
    }

    .rank-list {
      p {
        font-size: 20px;
        font-weight: bold;
        margin-bottom: 16px;
      }

      li {
        margin-bottom: 8px;
      }
    }
  }
`;

const Main = () => {
  // splash screen 적용하기
  const [splash, setSplash] = useState<boolean>(false);

  useEffect(() => {
    const timer = setTimeout(() => {
      setSplash(true);
    }, 1500);
    return () => clearTimeout(timer);
  }, []);

  // 랭크 menu select 모달 열기
  const [openSelect, setOpenSelect] = useState<boolean>(false);

  // 깃허브인지 백준인지 상태값 0: 깃허브, 1: 백준
  const [curRank, setCurRank] = useState<number>(0);
  const onChangeCurRank = (el: number) => {
    setCurRank(el);
    setOpenSelect(false);
  };

  // 로그인 모달 열기
  const [openLogin, setOpenLogin] = useState<boolean>(false);

  if (!splash) {
    return <Splash />;
  } else {
    return (
      <>
        <Wrapper>
          <RankMenu onClick={() => setOpenSelect(true)} curRank={curRank} />
          <SearchBar curRank={curRank} />
          <div className="content-wrapper">
            <div className="filter-box">
              <FilterIcon />
            </div>
            <div className="my-rank">
              <p>나의 랭킹</p>
              {/* <MainUserItem curRank={curRank} /> */}
              <NoAccount curRank={curRank} onClick={() => setOpenLogin(true)} />
            </div>
            <ul className="rank-list">
              <p>전체 랭킹</p>
              <li>
                <MainOtherItem curRank={curRank} />
              </li>
            </ul>
          </div>
        </Wrapper>
        {openSelect && <RankMenuSelectModal onClick={() => setOpenSelect(false)} onChangeCurRank={onChangeCurRank} />}
        {openLogin && <LoginModal onClick={() => setOpenLogin(false)} />}
      </>
    );
  }
};

export default Main;
