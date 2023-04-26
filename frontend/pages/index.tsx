import Readme from '@/components/profile/Readme';
import Link from 'next/link';
import { Spinner } from '@/components/common/Spinner';
import styled from 'styled-components';
import Main from './main';
import Footer from '@/components/common/Footer';

export default function Home() {
  return (
    <div>
      <Link href="/">
        <Main />
      </Link>
      <Spinner size="5" borderWidth="0.5" duration={0.8} />
    </div>
  );
}
