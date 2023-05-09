import { useState } from 'react';
import CustomNav from '@/components/common/CustomNav';
import BojInfo from '@/components/profile/BojInfo';
import GithubInfo from '@/components/profile/GithubInfo';
import styled from 'styled-components';

const ProfileContentDiv = styled.div`
  margin-bottom: max(4rem, 10vh);
  .nav {
    height: 5vh;
    margin: 1rem;
  }
`;

const MyProfile = () => {
  const navList = ['깃허브', '백준'];
  const [selectedIdx, setSelectedIdx] = useState<number>(0);
  const selectIdx = (idx: number) => {
    setSelectedIdx(idx);
  };

  return (
    <div>
      <ProfileContentDiv>
        <div className="nav">
          <CustomNav lists={navList} selectIdx={selectIdx} defaultIdx={0}></CustomNav>
        </div>
        {selectedIdx === 0 && <GithubInfo userId="0" my={true}></GithubInfo>}
        {selectedIdx === 1 && <BojInfo userId="0" my={true}></BojInfo>}
      </ProfileContentDiv>
    </div>
  );
};

export default MyProfile;
