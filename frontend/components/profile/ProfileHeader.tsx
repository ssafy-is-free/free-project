import styled from 'styled-components';
import Image from 'next/image';
import vectorIcon from '@/public/Icon/VectorIcon.png';

const HeaderDiv = styled.div`
  padding-left: 1rem;
  padding-top: 1rem;
  width: 100vw;
  position: fixed;
  background-color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;

  img {
    height: 1.5rem;
  }
  div {
    flex: 1;
  }
  .title {
    display: flex;
    justify-content: center;
    align-items: center;
  }
`;

interface IProfileHeader {
  back: () => void;
}

function ProfileHeader({ back }: IProfileHeader) {
  return (
    <HeaderDiv>
      <div>
        <Image src={vectorIcon} alt="화살표" width={16} height={24} onClick={back}></Image>
      </div>
      <div className="title"></div>
      <div>
        <span></span>
      </div>
    </HeaderDiv>
  );
}
export default ProfileHeader;
