import styled, { css, keyframes } from 'styled-components';
import CloseIcon from '../../public/Icon/CloseIcon.svg';
import FilterArrowIcon from '../../public/Icon/FilterArrowIcon.svg';
import { useRef, useState } from 'react';
import { NestedMiddlewareError } from 'next/dist/build/utils';

interface IProps {
  onClick: () => void;
}

interface IOption {
  openOption: { id: number; state: boolean | undefined }[];
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

const Wrapper = styled.div<IOption>`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  padding: 32px 24px;
  position: fixed;
  bottom: 0;
  background-color: ${(props) => props.theme.bgWhite};
  animation: 0.4s ease-in-out 0s ${moveUp};

  .title {
    font-weight: bold;
    font-size: 20px;
    margin-bottom: 44px;
  }

  .filter-box {
    display: flex;
    flex-direction: column;
    width: 100%;
    margin-bottom: 32px;

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
        font-size: 16px;
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

const FilterModal = (props: IProps) => {
  // 옵션 이름
  const optionNames = ['언어', '그룹'];

  // 옵션
  const languages = [
    'Java',
    'Python',
    'C++',
    'C#',
    'C',
    'Javascript',
    'Ruby',
    'Kotlin',
    'Swift',
    'Go',
    'Php',
    'Rust',
    'Pascal',
    'Scala',
    'R',
  ];
  // 옵션2
  const groups = ['Ssafy', 'Samsung'];
  const optionTypes = [languages, groups];

  // option창 보이기
  const [openOption, setOpenOption] = useState<{ id: number; state: boolean | undefined }[]>([
    { id: 1, state: false },
    { id: 2, state: false },
  ]);

  // option 카운트 변수
  const [selected, setSelected] = useState<({ id: number; itemArr: number[] | undefined } | undefined)[]>([
    { id: 1, itemArr: [] },
    { id: 2, itemArr: [] },
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

  // option 클릭 시
  const onClickOption = (itemIdx: number, parentIdx: number) => {
    const style = itemRefs.current[parentIdx].childNodes[itemIdx].style;

    const newArr = selected.map((item, idx) => {
      if (parentIdx == idx) {
        if (!selected.at(idx)?.itemArr?.includes(itemIdx)) {
          // 존재하지 않는 경우 => 배열에 추가,  style 교체
          style.backgroundColor = '#4A58A9';
          style.color = '#ffffff';

          let newItemArr = selected.at(idx)?.itemArr;

          if (newItemArr != undefined) {
            return {
              id: idx + 1,
              itemArr: [...newItemArr, itemIdx],
            };
          }
        } else {
          // 존재하는 경우 => 배열에서 제거, style 교체
          style.backgroundColor = '#ffffff';
          style.color = '#4A58A9';

          let newItemArr = selected.at(idx)?.itemArr?.filter((el) => el != itemIdx);

          return {
            id: idx + 1,
            itemArr: newItemArr,
          };
        }
      } else {
        return {
          id: idx + 1,
          itemArr: selected.at(idx)?.itemArr,
        };
      }
    });

    setSelected(newArr);
  };

  return (
    <>
      <DarkBg onClick={props.onClick} />
      <Wrapper openOption={openOption}>
        <StyledCloseIcon onClick={props.onClick} />
        <div className="title">검색 필터</div>
        {optionTypes.map((el, idx) => {
          return (
            <div className="filter-box" key={idx}>
              <div className="box-top" ref={(el) => (arrowRefs.current[idx] = el)}>
                <div className="label">
                  {optionNames[idx]}
                  {selected[idx]?.itemArr?.length ? (
                    <div className="selected-label">{selected[idx]?.itemArr?.length}개 선택</div>
                  ) : null}
                </div>
                <StyledFilterArrowIcon className="arrow" onClick={() => onHandleOptionBox(idx + 1)} />
              </div>
              <div className="box-content" ref={(el) => (itemRefs.current[idx] = el)}>
                {el.map((item, itemIdx) => {
                  return (
                    <div className="option-item" key={itemIdx} onClick={() => onClickOption(itemIdx, idx)}>
                      {item}
                    </div>
                  );
                })}
              </div>
            </div>
          );
        })}
      </Wrapper>
    </>
  );
};

export default FilterModal;
