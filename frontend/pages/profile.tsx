import { useState } from 'react';
import Footer from '@/components/common/Footer';
import CustomNav from '@/components/common/CustomNav';
import BojInfo from '@/components/profile/BojInfo';
import GithubInfo from '@/components/profile/GithubInfo';
import styled from 'styled-components';

const ProfileContentDiv = styled.div`
  margin: 1rem;
  margin-bottom: max(4rem, 10vh);
  .nav {
    margin: 1rem;
    height: 5vh;
  }
`;

export default function ProfilePage() {
  const navList = ['깃허브', '백준'];
  const [selectedIdx, setSelectedIdx] = useState<number>(0);
  const selectIdx = (idx: number) => {
    setSelectedIdx(idx);
  };

  const githubId = 1;
  const bojId = 1;

  const profile = [<GithubInfo githubId={githubId}></GithubInfo>, <BojInfo bojId={bojId}></BojInfo>];

  return (
    <div>
      <ProfileContentDiv>
        <div className="nav">
          <CustomNav lists={navList} selectIdx={selectIdx}></CustomNav>
        </div>
        {profile[selectedIdx]}
      </ProfileContentDiv>
      <Footer></Footer>
    </div>
  );
}
