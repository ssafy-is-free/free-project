import { login } from '@/redux/authSlice';
import { useRouter } from 'next/router';
import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { patchCrawaling } from './api/loginAxios';

const Redirect = () => {
  //https://velog.io/@blackeichi/Next.jsdocument-is-not-defined-%EC%97%90%EB%9F%AC-%EB%B0%9C%EC%83%9D-%EC%9B%90%EC%9D%B8-window-is-not-defined
  // next.js에사 document is not defined 에러 발생하는 이유

  const dispatch = useDispatch();
  const router = useRouter();

  useEffect(() => {
    const queryParams = new URLSearchParams(document.location.search);
    const accessToken = queryParams.get('token');

    // 첫 회원인지?
    const isNew = queryParams.get('isNew');

    if (isNew == 'false') {
      // 처음 로그인한 회원이면 깃허브 크롤링하기
      // 로딩 페이지로 이동
      // 완료되면 백준 모달 띄우기
      if (accessToken) {
        localStorage.setItem('accessToken', accessToken);
        dispatch(login());
        // patchCrawaling();
        router.push('/loginLoading');
      }
    } else {
      // 기존 회원이면
      // 바로 메인 페이지 이동(백준 모달 X)
      if (accessToken) {
        localStorage.setItem('accessToken', accessToken);
        dispatch(login());
        router.push('/');
      }
    }
  }, []);

  return null;
};

export default Redirect;
