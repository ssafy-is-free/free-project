// import GithubInfo from '../../../components/profile/GithubInfo';
import BojInfo from '@/components/profile/BojInfo';
import ProfileHeader from '@/components/profile/ProfileHeader';
import styled from 'styled-components';
import { useRouter } from 'next/router';
import { useState, useEffect } from 'react';
import { Spinner } from '@/components/common/Spinner';
import dynamic from 'next/dynamic';
import { ParsedUrlQuery } from 'querystring';
import { getBoj, getGithub } from '@/pages/api/profileAxios';
import { IBojProfile, IGithubProfile } from '@/components/profile/IProfile';
import CustomNav from '../common/CustomNav';

const ProfileDiv = styled.div`
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
