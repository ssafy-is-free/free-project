import styled from 'styled-components';
import { IGithubInfo } from './IProfile';
import CircleChart from './CircleChart';
import Readme from './Readme';

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

export default function GithubInfo({ github }: IGithubInfo) {
  const basicInfo = [
    {
      name: 'Commits',
      data: github.commit,
    },
    {
      name: 'Star',
      data: github.star,
    },
    {
      name: 'Followers',
      data: github.followers,
    },
  ];

  return (
    <GithubDiv>
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
        <CircleChart data={github.languages} label={true}></CircleChart>
      </LanguageDiv>
      <RepoDiv>
        <p>Repositiories</p>
        <ReadmeDiv>
          {github.repositories.map((repo, idx) => (
            <Readme key={idx} repository={repo}></Readme>
          ))}
        </ReadmeDiv>
      </RepoDiv>
    </GithubDiv>
  );
}