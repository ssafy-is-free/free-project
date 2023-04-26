import Readme from '@/components/profile/Readme';
import Link from 'next/link';
import styled from 'styled-components';
import Main from './main';
import Footer from '@/components/common/Footer';

export default function Home() {
  return (
    <div>
      <Link href="/">
        <Main />
      </Link>
      {/* <Footer /> */}
    </div>
  );
}
