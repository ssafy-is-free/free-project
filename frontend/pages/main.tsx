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
import BojModal from '@/components/login/BojModal';
import FilterModal from '@/components/rank/FilterModal';
import { useDispatch, useSelector } from 'react-redux';
import { SplashState, splashCheck } from '@/redux/splashSlice';
import Footer from '@/components/common/Footer';
import { RootState } from '@/redux';

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

  // splash 상태관리
  const splashState = useSelector<RootState>((selector) => selector.splashChecker.check);
  const dispatch = useDispatch();

  useEffect(() => {
    const timer = setTimeout(() => {
      setSplash(true);
      // splash 상태값 변경
      dispatch(splashCheck());
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
  // 백준 모달 열기
  const [opeBoj, setOpenBoj] = useState<boolean>(false);

  // filter 모달 열기
  const [openFilter, setOpenFilter] = useState<boolean>(false);

  // rank list
  const gitRankList = [
    {
      userId: 1,
      nickname: 'ssafy1',
      rank: 1,
      score: '600',
      avatarUrl: 'https://www.thechooeok.com/common/img/default_profile.png',
      rankUpDown: 3,
    },
    {
      userId: 2,
      nickname: 'ssafy2',
      rank: 2,
      score: '500',
      avatarUrl: 'https://www.thechooeok.com/common/img/default_profile.png',
      rankUpDown: 3,
    },
    {
      userId: 3,
      nickname: 'ssafy3',
      rank: 3,
      score: '400',
      avatarUrl: 'https://www.thechooeok.com/common/img/default_profile.png',
      rankUpDown: 3,
    },
    {
      userId: 4,
      nickname: 'ssafy4',
      rank: 4,
      score: '300',
      avatarUrl: 'https://www.thechooeok.com/common/img/default_profile.png',
      rankUpDown: 3,
    },
    {
      userId: 5,
      nickname: 'ssafy5',
      rank: 5,
      score: '200',
      avatarUrl: 'https://www.thechooeok.com/common/img/default_profile.png',
      rankUpDown: 3,
    },
    {
      userId: 6,
      nickname: 'ssafy6',
      rank: 6,
      score: '100',
      avatarUrl: 'https://www.thechooeok.com/common/img/default_profile.png',
      rankUpDown: 3,
    },
  ];

  const bojRankList = [
    {
      userId: 1,
      nickname: 'ssafy1',
      rank: 1,
      score: '600',
      avatarUrl: 'https://www.thechooeok.com/common/img/default_profile.png',
      tierUrl: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQgGc1LYJ69Hyiy7uUJCgq3PzRMBWgyT5DX_Q&usqp=CAU',
      rankUpDown: 3,
    },
    {
      userId: 2,
      nickname: 'ssafy2',
      rank: 2,
      score: '500',
      avatarUrl: 'https://www.thechooeok.com/common/img/default_profile.png',
      tierUrl: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQgGc1LYJ69Hyiy7uUJCgq3PzRMBWgyT5DX_Q&usqp=CAU',
      rankUpDown: 3,
    },
    {
      userId: 3,
      nickname: 'ssafy3',
      rank: 3,
      score: '400',
      avatarUrl: 'https://www.thechooeok.com/common/img/default_profile.png',
      tierUrl: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQgGc1LYJ69Hyiy7uUJCgq3PzRMBWgyT5DX_Q&usqp=CAU',
      rankUpDown: 3,
    },
    {
      userId: 4,
      nickname: 'ssafy4',
      rank: 4,
      score: '300',
      avatarUrl: 'https://www.thechooeok.com/common/img/default_profile.png',
      tierUrl: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQgGc1LYJ69Hyiy7uUJCgq3PzRMBWgyT5DX_Q&usqp=CAU',
      rankUpDown: 3,
    },
    {
      userId: 5,
      nickname: 'ssafy5',
      rank: 5,
      score: '200',
      avatarUrl: 'https://www.thechooeok.com/common/img/default_profile.png',
      tierUrl: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQgGc1LYJ69Hyiy7uUJCgq3PzRMBWgyT5DX_Q&usqp=CAU',
      rankUpDown: 3,
    },
    {
      userId: 6,
      nickname: 'ssafy6',
      rank: 6,
      score: '100',
      avatarUrl: 'https://www.thechooeok.com/common/img/default_profile.png',
      tierUrl: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQgGc1LYJ69Hyiy7uUJCgq3PzRMBWgyT5DX_Q&usqp=CAU',
      rankUpDown: 3,
    },
  ];

  if (!splash && !splashState) {
    return <Splash />;
  } else {
    return (
      <>
        <Wrapper>
          <RankMenu onClick={() => setOpenSelect(true)} curRank={curRank} />
          <SearchBar curRank={curRank} />
          <div className="content-wrapper">
            <div className="filter-box">
              <FilterIcon onClick={() => setOpenFilter(true)} />
            </div>
            <div className="my-rank">
              <p>나의 랭킹</p>
              {/* <MainUserItem curRank={curRank} /> */}
              <NoAccount curRank={curRank} onClick={() => setOpenLogin(true)} />
            </div>
            <ul className="rank-list">
              <p>전체 랭킹</p>
              {curRank == 0
                ? gitRankList.map((el) => (
                    <li>
                      <MainOtherItem curRank={curRank} item={el} />
                    </li>
                  ))
                : bojRankList.map((el) => (
                    <li>
                      <MainOtherItem curRank={curRank} item={el} />
                    </li>
                  ))}
            </ul>
          </div>
        </Wrapper>
        {openSelect && <RankMenuSelectModal onClick={() => setOpenSelect(false)} onChangeCurRank={onChangeCurRank} />}
        {openLogin && <LoginModal onClick={() => setOpenLogin(false)} setOpenBoj={setOpenBoj} />}
        {opeBoj && <BojModal onClick={() => setOpenBoj(false)} />}
        {openFilter && <FilterModal onClick={() => setOpenFilter(false)} />}
      </>
    );
  }
};

export default Main;
