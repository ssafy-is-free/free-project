import { login } from '@/redux/authSlice';
import { useRouter } from 'next/router';
import { useEffect } from 'react';
import { useDispatch } from 'react-redux';

const Redirect = () => {
  //https://velog.io/@blackeichi/Next.jsdocument-is-not-defined-%EC%97%90%EB%9F%AC-%EB%B0%9C%EC%83%9D-%EC%9B%90%EC%9D%B8-window-is-not-defined
  // next.js에사 document is not defined 에러 발생하는 이유

  const dispatch = useDispatch();
  const router = useRouter();

  useEffect(() => {
    const queryParams = new URLSearchParams(document.location.search);
    const accessToken = queryParams.get('token');

    if (accessToken) {
      localStorage.setItem('accessToken', accessToken);
      dispatch(login());
      router.push('/main');
    }
  }, []);

  return null;
};

export default Redirect;
