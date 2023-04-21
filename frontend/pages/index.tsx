import CancelOk from '@/components/common/CancelOk';
import Footer from '@/components/common/Footer';

export default function Home() {
  const cancel = () => {
    console.log('취소버튼클릭');
  };
  const ok = () => {
    console.log('확인버튼클릭');
  };

  return (
    <div>
      <CancelOk cancel={cancel} ok={ok}></CancelOk>
      <Footer></Footer>
    </div>
  );
}
