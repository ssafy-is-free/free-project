import styled from 'styled-components';
import { IFilterOptionProps } from './IRank';
import { useEffect, useRef } from 'react';
import CloseIcon from '../../public/Icon/CloseIcon.svg';
import { useDispatch } from 'react-redux';
import { setFilter } from '@/redux/rankSlice';

const Wrapper = styled.div`
  border: 1px solid ${(props) => props.theme.primary};
  border-radius: 50px;
  color: ${(props) => props.theme.primary};
  font-size: 0.8rem;
  text-align: center;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;

  p {
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 80%;
  }

  /* &:hover {
    background-color: ${(props) => props.theme.primary};
    color: ${(props) => props.theme.fontWhite};
  } */

  .del-btn {
    position: relative;
    width: 20%;
    height: 100%;
    display: flex;
    align-items: center;
  }
`;

const StyledCloseIcon = styled(CloseIcon)`
  /* position: absolute; */
  /* right: 8px; */
  path {
    fill: ${(props) => props.theme.primary};
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
      } else {
        style.width = `calc((100% - 24px)/3)`;
        style.position = 'absolute';
        style.top = '2rem';
        style.left = '2rem';
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
