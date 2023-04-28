import { useCookies } from 'react-cookie';

const Test = () => {
  const url = process.env.NEXT_PUBLIC_AUTHURL;
  const [cookies, setCookie] = useCookies(['redirect-uri']);
  const dd = () => {
    setCookie('redirect-uri', 'k8b102.p.ssafy.io/redirect');
  };
  return <div onClick={dd}>{url}</div>;
};

export default Test;
