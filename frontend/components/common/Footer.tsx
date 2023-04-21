import { useRouter } from 'next/router';
import styled from 'styled-components';
import Home from 'public/Icon/HomeIcon.svg';
import Career from 'public/Icon/CareerIcon.svg';
import Profile from 'public/Icon/ProfileIcon.svg';

const FooterDiv = styled.div`
  width: 100%;
  height: 10vh;
  bottom: 0;
  box-shadow: 0px -1px 3px 0px gray;
  display: flex;
  position: fixed;
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
  const footerItems = [
    {
      icon: HomeIcon,
      name: '메인',
      path: '/main',
    },
    {
      icon: CareerIcon,
      name: '취업관리',
      path: '/career',
    },
    {
      icon: ProfileIcon,
      name: '프로필',
      path: '/profile',
    },
  ];

  return (
    <FooterDiv>
      {footerItems.map((item, idx) => (
        <IconDiv
          onClick={() => {
            router.push(item.path);
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
