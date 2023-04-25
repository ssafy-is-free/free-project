import styled from 'styled-components';
import { IGithubInfo } from './IProfile';
import CircleChart from './CircleChart';

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

const RepoItemDiv = styled.div`
  border: 1px solid;
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
        <div>
          {github.repositories.map((repo, idx) => (
            <RepoItemDiv key={idx}>{repo.name}</RepoItemDiv>
          ))}
        </div>
      </RepoDiv>
    </GithubDiv>
  );
}
