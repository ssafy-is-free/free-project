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
  name: string;
}

export default function ProfileHeader({ back, name }: IProfileHeader) {
  return (
    <HeaderDiv>
      <div>
        <img src="/Icon/VectorIcon.svg" alt="화살표" onClick={back} />
      </div>
      <div className="title">
        <h3>{name}</h3>
      </div>
      <div>
        <span></span>
      </div>
    </HeaderDiv>
  );
}
