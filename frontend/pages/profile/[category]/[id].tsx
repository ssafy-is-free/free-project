// import GithubInfo from '../../../components/profile/GithubInfo';
import BojInfo from '../../../components/profile/BojInfo';
import ProfileHeader from '../../../components/profile/ProfileHeader';
import styled from 'styled-components';
import { useRouter } from 'next/router';
import { useState, useEffect } from 'react';
import { Spinner } from '@/components/common/Spinner';
import dynamic from 'next/dynamic';
import { ParsedUrlQuery } from 'querystring';
import { getBoj, getGithub } from '@/pages/api/profileAxios';
import { IBojProfile, IGithubProfile } from '@/components/profile/IProfile';

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

  const [githubData, setGithubData] = useState<IGithubProfile>();
  const [bojData, setBojData] = useState<IBojProfile>();
  const [headerName, setHeaderName] = useState<string>('');
  const getGithubData = async () => {
    const res = await getGithub(id);
    if (res.data) {
      setGithubData(res.data);
      setHeaderName('깃허브');
    } else {
      alert(res.message);
    }
  };
  const getBojData = async () => {
    const res = await getBoj(id);
    if (res.data) {
      setBojData(res.data);
      setHeaderName('백준');
    } else {
      alert(res.message);
    }
  };

  const back = () => {
    router.back();
  };

  useEffect(() => {
    if (category === 'github') {
      getGithubData();
    } else if (category === 'boj') {
      getBojData();
    }
  }, [category, id]);

  return (
    <div>
      <ProfileHeader back={back} name={headerName}></ProfileHeader>
      <ProfileInfoDiv>
        {githubData && <GithubInfo githubData={githubData}></GithubInfo>}
        {bojData && <BojInfo bojData={bojData}></BojInfo>}
      </ProfileInfoDiv>
    </div>
  );
};

export default Profile;
