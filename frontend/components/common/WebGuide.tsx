import { setCookie } from '@/utils/cookies';

const WebGuide = () => {
  const saveDate = () => {
    const today = new Date();
    setCookie('webview-check', today.toString());
  };

  return (
    <div>
      <div>대충 안내 페이지</div>
      <div onClick={saveDate}>오늘 하루 보지 않기</div>
    </div>
  );
};

export default WebGuide;
