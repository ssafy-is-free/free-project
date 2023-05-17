import styled from 'styled-components';
import { IAvatar, IAvatarData } from './IProfile';
import { useSelector } from 'react-redux';
import { RootState } from '@/redux';
import Image from 'next/image';
import compareIcon from '@/public/Icon/CompareIcon.jpg';
import profileIcon from '@/public/Icon/ProfileIcon.png';

const AvatarDiv = styled.div<{ active: boolean }>`
  background-color: ${(props) => props.theme.bgWhite};
  padding: 2rem;
  display: flex;
  align-items: center;
  justify-content: space-around;

  .profileimg {
    height: 6rem;
    border-radius: ${(props) => (props.active ? '50%' : '0')};
    margin-bottom: 1rem;
  }
  .avatar {
    margin-left: 50%;
    margin-right: 50%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  .nickname {
    white-space: nowrap;
  }
`;

const CompareDiv = styled.div`
  position: absolute;
  margin-left: 50%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  img {
    height: 3rem;
    width: 4rem;
    margin-bottom: 0.5rem;
  }
  p {
    font-size: x-small;
  }
`;

const mydata = {
  avatarUrl: profileIcon,
  name: 'ssafy',
  profileLink: 'github.com',
};

const Avatar = ({ isCircle, data, my, curRank, userId, setOpenCompare }: IAvatar) => {
  // login 상태값 가져오기
  const isLogin = useSelector<RootState>((selector) => selector.authChecker.isLogin);

  const toCompare = () => {
    if (setOpenCompare) {
      setOpenCompare(true);
    }
  };

  const compare = () => {
    if (!my && isLogin) {
      return (
        <CompareDiv onClick={toCompare}>
          <Image src={compareIcon} alt="compare" placeholder="blur" width={80} height={96} />
          <p>나와 비교하기</p>
        </CompareDiv>
      );
    } else {
      return null;
    }
  };
  return (
    <AvatarDiv active={isCircle}>
      <div>
        <a className="avatar" href={data.profileLink} target="_blank">
          <Image
            className="profileimg"
            src={data ? data.avatarUrl : mydata.avatarUrl}
            alt="프로필"
            width={96}
            height={96}
            priority={true}
          />
          <h3 className="nickname">{data ? data.name : mydata.name}</h3>
        </a>
      </div>
      {compare()}
    </AvatarDiv>
  );
};
export default Avatar;
