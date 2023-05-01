import styled, { keyframes } from 'styled-components';
import LogoPrimary from '../../public/Icon/LogoPrimary.svg';
import CloseIcon from '../../public/Icon/CloseIcon.svg';
import BigBtn from '../common/BigBtn';
import { ILoginProps } from './ILogin';
import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { setLoginStart } from '@/redux/authSlice';

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

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 40%;
  position: fixed;
  z-index: 15;
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

const LoginModal = (props: ILoginProps) => {
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(setLoginStart());
  }, []);

  const onBtnClick = () => {
    // 기존 로그인 모달 창 닫기
    props.onClick();

    // 깃허브 로그인
    window.location.href = 'https://k8b102.p.ssafy.io/api/oauth2/authorization/github';

    // props.setOpenBoj(true);
  };

  // modal drag
  const onHandleDrag = () => {};

  return (
    <>
      <DarkBg onClick={props.onClick} />
      <Wrapper onClick={onHandleDrag}>
        <StyledCloseIcon onClick={props.onClick} />
        <LogoPrimary />
        <div className="label">로그인 후 이용 가능합니다. </div>
        <BigBtn text={'깃허브 로그인'} onClick={onBtnClick} />
      </Wrapper>
    </>
  );
};

export default LoginModal;
