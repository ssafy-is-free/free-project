import { useState, useEffect } from 'react';

interface ICheckBoxProps {
  handeler: (bChecked: boolean) => void;
}

const CheckBox = ({ handeler }: ICheckBoxProps) => {
  const [bChecked, setChecked] = useState(false);

  const checkHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setChecked(!bChecked);
    handeler(!bChecked);
  };

  return (
    <div>
      <input type="checkbox" checked={bChecked} onChange={checkHandler} />
    </div>
  );
};

export default CheckBox;
