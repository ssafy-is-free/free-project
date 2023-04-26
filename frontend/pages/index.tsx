import { Spinner } from '@/components/common/Spinner';
import styled from 'styled-components';

export default function Home() {
  return (
    <div>
      <div>home</div>
      <Spinner size="5" borderWidth="0.5" duration={0.8} />
    </div>
  );
}
