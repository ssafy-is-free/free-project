import { useState, useEffect } from 'react';
import CustomNav from '@/components/common/CustomNav';
import BojInfo from '@/components/profile/BojInfo';
import GithubInfo from '@/components/profile/GithubInfo';
import styled from 'styled-components';
import { IGithubProfile, IBojProfile } from '@/components/profile/IProfile';
import { getMyBoj, getMyGithub } from '../api/profileAxios';
import { Spinner } from '@/components/common/Spinner';

const ProfileContentDiv = styled.div`
  margin: 1rem;
  margin-bottom: max(4rem, 10vh);
  .nav {
    height: 5vh;
  }
`;

const MyProfile = () => {
  const navList = ['깃허브', '백준'];
  const [selectedIdx, setSelectedIdx] = useState<number>(0);
  const selectIdx = (idx: number) => {
    setSelectedIdx(idx);
  };

  const [githubData, setGithubData] = useState<IGithubProfile>();
  const [bojData, setBojData] = useState<IBojProfile>();
  // const [message, setMessate] = useState<string>('');

  useEffect(() => {
    (async () => {
      const res = await getMyGithub();
      if (res.data) {
        setGithubData(res.data);
      } else {
        console.log(res.message);
      }
    })();
    (async () => {
      const res = await getMyBoj();
      if (res.data) {
        setBojData(res.data);
      } else {
        console.log(res.message);
      }
    })();
  }, []);

  return (
    <div>
      <ProfileContentDiv>
        <div className="nav">
          <CustomNav lists={navList} selectIdx={selectIdx}></CustomNav>
        </div>
        {selectedIdx === 0 && githubData && <GithubInfo githubData={githubData} my={true}></GithubInfo>}
        {selectedIdx === 1 && <BojInfo bojData={bojData} my={true}></BojInfo>}
      </ProfileContentDiv>
    </div>
  );
};

export default MyProfile;
