import styled from 'styled-components';
import { IFilterOptionProps } from './IRank';
import { useEffect, useRef } from 'react';
import CloseIcon from '../../public/Icon/CloseIcon.svg';
import { useDispatch } from 'react-redux';
import { setFilter } from '@/redux/rankSlice';

const Wrapper = styled.div`
  border: 1px solid ${(props) => props.theme.primary};
  color: ${(props) => props.theme.primary};
  font-size: 0.8rem;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;

  p {
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .del-btn {
    width: 10px;
    height: 10px;
    display: flex;
    align-items: center;
    margin-left: 8px;
  }
`;

const StyledCloseIcon = styled(CloseIcon)`
  path {
    fill: ${(props) => props.theme.fontGray};
  }
`;

const FilterOption = (props: IFilterOptionProps) => {
  const ref = useRef<HTMLDivElement>(null);

  const dispatch = useDispatch();

  useEffect(() => {
    const style = ref.current?.style;
    if (style) {
      if (props.isInFilter) {
        // 필터 모달창 안에 있는 옵션인 경우
        style.width = '92%';
        style.borderRadius = '50px';
        style.height = '40px';
      } else {
        // 랭킹 메인 페이지에 보여지는 옵션인 경우
        style.width = ``;
        style.borderRadius = '8px';
        style.height = '32px';
        style.backgroundColor = '#F5F5F5';
        style.border = 'none';
        style.color = '#B2B2B2';
        style.display = 'inline-flex';
        style.padding = ' 0 8px';
      }
    }
  }, []);

  const onDelete = () => {
    if (props.getRankList && props.setNoMore) {
      props.getRankList(0);
      props.setNoMore(false);
      dispatch(setFilter(null));
    }
  };

  return (
    <Wrapper ref={ref} onClick={props.onClick}>
      <p> {props?.item?.name}</p>

      {props.isInMain && (
        <div className="del-btn">
          <StyledCloseIcon onClick={onDelete} />
        </div>
      )}
    </Wrapper>
  );
};

export default FilterOption;
