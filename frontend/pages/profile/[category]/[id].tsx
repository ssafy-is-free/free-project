import GithubInfo from '../../../components/profile/GithubInfo';
import BojInfo from '../../../components/profile/BojInfo';
import ProfileHeader from '../../../components/profile/ProfileHeader';
import styled from 'styled-components';
import { useRouter } from 'next/router';
import Footer from '@/components/common/Footer';
import { Spinner } from '@/components/common/Spinner';

const ProfileInfoDiv = styled.div`
  margin: 1rem;
  margin-bottom: max(4rem, 10vh);
`;

export default function Profile() {
  const router = useRouter();
  const { category, id } = router.query;
  if (typeof category === 'string' && typeof id === 'string') {
    const back = () => {
      router.back();
    };
    return (
      <div>
        <ProfileHeader back={back}></ProfileHeader>
        <ProfileInfoDiv>
          {category === 'github' ? (
            <GithubInfo githubId={parseInt(id)}></GithubInfo>
          ) : (
            <BojInfo bojId={parseInt(id)}></BojInfo>
          )}
        </ProfileInfoDiv>
        <Footer></Footer>
      </div>
    );
  } else {
    return <Spinner></Spinner>;
  }
}
