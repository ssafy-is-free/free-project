import { useState } from 'react';
import styled from 'styled-components';
import { ICheckBoxProps } from './ICareer';

const CheckBoxDiv = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 2rem;
  input {
  }
`;

const CheckBox = ({ handeler }: ICheckBoxProps) => {
  const [bChecked, setChecked] = useState(false);

  const checkHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setChecked(!bChecked);
    handeler(!bChecked);
  };

  return (
    <CheckBoxDiv>
      <input type="checkbox" checked={bChecked} onChange={checkHandler} />
    </CheckBoxDiv>
  );
};

export default CheckBox;
