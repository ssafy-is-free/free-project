import styled, { keyframes } from 'styled-components';
import GithubIcon from '../public/Icon/GithubIcon.svg';
import { useEffect } from 'react';
import { patchCrawaling } from './api/loginAxios';
import { useRouter } from 'next/router';
import { useDispatch } from 'react-redux';
import { setLoginIng, setNew } from '@/redux/authSlice';

const upDown = keyframes`
    from{
    transform: translateY(0px);
  }
  to{
    transform: translateY(-10px);
  }
`;

const Wrapper = styled.div`
  width: 100vw;
  height: 100vh;
  /* height: calc(var(--vh, 1vh) * 100); */
  background-color: ${(props) => props.theme.primary};
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: relative;
  z-index: 999;
  font-size: 1.2rem;
  color: ${(props) => props.theme.fontWhite};
  text-align: center;
  line-height: 1.8rem;
`;

const StyledGithubIcon = styled(GithubIcon)`
  margin-bottom: 24px;
  animation: 1.4s infinite ease-in-out alternate ${upDown};
`;

const Splash = () => {
  const route = useRouter();
  const dispatch = useDispatch();

  useEffect(() => {
    (async () => {
      const data = await patchCrawaling();

      if (data.status == 'SUCCESS') {
        dispatch(setNew());
        route.push('/');
      }
    })();
  }, []);

  return (
    <Wrapper>
      <StyledGithubIcon />
      깃허브 정보를 <br />
      가져오고 있습니다...
    </Wrapper>
  );
};

export default Splash;
