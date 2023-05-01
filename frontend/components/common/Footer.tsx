import { useRouter } from 'next/router';
import styled from 'styled-components';
import Home from 'public/Icon/HomeIcon.svg';
import Career from 'public/Icon/CareerIcon.svg';
import Profile from 'public/Icon/ProfileIcon.svg';
import { useSelector } from 'react-redux';
import { RootState } from '@/redux';

const FooterDiv = styled.div`
  width: 100%;
  height: 10vh;
  min-height: 4rem;
  bottom: 0;
  background-color: ${(props) => props.theme.bgWhite};
  /* box-shadow: 0px -1px 3px 0px gray; */
  box-shadow: 0px -2px 10px #4a58a94b;
  display: flex;
  position: fixed;
  z-index: 5;
`;

const IconDiv = styled.div`
  flex: 1;
  margin-top: 0.5rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  span {
    margin-top: 0.2rem;
    margin-bottom: 0.3rem;
    font-size: 80%;
  }
`;

const HomeIcon = styled(Home)`
  path {
    fill: ${(props) => (props.active ? props.theme.primary : props.theme.footerGray)};
  }
`;

const CareerIcon = styled(Career)`
  path {
    fill: ${(props) => (props.active ? props.theme.primary : props.theme.footerGray)};
  }
`;

const ProfileIcon = styled(Profile)`
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
    console.log('item', item);
    if (isLogin) {
      router.push(item.path);
    } else {
      if (item.path == '/profile') {
        alert('로그인 후 이용 가능합니다.');
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
    </FooterDiv>
  );
}

export default Footer;
