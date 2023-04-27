import styled from 'styled-components';
import { IBojInfo, IAvatarData, IBojProfile } from './IProfile';
import CircleChart from './CircleChart';
import Avatar from './Avatar';

/** dummydata
 *
 */
const bojdata: IBojProfile = {
  bojId: 'fixup719',
  tierUrl: 'https://d2gd6pc034wcta.cloudfront.net/tier/17.svg',
  languages: [
    {
      name: 'java',
      percentage: 98,
    },
  ],
  pass: 719,
  tryFail: 5,
  submit: 1000,
  fail: 500,
};

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

export default function BojInfo({ bojId }: IBojInfo) {
  const boj = bojdata;
  const infoList = [
    {
      name: '맞은 문제',
      value: boj.pass,
      icon: '/Icon/CorrectIcon.svg',
    },
    {
      name: '틀렸습니다',
      value: boj.fail,
      icon: '/Icon/WrongIcon.svg',
    },
    {
      name: '제출',
      value: boj.submit,
      icon: '/Icon/SubmitIcon.svg',
    },
    {
      name: '시도했지만 맞지 못한 문제',
      value: boj.tryFail,
      icon: '/Icon/CryIcon.svg',
    },
  ];
  const avatarData: IAvatarData = {
    avatarUrl: boj.tierUrl,
    name: boj.bojId,
  };
  return (
    <BojInfoDiv>
      <Avatar isCircle={false} data={avatarData}></Avatar>
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
      <CircleChart data={boj.languages} label={true}></CircleChart>
    </BojInfoDiv>
  );
}
