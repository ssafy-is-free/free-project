import styled from 'styled-components';
import { IBojInfo, IAvatarData } from './IProfile';
import CircleChart from './CircleChart';
import Avatar from './Avatar';

const BojInfoDiv = styled.div``;
const BasicInfoDiv = styled.div`
  display: grid;
  grid-template-columns: 47% 47%;
  grid-template-rows: auto auto;
  grid-column-gap: 6%;
  grid-row-gap: 6%;
  margin-bottom: 2rem;
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
      <CircleChart data={bojData.languages} label={true}></CircleChart>
    </BojInfoDiv>
  );
};

export default BojInfo;
