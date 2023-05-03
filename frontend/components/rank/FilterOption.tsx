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
  /* position: relative; */

  p {
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    /* width: 100%; */
  }

  /* &:hover {
    background-color: ${(props) => props.theme.primary};
    color: ${(props) => props.theme.fontWhite};
  } */

  .del-btn {
    /* position: absolute; */
    /* right: 8px; */
    width: 10px;
    height: 10px;
    display: flex;
    align-items: center;
    margin-left: 8px;
  }
`;

const StyledCloseIcon = styled(CloseIcon)`
  /* position: absolute; */
  /* right: 8px; */
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
        style.width = '92%';
        style.borderRadius = '50px';
        style.height = '40px';
      } else {
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
    if (props.getRankList && props.setSelectedOption) {
      props.setSelectedOption(null);
      props.getRankList(props.size, 1);
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
