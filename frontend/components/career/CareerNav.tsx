import styled from 'styled-components';
import { ICareerNav } from './ICareer';

const CareerNavDiv = styled.div`
  display: flex;
  height: 100%;
`;

const NavItemDiv = styled.div<{ active: boolean }>`
  flex: 1;
  border-bottom: ${(props) => (props.active ? `4px solid ${props.theme.primary}` : '0px')};
  display: flex;
  align-items: center;
  justify-content: center;

  p {
    margin-bottom: 0.5rem;
    font-size: 1.2rem;
    color: ${(props) => (props.active ? props.theme.primary : props.theme.fontGray)};
  }
`;

function CareerNav({ selectedIdx, lists, selectIdx }: ICareerNav) {
  const clicked = (idx: number) => {
    selectIdx(idx);
  };

  return (
    <CareerNavDiv>
      {lists.map((item, idx) => (
        <NavItemDiv
          key={idx}
          active={idx === selectedIdx}
          onClick={() => {
            clicked(idx);
          }}
        >
          <p>{item}</p>
        </NavItemDiv>
      ))}
    </CareerNavDiv>
  );
}

export default CareerNav;
