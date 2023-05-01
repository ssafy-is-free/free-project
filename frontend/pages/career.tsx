import styled from 'styled-components';

const CareerDiv = styled.div`
  height: 80vh;
  margin: 1rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  img {
    width: 70%;
    margin-bottom: 1rem;
  }
`;

export default function Career() {
  return (
    <CareerDiv>
      <img src="/Icon/WorkInProgressIcon.png" alt="" />
      <p>
        자신의 취업지원 이력을
        <br />
        기록하고 관리할 수 있는 공간을 만들 예정이에요
      </p>
    </CareerDiv>
  );
}
