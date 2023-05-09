import styled from 'styled-components';

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

export default function ProfileHeader({ back }: IProfileHeader) {
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
