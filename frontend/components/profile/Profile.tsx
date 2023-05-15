import BojInfo from '@/components/profile/BojInfo';
import ProfileHeader from '@/components/profile/ProfileHeader';
import styled from 'styled-components';
import { useState } from 'react';
import { Spinner } from '@/components/common/Spinner';
import dynamic from 'next/dynamic';
import CustomNav from '../common/CustomNav';

const ProfileDiv = styled.div`
  background-color: white;
  position: absolute;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  z-index: 3;
  .headerblank {
    height: 2.5rem;
  }
  .profileNav {
    margin: 1rem;
  }
`;

const GithubInfo = dynamic(() => import('@/components/profile/GithubInfo'), {
  ssr: false,
  loading: () => <Spinner></Spinner>,
});

const ProfileInfoDiv = styled.div`
  margin-bottom: max(4rem, 10vh);
`;

interface IProfileProps {
  curRank: number;
  id: string;
  back: () => void;
  my?: boolean;
}

const TestProfile = ({ curRank, id, back, my }: IProfileProps) => {
  const [navIdx, setNavIdx] = useState<number>(curRank);

  return (
    <ProfileDiv>
      <ProfileHeader back={back}></ProfileHeader>
      <div className="headerblank"></div>
      <div className="profileNav">
        <CustomNav
          lists={['깃허브', '백준']}
          defaultIdx={curRank}
          selectIdx={(idx) => {
            setNavIdx(idx);
          }}
        ></CustomNav>
      </div>
      <ProfileInfoDiv>
        {navIdx === 0 && <GithubInfo userId={id} my={my}></GithubInfo>}
        {navIdx === 1 && <BojInfo userId={id} my={my}></BojInfo>}
      </ProfileInfoDiv>
    </ProfileDiv>
  );
};

export default TestProfile;
