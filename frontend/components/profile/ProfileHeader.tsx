import styled from 'styled-components';
import Image from 'next/image';

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
        {/* <Image src="/Icon/VectorIcon.svg"></Image> */}
        <img src="/Icon/VectorIcon.svg" alt="화살표" onClick={back} />
      </div>
      <div className="title"></div>
      <div>
        <span></span>
      </div>
    </HeaderDiv>
  );
}
export default ProfileHeader;
