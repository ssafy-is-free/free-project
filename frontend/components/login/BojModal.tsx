import styled, { keyframes } from 'styled-components';
import LogoPrimary from '../../public/Icon/LogoPrimary.svg';
import BigBtn from '../common/BigBtn';
import { IBojProps } from './ILogin';
import { useRouter } from 'next/router';
import { useRef, useState } from 'react';
import { checkBojId, postBojId } from '@/pages/api/loginAxios';

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
    width: 80%;
    font-size: 20px;
    font-weight: bold;
    color: ${(props) => props.theme.fontBlack};
    margin: 32px 0px 0px;
    text-align: left;
  }

  .label2 {
    width: 80%;
    font-size: 14px;
    color: ${(props) => props.theme.fontGray};
    margin: 8px 0px 32px;
    text-align: left;
  }

  .label3-true {
    width: 80%;
    font-size: 16px;
    color: #41bb31;
    margin: 0px 0px 8px;
    text-align: left;
  }

  .label3-false {
    width: 80%;
    font-size: 16px;
    color: #e93f3f;
    margin: 0px 0px 8px;
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

  // 등록할 백준 id
  const [bojId, setBojId] = useState<string>('');

  // 중복 체크
  const [check, setCheck] = useState<number>(2);

  // input창 ref
  const inputRef = useRef<any>();

  const onChange = async (event: any) => {
    const id = event.target.value;
    const data = await checkBojId(id);
    const style = inputRef.current.style;

    if (id == '') {
      style.border = '';
      setCheck(3);
      setBojId('');
      return;
    }

    if (data.status == 'SUCCESS') {
      // 중복된 아이디가 아니면
      style.border = '2px solid #41bb31';
      setCheck(0);
      setBojId(id);
    } else if (data.status == 'FAIL') {
      // 중복된 아이디면
      style.border = '2px solid #e93f3f';
      setCheck(1);
      setBojId('');
    }
  };

  const onClickBojId = () => {
    if (bojId != '') {
      // 등록 가능
      postBojId(bojId);
      props.onClick;
      router.push('/');
    } else {
      // 등록 불가능
      // 경고 메시지 띄우기
      const style = inputRef.current.style;
      style.border = '2px solid #e93f3f';
      setCheck(2);
    }
  };

  return (
    <>
      <DarkBg onClick={props.onClick} />
      <Wrapper>
        {/* <StyledCloseIcon onClick={props.onClick} /> */}
        <LogoPrimary />
        <div className="label1">백준 아이디를 등록하시겠습니까? </div>
        <div className="label2">백준 아이디를 등록해서 랭킹을 확인해보세요. </div>
        <input
          type="text"
          className="input-id"
          placeholder="아이디"
          onChange={(event) => onChange(event)}
          ref={inputRef}
        />
        {check == 0 ? (
          <div className="label3-true">등록 가능한 아이디 입니다.</div>
        ) : check == 1 ? (
          <div className="label3-false">중복된 아이디 입니다.</div>
        ) : check == 2 ? (
          <div className="label3-false">아이디를 입력해주세요.</div>
        ) : null}
        <BigBtn text={'등록하기'} onClick={onClickBojId} />
        <div className="btn-wrapper">
          <button
            className="passBtn"
            onClick={() => {
              props.onClick;
              router.push('/');
            }}
          >
            건너뛰기
          </button>
        </div>
      </Wrapper>
    </>
  );
};

export default BojModal;
