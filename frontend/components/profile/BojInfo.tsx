import styled from 'styled-components';
import { useState, useEffect } from 'react';
import { IBojInfo, IBojAvatar, IBojProfile } from './IProfile';
import CircleChart from './CircleChart';
import Avatar from './Avatar';
import BojModal from '../login/BojModal';
import { Spinner } from '../common/Spinner';
import { getBoj, getMyBoj } from '@/pages/api/profileAxios';
import Image from 'next/image';

const LoadingDiv = styled.div`
  min-height: 50vh;
  display: flex;
  align-items: center;
`;
const BojInfoDiv = styled.div`
  background-color: ${(props) => props.theme.lightGray};
`;
const BojNoneDiv = styled.div`
  margin: 1rem;
  background-color: ${(props) => props.theme.secondary};
  height: 50vh;
  border-radius: 1rem;
  display: flex;
  justify-content: center;
  align-items: center;
`;
const ChartDiv = styled.div`
  margin-top: 0.5rem;
  margin-bottom: 1rem;
  border-top-right-radius: 1rem;
  border-top-left-radius: 1rem;
  background-color: ${(props) => props.theme.bgWhite};
  padding: 1rem;
`;
const BasicInfoDiv = styled.div`
  margin-top: 0.5rem;
  padding: 1rem;
  padding-bottom: 2rem;
  border-radius: 1rem;
  background-color: ${(props) => props.theme.bgWhite};
  display: grid;
  grid-template-columns: 47% 47%;
  grid-template-rows: auto auto;
  grid-column-gap: 6%;
  grid-row-gap: 6%;
`;
const BoxDiv = styled.div`
  min-height: 10rem;
  border-radius: 1rem;
  background-color: ${(props) => props.theme.secondary};
  padding: 1.3rem;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  img {
    display: block;
    margin-left: auto;
  }
`;

const BojInfo = ({ userId, my, setOpenCompare }: IBojInfo) => {
  const [bojData, setBojData] = useState<IBojProfile | null>(null);
  const [openBoj, setOpenBoj] = useState<boolean>(false);

  const getBojData = async () => {
    const res = await getBoj(userId);
    if (res.data) {
      setBojData(res.data);
    } else {
      setBojData({
        bojId: '-1',
        tierUrl: '',
        languages: [],
        pass: 0,
        tryFail: 0,
        submit: 0,
        fail: 0,
      });
    }
  };

  const getMyBojData = async () => {
    const res = await getMyBoj();
    if (res.data) {
      setBojData(res.data);
    } else {
      setBojData({
        bojId: '-1',
        tierUrl: '',
        languages: [],
        pass: 0,
        tryFail: 0,
        submit: 0,
        fail: 0,
      });
    }
  };

  useEffect(() => {
    if (my) {
      getMyBojData();
    } else {
      getBojData();
    }
  }, []);

  if (!bojData) {
    return (
      <LoadingDiv>
        <Spinner></Spinner>
      </LoadingDiv>
    );
  } else if (bojData.bojId === '-1') {
    if (my) {
      return (
        <BojNoneDiv
          onClick={() => {
            setOpenBoj(true);
          }}
        >
          <p>백준 아이디를 등록해주세요</p>
          {openBoj && <BojModal onClick={() => setOpenBoj(false)} />}
        </BojNoneDiv>
      );
    }
    return (
      <BojNoneDiv>
        <p>백준 아이디를 등록하지 않은 유저입니다.</p>
      </BojNoneDiv>
    );
  } else {
    const BOJ_URL = 'https://www.acmicpc.net/';
    const infoList = [
      {
        name: '맞은 문제',
        value: bojData.pass,
        icon: '/Icon/CorrectIcon.svg',
        link: `${BOJ_URL}problemset?user=${bojData.bojId}&user_solved=1`,
      },
      {
        name: '틀렸습니다',
        value: bojData.fail,
        icon: '/Icon/WrongIcon.svg',
        link: `${BOJ_URL}status?user_id=${bojData.bojId}&result_id=6`,
      },
      {
        name: '제출',
        value: bojData.submit,
        icon: '/Icon/SubmitIcon.svg',
        link: `${BOJ_URL}status?user_id=${bojData.bojId}`,
      },
      {
        name: '시도했지만 맞지 못한 문제',
        value: bojData.tryFail,
        icon: '/Icon/CryIcon.svg',
        link: `${BOJ_URL}problemset?user=${bojData.bojId}&user_solved=0`,
      },
    ];
    const avatarData: IBojAvatar = {
      avatarUrl: bojData.tierUrl,
      name: bojData.bojId,
    };

    return (
      <BojInfoDiv>
        <Avatar
          isCircle={false}
          data={avatarData}
          my={my}
          curRank={1}
          userId={Number(userId)}
          setOpenCompare={setOpenCompare}
        ></Avatar>
        <BasicInfoDiv>
          {infoList.map((info, idx) => (
            <a href={info.link} target="_blank" key={idx}>
              <BoxDiv key={idx}>
                <h4>{info.name}</h4>
                <p>{info.value}</p>
                <Image src={info.icon} alt={info.name} width={50} height={50} />
                {/* <img src={info.icon} alt="IC" /> */}
              </BoxDiv>
            </a>
          ))}
        </BasicInfoDiv>
        <ChartDiv>
          <p>Language</p>
          <CircleChart data={bojData.languages} label={true}></CircleChart>
        </ChartDiv>
      </BojInfoDiv>
    );
  }
};

export default BojInfo;
