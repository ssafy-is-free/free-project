import styled from 'styled-components';
import { ICompareUserBoxProps } from '../jobrank/IJobrank';
import CompareUserBox from '../jobrank/CompareUserBox';
import BackIcon from '../../public/Icon/BackIcon.svg';
import { useEffect } from 'react';

const Wrapper = styled.div`
  width: 100vw;
  height: 100vh;
  background-color: ${(props) => props.theme.bgWhite};
  padding: 4rem 2rem;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 3;

  .space {
    height: 100px;
    padding: 0px 14px;
    width: 100%;
  }

  .back-icon {
    position: absolute;
    top: 32px;
    left: 32px;
  }
`;

const Compare = (props: ICompareUserBoxProps) => {
  return (
    <Wrapper>
      <div className="back-icon" onClick={props.onClick}>
        <BackIcon />
      </div>
      <CompareUserBox curRank={props.curRank} userId={props.userId} />
      <div className="space"></div>
    </Wrapper>
  );
};

export default Compare;
