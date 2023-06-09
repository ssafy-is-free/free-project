import styled, { keyframes } from 'styled-components';
import LogoPrimary from '../../public/Icon/LogoPrimary.svg';
import CloseIcon from '../../public/Icon/CloseIcon.svg';
// import BigBtn from '../common/BigBtn';
import { ISettingModalProps } from './IRank';
import dynamic from 'next/dynamic';
const BigBtn = dynamic(() => import('../common/BigBtn'), { ssr: false });

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

  .close-box {
    position: absolute;
    top: 32px;
    left: 32px;
    cursor: pointer;
    width: 20px;
    height: 20px;
  }

  .atag {
    width: 100%;
    display: flex;
    justify-content: center;
  }
`;

const SettingModal = (props: ISettingModalProps) => {
  return (
    <>
      <DarkBg onClick={props.onClick} />
      <Wrapper>
        <div className="close-box">
          <CloseIcon onClick={props.onClick} />
        </div>
        <LogoPrimary />
        <div className="label">CHPO 서비스 후기를 남겨주세요. </div>
        <a href="https://forms.gle/dMTLyX5qSLes4v2K8" target="_blank" className="atag">
          <BigBtn text={'피드백 하기'} onClick={() => {}} />
        </a>
      </Wrapper>
    </>
  );
};

export default SettingModal;
