import styled, { keyframes } from 'styled-components';
import LogoPrimary from '../../public/Icon/LogoPrimary.svg';
import BigBtn from '../common/BigBtn';
import { IBojProps } from './ILogin';
import { useRouter } from 'next/router';
import { useState } from 'react';
import { postBojId } from '@/pages/api/rankAxios';

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
  /* height: 50%; */
  padding: 72px 0px;
  position: fixed;
  z-index: 15;
  bottom: 0;
  background-color: ${(props) => props.theme.bgWhite};
  animation: 0.4s ease-in-out 0s ${moveUp};

  .label1 {
    font-size: 20px;
    font-weight: bold;
    color: ${(props) => props.theme.fontBlack};
    margin: 32px 0px 0px;
    text-align: left;
  }

  .label2 {
    font-size: 14px;
    color: ${(props) => props.theme.fontGray};
    margin: 8px 0px 32px;
    text-align: left;
  }

  .input-id {
    background-color: ${(props) => props.theme.lightGray};
    color: ${(props) => props.theme.fontBlack};
    font-size: 20px;
    padding: 16px 16px;
    border-radius: 8px;
    width: 80%;
    outline: none;
    margin-bottom: 8px;

    &::placeholder {
      color: ${(props) => props.theme.fontGray};
    }
  }
  .btn-wrapper {
    width: 80%;
    display: flex;
    align-items: center;
    justify-content: right;
    margin-top: 8px;

    .passBtn {
      background-color: transparent;
      font-size: 16px;
      color: ${(props) => props.theme.fontBlack};
      /* font-weight: bold; */
      cursor: pointer;
    }
  }
`;

const BojModal = (props: IBojProps) => {
  const router = useRouter();

  const [bojId, setBojId] = useState<string>();

  const onChange = (event: any) => {
    setBojId(event.target.value);
  };

  const onClickBojId = () => {
    if (bojId) postBojId(bojId);
  };

  return (
    <>
      <DarkBg onClick={props.onClick} />
      <Wrapper>
        {/* <StyledCloseIcon onClick={props.onClick} /> */}
        <LogoPrimary />
        <div className="label1">백준 아이디를 등록하시겠습니까? </div>
        <div className="label2">백준 아이디를 등록해서 랭킹을 확인해보세요. </div>
        <input type="text" className="input-id" placeholder="아이디" onChange={(event) => onChange(event)} />
        <BigBtn text={'등록하기'} onClick={onClickBojId} />
        <div className="btn-wrapper">
          <button className="passBtn" onClick={() => router.push('/')}>
            건너뛰기
          </button>
        </div>
      </Wrapper>
    </>
  );
};

export default BojModal;
