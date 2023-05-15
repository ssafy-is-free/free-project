import styled, { css, keyframes } from 'styled-components';
import CloseIcon from '../../public/Icon/CloseIcon.svg';
import FilterArrowIcon from '../../public/Icon/FilterArrowIcon.svg';
import { useEffect, useRef, useState } from 'react';
import { NestedMiddlewareError } from 'next/dist/build/utils';
import CancelOk from '../common/CancelOk';
import { IFilterModalProps } from './IRank';
import { getFilter, getGithubRanking, getMyBojRanking, getMyGitRanking } from '@/pages/api/rankAxios';
import FilterOption from './FilterOption';
import { useDispatch, useSelector } from 'react-redux';
import { setFilter } from '@/redux/rankSlice';
import { RootState } from '@/redux';
import { current } from '@reduxjs/toolkit';

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

  .close-box {
    position: absolute;
    top: 32px;
    left: 32px;
    cursor: pointer;
    width: 20px;
    height: 20px;
  }

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
    }
  }
`;

const StyledFilterArrowIcon = styled(FilterArrowIcon)`
  cursor: pointer;
`;

const StyledCancelOk = styled(CancelOk)`
  font-size: 14px;
`;

const FilterModal = (props: IFilterModalProps) => {
  const dispatch = useDispatch();
  // 옵션
  const filter = useSelector<RootState>((selector) => selector.rankChecker.filter);
  const filterName = useSelector<RootState>((selector) => selector.rankChecker.filter?.name);
  const filterId = useSelector<RootState>((selector) => selector.rankChecker.filter?.languageId);

  // 옵션 언어 목록
  const [languages, setLanguages] = useState<
    {
      languageId: number;
      name: string;
    }[]
  >([]);

  // 클릭한 element 접근
  const itemRefs = useRef<any>([]);
  const arrowRefs = useRef<any>([]);

  // 선택된 옵션 정보, redux에 담을 것
  const [selectedItem, setSelectedItem] = useState<{ languageId: number; name: string } | null>(null);

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

  // option 창 여닫기 함수
  const onHandleOptionBox = () => {
    // 옵션 박스 style
    const itemRefStyle = itemRefs.current.style;
    const arrowRefStyle = arrowRefs.current.childNodes[1].style;

    if (itemRefStyle.opacity == 0) {
      itemRefStyle.opacity = '1';
      itemRefStyle.visibility = 'visible';
      itemRefStyle.maxHeight = '240px';
      arrowRefStyle.transform = 'rotate(180deg)';

      // 이미 필터가 있다면 해당 필터 색깔 칠해지기
      setSelectedItem({
        languageId: Number(filterId),
        name: String(filterName),
      });
      itemRefs.current.childNodes.forEach((el: HTMLDivElement, idx: number) => {
        let text = (el.childNodes[0] as HTMLDivElement).innerHTML.trim();
        const selectedOptionStyle = el.style;
        if (text == String(filterName)) {
          // 이미 선택되어있는 필터 옵션이라면
          selectedOptionStyle.backgroundColor = '#4A58A9';
          selectedOptionStyle.color = '#ffffff';
        }
      });
    } else {
      itemRefStyle.opacity = '0';
      itemRefStyle.visibility = 'hidden';
      itemRefStyle.maxHeight = '0';
      arrowRefStyle.transform = 'rotate(0deg)';
    }
  };

  // option 클릭 시
  // TODO : 스타일 이렇게 일일이 말고 다른 방법 있는지 찾아보기
  const onClickOption = (item: { languageId: number; name: string }, itemIdx: number) => {
    // 옵션 스타일 바꿔주기 => 파란색
    itemRefs.current.childNodes.forEach((el: HTMLDivElement, idx: number) => {
      const selectedOptionStyle = el.style;
      // if (idx === itemIdx && item.languageId != Number(filterId)) {
      if (idx === itemIdx) {
        // 선택된 옵션일 때
        selectedOptionStyle.backgroundColor = '#4A58A9';
        selectedOptionStyle.color = '#ffffff';
      } else {
        selectedOptionStyle.backgroundColor = '#ffffff';
        selectedOptionStyle.color = '#4A58A9';
      }
    });

    setSelectedItem({
      languageId: item.languageId,
      name: item.name,
    });
  };

  // 초기화 버튼 클릭 시
  const onInit = () => {
    setSelectedItem(null);

    itemRefs.current.childNodes.forEach((el: HTMLDivElement) => {
      const selectedOptionStyle = el.style;
      selectedOptionStyle.backgroundColor = '#ffffff';
      selectedOptionStyle.color = '#4A58A9';
    });
  };

  const onClickFilter = () => {
    if (selectedItem) {
      // 선택한 것이 있을 떄

      // redux에 필터 값 담기
      dispatch(setFilter(selectedItem));

      // 필터 반영해서 랭킹 정보 갱신
      props.getRankList(0, selectedItem?.languageId);
    } else {
      // 필터를 선택하지 않았을 때
      props.getRankList(0);
      dispatch(setFilter(null));
      setSelectedItem(null);
    }

    // 모달창 닫기
    props.setNoMore(false);
    props.onClick();
  };

  return (
    <>
      <DarkBg onClick={props.onClick} />
      <Wrapper>
        <div className="close-box">
          <CloseIcon onClick={props.onClick} />
        </div>
        <div className="title">검색 필터</div>

        <div className="filter-box">
          <div className="box-top" ref={arrowRefs} onClick={() => onHandleOptionBox()}>
            <div className="label">언어</div>
            <StyledFilterArrowIcon className="arrow" />
          </div>
          <div className="box-content" ref={itemRefs}>
            {languages.map((item, itemIdx) => {
              return (
                <FilterOption
                  key={itemIdx}
                  isInFilter={true}
                  item={item}
                  onClick={() => onClickOption(item, itemIdx)}
                />
              );
            })}
          </div>
        </div>
        <StyledCancelOk cancelWord="초기화" okWord="필터적용" cancel={onInit} ok={onClickFilter} />
      </Wrapper>
    </>
  );
};

export default FilterModal;
