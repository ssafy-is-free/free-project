import { login } from '@/redux/authSlice';
import { useRouter } from 'next/router';
import { useEffect } from 'react';
import { useDispatch } from 'react-redux';

const Redirect = () => {
  const dispatch = useDispatch();
  const router = useRouter();

  useEffect(() => {
    const queryParams = new URLSearchParams(document.location.search);
    const accessToken = queryParams.get('token');

    if (accessToken) {
      localStorage.setItem('accessToken', accessToken);
      dispatch(login());
      router.push('/loginLoading');
    }

    // 첫 회원인지?
    // const isNew = queryParams.get('isNew');

    // if (isNew == 'false') {
    //   // 기존 회원이면
    //   // 바로 메인 페이지 이동(백준 모달 X)
    //   if (accessToken) {
    //     localStorage.setItem('accessToken', accessToken);
    //     dispatch(login());
    //     router.push('/');
    //   }
    // } else {
    //   // 처음 로그인한 회원이면 깃허브 크롤링하기
    //   // 로딩 페이지로 이동
    //   // 완료되면 백준 모달 띄우기

    //   if (accessToken) {
    //     localStorage.setItem('accessToken', accessToken);
    //     dispatch(login());
    //     router.push('/loginLoading');
    //   }
    // }
  }, []);

  return null;
};

export default Redirect;
