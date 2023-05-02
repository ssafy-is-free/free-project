import styled from 'styled-components';
import { IFilterOptionProps } from './IRank';
import { useEffect, useRef } from 'react';

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

  &:hover {
    background-color: ${(props) => props.theme.primary};
    color: ${(props) => props.theme.fontWhite};
  }
`;

const FilterOption = (props: IFilterOptionProps) => {
  const ref = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const style = ref.current?.style;
    if (style) {
      if (props.isInFilter) {
        style.width = '92%';
      } else {
        style.width = `calc((100% - 24px)/3)`;
      }
    }
  }, []);

  return (
    <Wrapper ref={ref} onClick={props.onClick}>
      {props?.item?.name}
    </Wrapper>
  );
};

export default FilterOption;
