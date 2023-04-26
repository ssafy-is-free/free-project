import styled from 'styled-components';
import { IAvatarData, IGithubInfo, IGithubProfile } from './IProfile';
import CircleChart from './CircleChart';
import Readme from './Readme';
import Avatar from './Avatar';

/**dummydata
 *
 */
const gitdata: IGithubProfile = {
  githubId: 1,
  nickname: 'taehak',
  profileLink: 'https://github.com/happyd918',
  avatarUrl: 'https://avatars.githubusercontent.com/u/84832358?v=4',
  commit: 100,
  star: 20,
  followers: 5,
  repositories: [
    {
      id: 1,
      name: 'repo-example1',
      link: 'https://raw.githubusercontent.com/hotsix-turtles/TUPLI/dev/README.md',
    },
    {
      id: 2,
      name: 'repo-example1',
      link: 'https://raw.githubusercontent.com/hotsix-turtles/TUPLI/dev/README.md',
    },
    {
      id: 3,
      name: 'repo-example1',
      link: 'https://raw.githubusercontent.com/hotsix-turtles/TUPLI/dev/README.md',
    },
    {
      id: 4,
      name: 'repo-example1',
      link: 'https://raw.githubusercontent.com/hotsix-turtles/TUPLI/dev/README.md',
    },
  ],
  languages: [
    {
      name: 'java',
      percentage: 98,
    },
    {
      name: 'python',
      percentage: 2,
    },
  ],
};

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

export default function GithubInfo({ githubId }: IGithubInfo) {
  const github = gitdata;
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
  const avatarData: IAvatarData = {
    avatarUrl: github.avatarUrl,
    name: github.nickname,
  };

  return (
    <GithubDiv>
      <Avatar isCircle={true} data={avatarData}></Avatar>
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
