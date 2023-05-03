import styled from 'styled-components';
import { useState } from 'react';
import { IBojInfo, IAvatarData } from './IProfile';
import CircleChart from './CircleChart';
import Avatar from './Avatar';
import BojModal from '../login/BojModal';

const BojInfoDiv = styled.div``;
const BojNoneDiv = styled.div`
  margin-top: 1rem;
  background-color: ${(props) => props.theme.secondary};
  height: 50vh;
  border-radius: 1rem;
  display: flex;
  justify-content: center;
  align-items: center;
`;
const BasicInfoDiv = styled.div`
  display: grid;
  grid-template-columns: 47% 47%;
  grid-template-rows: auto auto;
  grid-column-gap: 6%;
  grid-row-gap: 6%;
  margin-bottom: 3rem;
`;

const BoxDiv = styled.div`
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

const BojInfo = ({ bojData, my }: IBojInfo) => {
  if (bojData === undefined) {
    const [openBoj, setOpenBoj] = useState<boolean>(false);
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

  const infoList = [
    {
      name: '맞은 문제',
      value: bojData.pass,
      icon: '/Icon/CorrectIcon.svg',
    },
    {
      name: '틀렸습니다',
      value: bojData.fail,
      icon: '/Icon/WrongIcon.svg',
    },
    {
      name: '제출',
      value: bojData.submit,
      icon: '/Icon/SubmitIcon.svg',
    },
    {
      name: '시도했지만 맞지 못한 문제',
      value: bojData.tryFail,
      icon: '/Icon/CryIcon.svg',
    },
  ];
  const avatarData: IAvatarData = {
    avatarUrl: bojData.tierUrl,
    name: bojData.bojId,
  };

  return (
    <BojInfoDiv>
      <Avatar isCircle={false} data={avatarData} my={my}></Avatar>
      <BasicInfoDiv>
        {infoList.map((info, idx) => (
          <BoxDiv key={idx}>
            <div>
              <h4>{info.name}</h4>
              <p>{info.value}</p>
            </div>
            <img src={info.icon} alt="IC" />
          </BoxDiv>
        ))}
      </BasicInfoDiv>
      <p>Language</p>
      <CircleChart data={bojData.languages} label={true}></CircleChart>
    </BojInfoDiv>
  );
};

export default BojInfo;
