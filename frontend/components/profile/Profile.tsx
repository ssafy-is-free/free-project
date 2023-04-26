import GithubInfo from './GithubInfo';
import BojInfo from './BojInfo';
import { IProfile } from './IProfile';
import ProfileHeader from './ProfileHeader';
import styled from 'styled-components';

const ProfileInfoDiv = styled.div`
  margin: 1rem;
`;

export default function Profile({ isGithub, userId, back }: IProfile) {
  return (
    <div>
      <ProfileHeader back={back}></ProfileHeader>
      <ProfileInfoDiv>
        {isGithub ? <GithubInfo githubId={userId}></GithubInfo> : <BojInfo bojId={userId}></BojInfo>}
      </ProfileInfoDiv>
    </div>
  );
}
