import { patchRepoOpen } from '@/pages/api/profileAxios';
import { useState } from 'react';
import styled from 'styled-components';
import CancelOk from '../common/CancelOk';

const Modal = styled.div`
  position: fixed;
  height: 100vh;
  top: 0;
  left: 0;
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
  justify-content: end;
  /* height: 50%; */
  left: 10%;
  width: 80vw;
  top: 37%;
  height: 25vh;
  position: fixed;
  z-index: 15;
  background-color: ${(props) => props.theme.bgWhite};
  .qa {
    margin: auto;
  }
  .cancelok {
    width: 100%;
  }
`;
const ToggleContainer = styled.div`
  position: relative;
  cursor: pointer;

  > .toggle-container {
    width: 50px;
    height: 24px;
    border-radius: 30px;
    background-color: rgb(233, 233, 234);
  }
  //.toggle--checked 클래스가 활성화 되었을 경우의 CSS를 구현
  > .toggle--checked {
    background-color: rgb(0, 200, 102);
    transition: 0.5s;
  }

  > .toggle-circle {
    position: absolute;
    top: 1px;
    left: 1px;
    width: 22px;
    height: 22px;
    border-radius: 50%;
    background-color: rgb(255, 254, 255);
    transition: 0.5s;
    //.toggle--checked 클래스가 활성화 되었을 경우의 CSS를 구현
  }
  > .toggle--checked {
    left: 27px;
    transition: 0.5s;
  }
`;

interface IToggle {
  isOn: boolean;
  setIsOn: (status: boolean) => void;
  githubId: number;
  reload: () => void;
}

export const Toggle = ({ isOn, setIsOn, githubId, reload }: IToggle) => {
  const [modalOpen, setModalOpen] = useState<boolean>(false);

  const goApi = async () => {
    const res = await patchRepoOpen(!isOn, githubId);
    if (res.status === 'SUCCESS') {
      setModalOpen(false);
      setIsOn(!isOn);
      reload;
    } else {
      alert(res.message);
      setModalOpen(false);
    }
  };
  const toggleHandler = async () => {
    setModalOpen(true);
  };

  return (
    <>
      <ToggleContainer
        // 클릭하면 토글이 켜진 상태(isOn)를 boolean 타입으로 변경하는 메소드가 실행
        onClick={toggleHandler}
      >
        {/* 아래에 div 엘리먼트 2개가 있다. 각각의 클래스를 'toggle-container', 'toggle-circle' 로 지정 */}
        {/* Toggle Switch가 ON인 상태일 경우에만 toggle--checked 클래스를 div 엘리먼트 2개에 모두 추가. 조건부 스타일링을 활용*/}
        <div className={`toggle-container ${isOn ? 'toggle--checked' : null}`} />
        <div className={`toggle-circle ${isOn ? 'toggle--checked' : null}`} />
      </ToggleContainer>
      {modalOpen && (
        <Modal>
          <DarkBg onClick={() => setModalOpen(false)} />
          <Wrapper>
            <div className="qa">{isOn ? '레포지토리를 비공개 하시겠습니까?' : '레포지토리를 공개 하시겠습니까?'}</div>
            <div className="cancelok">
              <CancelOk
                ok={goApi}
                okWord={isOn ? '비공개' : '공개'}
                cancel={() => setModalOpen(false)}
                cancelWord={isOn ? '공개' : '비공개'}
              ></CancelOk>
            </div>
          </Wrapper>
        </Modal>
      )}
    </>
  );
};
