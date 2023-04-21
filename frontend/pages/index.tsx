import CancelOk from '@/components/common/CancelOk';
import Footer from '@/components/common/Footer';
import CircleChart from '@/components/profile/CircleChart';

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
      <CircleChart></CircleChart>
      <Footer></Footer>
    </div>
  );
}
