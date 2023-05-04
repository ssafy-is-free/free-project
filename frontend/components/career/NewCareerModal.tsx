import { useEffect, useState } from 'react';
import styled, { keyframes } from 'styled-components';
import { Spinner } from '../common/Spinner';

const moveUp = keyframes`
 from{
    transform: translateY(180px);
    opacity: 0;
  }
  to{
    transform: translateY(0px);
    opacity: 1;
  }
`;

const DarkBg = styled.div`
  position: fixed;
  z-index: 10;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: ${(props) => props.theme.modalGray};
`;

const NewCareerModalDiv = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  /* height: 50%; */
  padding: 72px 0px;
  position: fixed;
  z-index: 15;
  bottom: 0;
  background-color: ${(props) => props.theme.bgWhite};
  animation: 0.4s ease-in-out 0s ${moveUp};
`;

const status = () => {
  return (
    <div>
      <div>status</div>
    </div>
  );
};

const dday = () => {
  return (
    <div>
      <div>dday</div>
    </div>
  );
};

const NewCareerModal = () => {
  const [open, setOpen] = useState(true);

  return (
    <div>
      <DarkBg onClick={() => setOpen(false)}></DarkBg>
      <NewCareerModalDiv>{dday()}</NewCareerModalDiv>
      {/* <NewCareerModalDiv>{status()}</NewCareerModalDiv> */}
    </div>
  );
};
export default NewCareerModal;
