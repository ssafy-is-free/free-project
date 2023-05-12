import { useState, useEffect } from 'react';
import styled from 'styled-components';
import { ICustomNav } from './ICommon';

const CustomNavDiv = styled.div`
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

function CustomNav({ lists, selectIdx, defaultIdx }: ICustomNav) {
  const [navIdx, setNavIdx] = useState<number>(defaultIdx);
  const clicked = (idx: number) => {
    selectIdx(idx);
  };

  return (
    <CustomNavDiv>
      {lists.map((item, idx) => (
        <NavItemDiv
          key={idx}
          active={idx === navIdx}
          onClick={() => {
            clicked(idx);
            setNavIdx(idx);
          }}
        >
          <p>{item}</p>
        </NavItemDiv>
      ))}
    </CustomNavDiv>
  );
}

export default CustomNav;
