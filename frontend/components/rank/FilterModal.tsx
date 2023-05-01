import styled, { css, keyframes } from 'styled-components';
import CloseIcon from '../../public/Icon/CloseIcon.svg';
import FilterArrowIcon from '../../public/Icon/FilterArrowIcon.svg';
import { useEffect, useRef, useState } from 'react';
import { NestedMiddlewareError } from 'next/dist/build/utils';
import CancelOk from '../common/CancelOk';
import { IFilterModalProps } from './IRank';
import { getFilter, getGithubRanking, getMyBojRanking, getMyGitRanking } from '@/pages/api/rankAxios';

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
  /* padding: 32px 24px; */
  padding: 32px 0px 0px;
  position: fixed;
  z-index: 15;
  bottom: 0;
  background-color: ${(props) => props.theme.bgWhite};
  animation: 0.4s ease-in-out 0s ${moveUp};

  .title {
    font-weight: bold;
    font-size: 20px;
    margin-bottom: 44px;
    padding: 0 24px;
  }

  .filter-box {
    display: flex;
    flex-direction: column;
    width: 100%;
    margin-bottom: 32px;
    padding: 0 24px;

    .box-top {
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-bottom: 16px;

      .label {
        font-size: 20px;
        font-weight: bold;
        display: flex;
        align-items: center;

        .selected-label {
          color: ${(props) => props.theme.primary};
          font-size: 14px;
          margin-left: 16px;
        }
      }
    }

    .box-content {
      overflow-y: scroll;
      max-height: 0;
      opacity: 0;
      visibility: hidden;
      display: grid;
      grid-template-columns: 1fr 1fr 1fr;
      row-gap: 8px;
      justify-items: center;
      transition: 0.4s;

      .option-item {
        border: 1px solid ${(props) => props.theme.primary};
        border-radius: 50px;
        color: ${(props) => props.theme.primary};
        font-size: 0.8rem;
        text-align: center;
        width: 92%;
        height: 40px;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;

        &:hover {
          background-color: ${(props) => props.theme.primary};
          color: ${(props) => props.theme.fontWhite};
        }
      }
    }
  }
`;

const StyledCloseIcon = styled(CloseIcon)`
  position: absolute;
  top: 32px;
  left: 32px;
  cursor: pointer;
`;

const StyledFilterArrowIcon = styled(FilterArrowIcon)`
  cursor: pointer;
`;

const StyledCancelOk = styled(CancelOk)`
  font-size: 14px;
`;

const FilterModal = (props: IFilterModalProps) => {
  // 옵션 이름
  // const optionNames = ['언어', '그룹'];
  const optionNames = ['언어'];

  // 옵션
  const [languages, setLanguages] = useState<
    {
      languageId: number;
      name: string;
    }[]
  >([]);
  // const [groups, setGroups] = useState<string[]>([]);
  const [optionTypes, setOptionTypes] = useState<
    {
      languageId: number;
      name: string;
    }[][]
  >([]);

  // filter 목록 가져오기
  useEffect(() => {
    (async () => {
      if (props.curRank == 0) {
        // 깃허브
        const data = await getFilter('GITHUB');
        setLanguages([...data]);
      } else {
        // 백준
        const data = await getFilter('BAEKJOON');
        setLanguages([...data]);
      }
    })();
  }, []);

  useEffect(() => {
    setOptionTypes([languages]);
  }, [languages]);

  // option창 보이기
  const [openOption, setOpenOption] = useState<{ id: number; state: boolean | undefined }[]>([
    { id: 1, state: false },
    { id: 2, state: false },
  ]);

  // 클릭한 element 접근
  const itemRefs = useRef<any>([]);
  const arrowRefs = useRef<any>([]);

  // option 창 여닫기 함수
  const onHandleOptionBox = (el: number) => {
    const newArr = openOption.map((item, idx) => {
      if (el == idx + 1) {
        return {
          id: idx + 1,
          state: !openOption.at(idx)?.state,
        };
      } else {
        return {
          id: idx + 1,
          state: openOption.at(idx)?.state,
        };
      }
    });
    const itemRefStyle = itemRefs.current[el - 1].style;
    if (newArr[el - 1]?.state) {
      itemRefStyle.opacity = '1';
      itemRefStyle.visibility = 'visible';
      itemRefStyle.maxHeight = '240px';

      // 아이콘
      arrowRefs.current[el - 1].childNodes[1].style.transform = 'rotate(180deg)';
    } else {
      itemRefStyle.opacity = '0';
      itemRefStyle.visibility = 'hidden';
      itemRefStyle.maxHeight = '0';

      // 아이콘
      arrowRefs.current[el - 1].childNodes[1].style.transform = 'rotate(0deg)';
    }
    setOpenOption(newArr);
  };

  // TODO : 그룹 추가되면 2차원 배열로 변경
  // 선택된 옵션
  const [selected, setSelected] = useState<number[]>();

  useEffect(() => {
    if (selected?.length && selected?.length > 0) {
      const arr = itemRefs.current[selected[0]].childNodes;

      arr.forEach((element: any, idx: number) => {
        const style = itemRefs.current[selected[0]].childNodes[idx].style;
        if (idx == selected[1]) {
          style.backgroundColor = '#4A58A9';
          style.color = '#ffffff';
        } else {
          style.backgroundColor = '#ffffff';
          style.color = '#4A58A9';
        }
      });
    }
  }, [selected]);

  // option 클릭 시
  const onClickOption = (itemIdx: number, parentIdx: number, languageId: number) => {
    setSelected([parentIdx, itemIdx, languageId]);
  };

  // TODO : 더 좋은 방법이 없을까..?
  // 초기화 버튼 클릭 시
  const onInit = () => {
    setSelected([]);

    itemRefs.current.map((parent: any) => {
      parent.childNodes.forEach((child: any) => {
        child.style.backgroundColor = '#ffffff';
        child.style.color = '#4A58A9';
      });
    });
  };

  const onClickFilter = () => {
    if (selected && selected?.length > 0) {
      // 필터를 선택했을 때
      if (selected) {
        props.getRankList(props.size, 1, selected[2]);
      }
    } else {
      // 필터를 선택하지 않았을 때
      props.getRankList(props.size, 1);
    }

    // 모달창 닫기
    props.onClick();
  };

  return (
    <>
      <DarkBg onClick={props.onClick} />
      <Wrapper>
        <StyledCloseIcon onClick={props.onClick} />
        <div className="title">검색 필터</div>
        {optionTypes.map((el, idx) => {
          return (
            <div className="filter-box" key={idx}>
              <div
                className="box-top"
                ref={(el) => (arrowRefs.current[idx] = el)}
                onClick={() => onHandleOptionBox(idx + 1)}
              >
                <div className="label">{optionNames[idx]}</div>
                <StyledFilterArrowIcon className="arrow" />
              </div>
              <div className="box-content" ref={(el) => (itemRefs.current[idx] = el)}>
                {el.map((item, itemIdx) => {
                  return (
                    <div
                      className="option-item"
                      key={itemIdx}
                      onClick={() => onClickOption(itemIdx, idx, item.languageId)}
                    >
                      {item.name}
                    </div>
                  );
                })}
              </div>
            </div>
          );
        })}
        <StyledCancelOk cancelWord="초기화" okWord="필터적용" cancel={onInit} ok={onClickFilter} />
      </Wrapper>
    </>
  );
};

export default FilterModal;
