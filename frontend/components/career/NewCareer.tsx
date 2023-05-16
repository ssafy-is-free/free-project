import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import styled from 'styled-components';
import CareerSearch from './NewCareerSearch';
import StatusModal from './ModalStatus';
import DdayModal from './ModalDday';
import { postJob } from '@/pages/api/careerAxios';
import BgLoading from './BgLoading';
import { ICareerStatus, IDefaultDate, INewCareerProps, ISearchResult } from './ICareer';
import Swal from 'sweetalert2';
import { fadeIn2 } from './SCareer';

const NewCareerDiv = styled.div`
  width: 100vw;
  height: 100vh;
  top: 0;
  position: fixed;
  background-color: white;
  padding-bottom: max(10vh, 4rem);
  z-index: 5;

  animation: ${fadeIn2} 0.5s;

  overflow-y: scroll;
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none; /* Firefox */
  &::-webkit-scrollbar {
    display: none; /* Chrome, Safari, Opera*/
  }
`;

const HeaderDiv = styled.div`
  margin: 1rem;
  margin-top: 2rem;
  height: 3rem;
  display: flex;
  justify-content: space-between;

  img {
    height: 70%;
  }
  button {
    background-color: transparent;
    display: flex;
    align-items: center;
    h3 {
      font-size: large;
      color: ${(props) => props.theme.primary};
    }
  }
`;

export const InputDiv = styled.div`
  padding: 1rem;
  div {
    margin-bottom: 0.2rem;
  }
  .input {
    background-color: ${(props) => props.theme.lightGray};
    color: ${(props) => props.theme.fontBlack};
    font-size: 1rem;
    padding: 0.5rem;
    border-radius: 0.5rem;
    width: 100%;
    border: 2px solid transparent;
    font-family: inherit;

    &:focus {
      outline: none;
      border-color: ${(props) => props.theme.primary};
    }
    &::placeholder {
      white-space: pre-wrap;
      text-align: center;
      color: ${(props) => props.theme.fontGray};
    }
  }
`;

