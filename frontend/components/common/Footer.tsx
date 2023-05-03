import { useRouter } from 'next/router';
import styled, { keyframes } from 'styled-components';
import Home from 'public/Icon/HomeIcon.svg';
import Career from 'public/Icon/CareerIcon.svg';
import Profile from 'public/Icon/ProfileIcon.svg';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '@/redux';
import { useRef, useState } from 'react';
import LoginModal from '../login/LoginModal';

const bounce = keyframes`
  from, to { transform: scale(1, 1); }
  25% { transform: scale(0.9, 1.1); }
  50% { transform: scale(1.1, 0.9); }
  75% { transform: scale(0.95, 1.05); }
`;

const FooterDiv = styled.div`
  width: 100%;
  /* height: 10vh; */
  /* height: 8vh; */
  min-height: 4rem;
  bottom: 0;
  background-color: ${(props) => props.theme.bgWhite};
  /* box-shadow: 0px -1px 3px 0px gray; */
  box-shadow: 0px -3px 10px #8389a52d;
  display: flex;
  align-items: center;
  position: fixed;
  z-index: 5;
  padding: 10px 0px 24px;
  border-radius: 24px 24px 0px 0px;
`;

const IconDiv = styled.div`
  flex: 1;
  /* margin-top: 0.5rem; */
  display: flex;
  flex-direction: column;
  align-items: center;
  span {
    margin-top: 0.2rem;
    /* margin-bottom: 0.3rem; */
    font-size: 50%;
  }

  &:hover {
    animation: 0.5s ease 0s ${bounce};
  }
`;

const HomeIcon = styled(Home)`
  width: 24px;
  path {
    fill: ${(props) => (props.active ? props.theme.primary : props.theme.footerGray)};
  }
`;

const CareerIcon = styled(Career)`
  width: 24px;
  path {
    fill: ${(props) => (props.active ? props.theme.primary : props.theme.footerGray)};
  }
`;

const ProfileIcon = styled(Profile)`
  width: 24px;
  path {
    fill: ${(props) => (props.active ? props.theme.primary : props.theme.footerGray)};
  }
`;

/**
 * 현재 경로(url)에 따라 버튼이 활성화가 되도록 만들었습니다.
 * /main, /career, /profile
 * @returns
 */
function Footer() {
  const router = useRouter();
  // login 상태값 가져오기
  const isLogin = useSelector<RootState>((selector) => selector.authChecker.isLogin);

  // 로그인 모달 열기
  const [openLogin, setOpenLogin] = useState<boolean>(false);

  const footerItems = [
    {
      icon: HomeIcon,
      name: '메인',
      path: '/',
    },
    {
      icon: CareerIcon,
      name: '취업관리',
      // path: '/career',
      path: '/temp',
    },
    {
      icon: ProfileIcon,
      name: '프로필',
      path: '/profile',
    },
  ];

  const goPage = (item: any) => {
    if (!isLogin && item.path == '/profile') {
      setOpenLogin(true);
    } else {
      if (item.path == '/') {
        window.location.href = `${item.path}`;
      } else {
        router.push(item.path);
      }
    }
  };

  return (
    <FooterDiv>
      {footerItems.map((item, idx) => (
        <IconDiv
          onClick={() => {
            // router.push(item.path);
            goPage(item);
          }}
          key={idx}
        >
          <item.icon active={item.path === router.pathname ? 1 : 0} className="icon"></item.icon>
          <span>{item.name}</span>
        </IconDiv>
      ))}
      {openLogin && <LoginModal onClick={() => setOpenLogin(false)} />}
    </FooterDiv>
  );
}

export default Footer;
