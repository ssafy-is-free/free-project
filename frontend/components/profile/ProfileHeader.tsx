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
`;

interface IProfileHeader {
  back: () => void;
}

export default function ProfileHeader({ back }: IProfileHeader) {
  return (
    <HeaderDiv>
      <img src="Icon/VectorIcon.svg" alt="화살표" onClick={back} />
    </HeaderDiv>
  );
}
