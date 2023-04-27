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
  const [message, setMessate] = useState<string>('');

  useEffect(() => {
    (async () => {
      console.log('api호출');
      const res = await getMyGithub();
      if (res.data) {
        setGithubData(res);
      } else {
        setMessate(res.message);
      }
    })();
    // (async () => {
    //   const data = await getMyBoj();
    //   setBojData(data);
    // })();
  }, []);

  return (
    <div>
      <ProfileContentDiv>
        <div className="nav">
          <CustomNav lists={navList} selectIdx={selectIdx}></CustomNav>
        </div>
        {selectedIdx === 0 && githubData ? <GithubInfo githubData={githubData}></GithubInfo> : <div>{message}</div>}
        {/* {selectedIdx === 1 && bojData && <BojInfo bojData={bojData}></BojInfo>} */}
      </ProfileContentDiv>
    </div>
  );
};

export default MyProfile;
