import { login, setBoj, setNew } from '@/redux/authSlice';
import { useRouter } from 'next/router';
import { useEffect } from 'react';
import { useDispatch } from 'react-redux';

const Redirect = () => {
  const dispatch = useDispatch();
  const router = useRouter();

  useEffect(() => {
    const queryParams = new URLSearchParams(document.location.search);
    const accessToken = queryParams.get('token');
    const isBoj = queryParams.get('isBoj');

    if (isBoj == 'false') {
      // 백준 계정 X
      dispatch(setNew());
    } else {
      dispatch(setBoj());
    }

    if (accessToken) {
      localStorage.setItem('accessToken', accessToken);
      dispatch(login());
      router.push('/');
    }
  }, []);

  return null;
};

export default Redirect;
