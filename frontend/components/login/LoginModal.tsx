import styled, { keyframes } from 'styled-components';
import LogoPrimary from '../../public/Icon/LogoPrimary.svg';
import CloseIcon from '../../public/Icon/CloseIcon.svg';
import BigBtn from '../common/BigBtn';

interface IProps {
  onClick: () => void;
}

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
  top: 0;
  width: 100%;
  height: 100%;
  background-color: ${(props) => props.theme.modalGray};
`;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 40%;
  position: fixed;
  bottom: 0;
  background-color: ${(props) => props.theme.bgWhite};
  animation: 0.4s ease-in-out 0s ${moveUp};

  .label {
    font-size: 16px;
    color: ${(props) => props.theme.fontGray};
    margin: 16px 0px;
  }
`;

const StyledCloseIcon = styled(CloseIcon)`
  position: absolute;
  top: 32px;
  left: 32px;
  cursor: pointer;
`;

const LoginModal = (props: IProps) => {
  return (
    <>
      <DarkBg onClick={props.onClick} />
      <Wrapper>
        <StyledCloseIcon onClick={props.onClick} />
        <LogoPrimary />
        <div className="label">로그인 후 이용 가능합니다. </div>
        <BigBtn text={'깃허브 로그인'} />
      </Wrapper>
    </>
  );
};

export default LoginModal;
