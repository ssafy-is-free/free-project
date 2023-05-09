import styled from 'styled-components';

const HeaderDiv = styled.div`
  margin: 1rem;
  margin-top: 2rem;

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
