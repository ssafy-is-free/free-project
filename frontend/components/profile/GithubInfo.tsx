import styled from 'styled-components';
import { useState, useEffect } from 'react';
import { IAvatarData, IGithubInfo, IGithubProfile } from './IProfile';
import CircleChart from './CircleChart';
import Readme from './Readme';
import Avatar from './Avatar';
import { Spinner } from '../common/Spinner';
import { getGithub, getMyGithub } from '@/pages/api/profileAxios';

const LoadingDiv = styled.div`
  min-height: 50vh;
  display: flex;
  align-items: center;
`;
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

const GithubInfo = ({ userId, my }: IGithubInfo) => {
  const [githubData, setGithubData] = useState<IGithubProfile | null>(null);
  const getGithubData = async () => {
    const res = await getGithub(userId);
    if (res.data) {
      setGithubData(res.data);
    } else {
      alert(res.message);
    }
  };
  const getMyGithubData = async () => {
    const res = await getMyGithub();
    if (res.data) {
      setGithubData(res.data);
    } else {
      alert(res.message);
    }
  };

  useEffect(() => {
    if (my) {
      getMyGithubData();
    } else {
      getGithubData();
    }
  }, []);

  if (!githubData) {
    return (
      <LoadingDiv>
        <Spinner></Spinner>
      </LoadingDiv>
    );
  } else {
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
      profileLink: githubData.profileLink,
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
            {githubData.repositories.length === 0 ? (
              <div>레포지토리가 없어요</div>
            ) : (
              githubData.repositories.map((repo, idx) => (
                <Readme key={idx} repository={repo} githubId={githubData.githubId}></Readme>
              ))
            )}
          </ReadmeDiv>
        </RepoDiv>
      </GithubDiv>
    );
  }
};

export default GithubInfo;
