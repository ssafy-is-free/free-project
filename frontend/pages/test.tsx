import Readme from '@/components/profile/Readme';
import Link from 'next/link';
import styled from 'styled-components';
import Main from '.';
import Footer from '@/components/common/Footer';
import Profile from '@/components/profile/Profile';

export default function Home() {
  const back = () => {};

  return (
    <div>
      {/* <Link href="/">
        <Main />
      </Link> */}
      <Profile isGithub={false} userId={1} back={back}></Profile>
    </div>
  );
}
