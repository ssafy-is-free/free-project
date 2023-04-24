import styled from 'styled-components';

const HeaderDiv = styled.div`
  margin: 1rem;
  margin-top: 3rem;

  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const ButtonDiv = styled.div`
  background-color: ${(props) => props.theme.primary};
  padding-inline: 1.5rem;
  padding-top: 0.5rem;
  padding-bottom: 0.5rem;
  border-radius: 1rem;
  span {
    font-size: 1.5rem;
    color: ${(props) => props.theme.fontWhite};
  }
`;

interface IProfileHeader {
  back: () => void;
  compare: () => void;
}

export default function ProfileHeader({ back, compare }: IProfileHeader) {
  return (
    <HeaderDiv onClick={back}>
      <img src="Icon/VectorIcon.svg" alt="화살표" />
      <ButtonDiv onClick={compare}>
        <span>비교하기</span>
      </ButtonDiv>
    </HeaderDiv>
  );
}