const NewCareer = ({ close }: INewCareerProps) => {
  const router = useRouter();
  const [postingId, setPostingId] = useState<number>(0);
  const [statusId, setStatusId] = useState<number>(0);
  const [postingName, setPostingName] = useState<any>();
  const [companyName, setCompanyName] = useState<any>();
  const [startTime, setStartTime] = useState<any>();
  const [endTime, setEndTime] = useState<any>();
  const [status, setStatus] = useState<any>();
  const [dDayName, setDDayName] = useState<any>();
  const [dDay, setDDay] = useState<any>();
  const [defaultDate, setDefaultDate] = useState<IDefaultDate>();

  // 화면에 보여줄 요소
  const [searchOpen, setSearchOpen] = useState<boolean>(false);
  const [statusOpen, setStatusOpen] = useState<boolean>(false);
  const [dDayOpen, setDDayOpen] = useState<boolean>(false);
  const [loading, setLoading] = useState<boolean>(false);

  const showList = [
    {
      key: 'postingName',
      label: '공고명',
      placeholder: '클릭하면 검색할 수 있어요',
      readonly: true,
      value: postingName,
    },
    {
      key: 'companyName',
      label: '기업명',
      placeholder: '클릭하면 검색할 수 있어요',
      readonly: true,
      value: companyName,
    },
    { key: 'startTime', label: '접수 시작일', placeholder: '', readonly: true, value: startTime },
    { key: 'endTime', label: '접수 마감일', placeholder: '', readonly: true, value: endTime },
    { key: 'status', label: '현재 진행 상태', placeholder: 'ex) 서류 합격, 코테 합격', readonly: true, value: status },
  ];

  const inputList = [
    { key: 'objective', label: '지원 직무', placeholder: 'ex) 백엔드, 프론트엔드', readonly: false },
    {
      key: 'ddayName',
      label: '다음 일정 이름',
      placeholder: 'ex) 1차 면접, 코딩테스트',
      readonly: false,
      value: dDayName,
    },
    { key: 'dday', label: '다음 일정', placeholder: '', readonly: true, value: dDay },
  ];

  const inputClick = (e: React.MouseEvent<HTMLInputElement>) => {
    const { id } = e.target as HTMLInputElement;
    // 공고명, 기업명을 누를경우 검색창 오픈
    if (['postingName', 'companyName'].includes(id)) {
      setSearchOpen(true);
    } else if (id === 'status') {
      setStatusOpen(true);
    } else if (['dday', 'ddayName'].includes(id)) {
      setDDayOpen(true);
    }
  };

  // 등록하기 버튼 클릭시
  const newPost = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const form = e.currentTarget;
    const formData = new FormData(form);
    const formJson = Object.fromEntries(formData.entries());
    if (formJson.jobPostingId === '0') {
      Swal.fire({
        text: '취업공고를 선택해주세요',
        icon: 'warning',
      }).then((result: any) => {
        if (result.isConfirmed) {
          setSearchOpen(true);
        }
      });
    } else if (formJson.statusId === '0') {
      Swal.fire({
        text: '현재 진행 상태를 선택해주세요',
        icon: 'warning',
      }).then((result: any) => {
        if (result.isConfirmed) {
          setStatusOpen(true);
        }
      });
    } else if (formJson.objective === '') {
      Swal.fire({
        text: '지원 직무를 입력해주세요',
        icon: 'warning',
      });
    } else {
      setLoading(true);
      const res = await postJob(formJson);
      if (res.status === 'SUCCESS') {
        setLoading(false);
        router.reload();
      } else {
        Swal.fire({
          title: 'Error!',
          text: res.message,
          icon: 'error',
        }).then((result: any) => {
          if (result.isConfirmed) {
            setLoading(false);
          }
        });
      }
    }
  };

  // 검색 결과 업데이트
  const searchResult = (item: ISearchResult) => {
    setPostingId(item.jobPostingId);
    setPostingName(item.postingName);
    setCompanyName(item.companyName);
    if (item.startTime === '1996-11-22') {
      setStartTime('미정');
    } else {
      setStartTime(item.startTime);
    }
    if (item.endTime === '1996-11-22') {
      setEndTime('미정');
    } else {
      setEndTime(item.endTime);
    }
  };

  const statusReasut = (status: ICareerStatus) => {
    setStatusId(status.id);
    setStatus(status.name);
  };

  const dDayResult = (res: any) => {
    setDDayName(res.ddayName);
    setDDay(res.date);
  };

  const getToday = () => {
    const today = new Date();
    const year = today.getFullYear() - 2020;
    const month = today.getMonth();
    const day = today.getDate() - 1;
    setDefaultDate({
      year,
      month,
      day,
    });
  };

  useEffect(() => {
    getToday();
  }, []);

  return (
    <NewCareerDiv>
      <form action="post" id="careerForm" onSubmit={newPost}>
        <HeaderDiv>
          <div>
            <img src="/Icon/CloseIcon.svg" alt="#" onClick={close} />
            {/* <img src="/Icon/CloseIcon.svg" alt="#" onClick={test} /> */}
          </div>
          <button type="submit">
            <h3>등록하기</h3>
          </button>
        </HeaderDiv>
        {showList.map((input) => (
          <InputDiv key={input.label}>
            <div>{input.label}</div>
            <input
              id={input.key}
              onClick={inputClick}
              type="text"
              className="input"
              placeholder={input.placeholder}
              value={input.value}
              readOnly={input.readonly}
              autoComplete="off"
            ></input>
          </InputDiv>
        ))}
        <input type="hidden" name="jobPostingId" value={postingId} required />
        <input type="hidden" name="statusId" value={statusId} required />
        {inputList.map((input) => (
          <InputDiv key={input.label}>
            <div>{input.label}</div>
            <input
              id={input.key}
              name={input.key}
              onClick={inputClick}
              type="text"
              className="input"
              placeholder={input.placeholder}
              value={input.value}
              readOnly={input.readonly}
              autoComplete="off"
            ></input>
          </InputDiv>
        ))}
        <InputDiv>
          <div>메모</div>
          <textarea className="input" name="memo" form="careerForm" rows={4} placeholder=""></textarea>
        </InputDiv>
      </form>
      {searchOpen && (
        <CareerSearch close={() => setSearchOpen(false)} result={(item) => searchResult(item)}></CareerSearch>
      )}
      {statusOpen && (
        <StatusModal
          close={() => setStatusOpen(false)}
          result={(statusInput) => statusReasut(statusInput)}
        ></StatusModal>
      )}
      {dDayOpen && defaultDate && (
        <DdayModal
          close={() => setDDayOpen(false)}
          result={(res) => dDayResult(res)}
          defaultDate={defaultDate}
        ></DdayModal>
      )}
      {loading && <BgLoading></BgLoading>}
    </NewCareerDiv>
  );
};

export default NewCareer;
