import styled from 'styled-components';
import { IAvatarData, IGithubInfo } from './IProfile';
import CircleChart from './CircleChart';
import Readme from './Readme';
import Avatar from './Avatar';

const GithubDiv = styled.div`
  display: flex;
  flex-direction: column;
  background-color: ${(props) => props.theme.lightGray};
`;

const CommitDiv = styled.div`
  border-radius: 1rem;
  background-color: ${(props) => props.theme.bgWhite};
  padding: 1rem;
  display: flex;
  div {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
`;

const BlackDiv = styled.div`
  height: 0.5rem;
`;
const LanguageDiv = styled.div`
  border-radius: 1rem;
  background-color: ${(props) => props.theme.bgWhite};
  padding: 1rem;
`;

const RepoDiv = styled.div`
  border-top-left-radius: 1rem;
  border-top-right-radius: 1rem;
  background-color: ${(props) => props.theme.bgWhite};
  padding: 1rem;
  padding-bottom: 1rem;
  p {
    margin-bottom: 1rem;
  }
`;

const ReadmeDiv = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
`;

const GithubInfo = ({ githubData, my }: IGithubInfo) => {
  const basicInfo = [
    {
      name: '커밋',
      data: githubData.commit,
    },
    {
      name: '스타',
      data: githubData.star,
    },
    {
      name: '팔로워',
      data: githubData.followers,
    },
  ];
  const avatarData: IAvatarData = {
    avatarUrl: githubData.avatarUrl,
    name: githubData.nickname,
  };

  return (
    <GithubDiv>
      <Avatar isCircle={true} data={avatarData} my={my}></Avatar>
      <BlackDiv></BlackDiv>
      <CommitDiv>
        {basicInfo.map((info, idx) => (
          <div key={idx}>
            <h3>{info.data}</h3>
            <p>{info.name}</p>
          </div>
        ))}
      </CommitDiv>
      <BlackDiv></BlackDiv>
      <LanguageDiv>
        <p>Language</p>
        <CircleChart data={githubData.languages} label={true}></CircleChart>
      </LanguageDiv>
      <BlackDiv></BlackDiv>
      <RepoDiv>
        <p>Repositiories</p>
        <ReadmeDiv>
          {githubData.repositories.map((repo, idx) => (
            <Readme key={idx} repository={repo} githubId={githubData.githubId}></Readme>
          ))}
        </ReadmeDiv>
      </RepoDiv>
    </GithubDiv>
  );
};

export default GithubInfo;
