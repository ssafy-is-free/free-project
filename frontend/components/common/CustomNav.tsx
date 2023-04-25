import { useState } from 'react';
import styled from 'styled-components';
import { ICustomNav } from './ICommon';

const CustomNavDiv = styled.div`
  display: flex;
`;

const NavItemDiv = styled.div<{ active: boolean }>`
  flex: 1;
  border-bottom: ${(props) => (props.active ? `2px solid ${props.theme.primary}` : '0px')};
  display: flex;
  align-items: center;
  justify-content: center;

  p {
    color: ${(props) => (props.active ? props.theme.primary : props.theme.fontGray)};
  }
`;

function CustomNav({ lists, selectIdx }: ICustomNav) {
  const [selectedIdx, setSelectedIdx] = useState<number>(0);
  const clicked = (idx: number) => {
    setSelectedIdx(idx);
    selectIdx(idx);
  };

  return (
    <CustomNavDiv>
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
    </CustomNavDiv>
  );
}

export default CustomNav;
