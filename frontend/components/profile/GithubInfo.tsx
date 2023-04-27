import styled from 'styled-components';
import { IAvatarData, IGithubInfo } from './IProfile';
import CircleChart from './CircleChart';
import Readme from './Readme';
import Avatar from './Avatar';

const GithubDiv = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;

const CommitDiv = styled.div`
  display: flex;

  div {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
`;

const LanguageDiv = styled.div``;

const RepoDiv = styled.div`
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
      name: 'Commits',
      data: githubData.commit,
    },
    {
      name: 'Star',
      data: githubData.star,
    },
    {
      name: 'Followers',
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
      <CommitDiv>
        {basicInfo.map((info, idx) => (
          <div key={idx}>
            <h3>{info.data}</h3>
            <p>{info.name}</p>
          </div>
        ))}
      </CommitDiv>
      <LanguageDiv>
        <p>Language</p>
        <CircleChart data={githubData.languages} label={true}></CircleChart>
      </LanguageDiv>
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
