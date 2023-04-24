import Footer from '@/components/common/Footer';
import GithubInfo from '@/components/profile/GithubInfo';
import styled from 'styled-components';
import CustomNav from '@/components/common/CustomNav';
import { IGithubProfile, IBojProfile } from '@/components/profile/IProfile';
import { useEffect, useState } from 'react';
import BojInfo from '@/components/profile/BojInfo';
import ProfileHeader from '@/components/profile/ProfileHeader';

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
      name: 'ssafy-name',
      link: 'https://~~~',
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

const ProfileDiv = styled.div`
  margin-left: 2rem;
  margin-right: 2rem;
  margin-top: 4rem;
  margin-bottom: max(10vh, 4rem);
`;

const AvatarDiv = styled.div<{ active: boolean }>`
  margin-bottom: 2rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  img {
    height: 6rem;
    border-radius: ${(props) => (props.active ? '50%' : '0')};
  }
`;

const InfoDiv = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

const navList = ['깃허브', '백준'];

interface IAvatarData {
  avatarUrl: string;
  name: string;
}

export default function Profile() {
  const [navIdx, setNavIdx] = useState(0);
  const [profileData, setProfileData] = useState<IAvatarData>({
    avatarUrl: 'Icon/ProfileIcon.svg',
    name: '로딩중',
  });

  useEffect(() => {
    if (navIdx === 0) {
      setProfileData({
        avatarUrl: gitdata.avatarUrl,
        name: gitdata.nickname,
      });
    } else {
      setProfileData({
        avatarUrl: bojdata.tierUrl,
        name: bojdata.bojId,
      });
    }
  }, [navIdx]);

  const avatarDiv = (
    <AvatarDiv active={navIdx === 0}>
      <img src={profileData.avatarUrl} alt="Icon/ProfileIcon.svg" />
      <h3>{profileData.name}</h3>
    </AvatarDiv>
  );

  const back = () => {};
  const compare = () => {};

  return (
    <div>
      <ProfileHeader back={back} compare={compare}></ProfileHeader>
      <ProfileDiv>
        {avatarDiv}
        <InfoDiv>
          <CustomNav
            lists={navList}
            selectIdx={(idx: number) => {
              setNavIdx(idx);
            }}
          ></CustomNav>
          {navIdx === 0 ? <GithubInfo github={gitdata}></GithubInfo> : <BojInfo boj={bojdata}></BojInfo>}
        </InfoDiv>
      </ProfileDiv>
      <Footer></Footer>
    </div>
  );
}
