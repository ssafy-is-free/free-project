import { setCookie } from '@/utils/cookies';

const test = () => {
  const gg = () => {
    const dd = new Date('2023-05-13');
    setCookie('webview-check', dd.toString());
  };

  return (
    <div>
      <div onClick={gg}>dd</div>
    </div>
  );
};

export default test;
