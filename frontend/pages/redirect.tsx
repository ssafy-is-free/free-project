import { login } from '@/redux/authSlice';
import { useEffect } from 'react';
import { useDispatch } from 'react-redux';

const Redirect = () => {
  const queryParams = new URLSearchParams(document.location.search);
  const accessToken = queryParams.get('accessToken');
  const dispatch = useDispatch();

  useEffect(() => {
    if (accessToken) {
      localStorage.setItem('accessToken', accessToken);
      dispatch(login());
    }
  }, []);

  return null;
};

export default Redirect;
