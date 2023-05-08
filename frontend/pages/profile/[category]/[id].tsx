// import GithubInfo from '../../../components/profile/GithubInfo';
import BojInfo from '../../../components/profile/BojInfo';
import ProfileHeader from '../../../components/profile/ProfileHeader';
import styled from 'styled-components';
import { useRouter } from 'next/router';
import { useState, useEffect } from 'react';
import { Spinner } from '@/components/common/Spinner';
import dynamic from 'next/dynamic';
import { ParsedUrlQuery } from 'querystring';
import CustomNav from '@/components/common/CustomNav';

const GithubInfo = dynamic(() => import('@/components/profile/GithubInfo'), {
  ssr: false,
  loading: () => <Spinner></Spinner>,
});

interface IProfileQuery extends ParsedUrlQuery {
  category: string;
  id: string;
}

const ProfileInfoDiv = styled.div`
  margin-bottom: max(4rem, 10vh);
`;

const Profile = () => {
  const router = useRouter();
  const { category, id } = router.query as IProfileQuery;
  const [navIdx, setNavIdx] = useState<number>(3);

  const back = () => {
    router.back();
  };

  useEffect(() => {
    if (category === 'github') {
      setNavIdx(0);
    } else if (category === 'boj') {
      setNavIdx(1);
    }
  }, [category, id]);

  return (
    <div>
      <ProfileHeader back={back}></ProfileHeader>
      <CustomNav
        lists={['깃허브', '백준']}
        defaultIdx={navIdx}
        selectIdx={(idx) => {
          setNavIdx(idx);
        }}
      ></CustomNav>
      <ProfileInfoDiv>
        {navIdx === 0 && <GithubInfo userId={id}></GithubInfo>}
        {navIdx === 1 && <BojInfo userId={id}></BojInfo>}
      </ProfileInfoDiv>
    </div>
  );
};

export default Profile;
