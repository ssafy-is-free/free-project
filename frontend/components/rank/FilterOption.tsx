import styled from 'styled-components';
import { IFilterOptionProps } from './IRank';

const Wrapper = styled.div`
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
`;

const FilterOption = (props: IFilterOptionProps) => {
  return <Wrapper onClick={props.onClick}> {props.item.name}</Wrapper>;
};

export default FilterOption;
