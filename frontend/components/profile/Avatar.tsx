import styled from 'styled-components';
import { IAvatar, IAvatarData } from './IProfile';

const AvatarDiv = styled.div<{ active: boolean }>`
  background-color: ${(props) => props.theme.bgWhite};
  padding: 2rem;
  display: flex;
  align-items: center;
  justify-content: space-around;

  img {
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
  }
  p {
    font-size: x-small;
  }
`;

const mydata: IAvatarData = {
  avatarUrl: '/Icon/ProfileIcon',
  name: 'ssafy',
};

const Avatar = ({ isCircle, data, my }: IAvatar) => {
  const toCompare = () => {
    alert('업데이트 예정입니다.');
  };

  const compare = () => {
    if (!my) {
      return (
        <CompareDiv onClick={toCompare}>
          <img src="/Icon/Compare.svg" alt="" />
          <p>나와 비교하기</p>
        </CompareDiv>
      );
    } else {
      return null;
    }
  };
  return (
    <AvatarDiv active={isCircle}>
      <div className="avatar">
        <img src={data ? data.avatarUrl : mydata.avatarUrl} alt="Icon/ProfileIcon.svg" />
        <h3>{data ? data.name : mydata.name}</h3>
      </div>
      {compare()}
    </AvatarDiv>
  );
};
export default Avatar;
