import styled from 'styled-components';
import { IBigBtnProps } from './ICommon';

const Wrapper = styled.button`
  background-color: ${(props) => props.theme.primary};
  color: ${(props) => props.theme.fontWhite};
  font-size: 20px;
  padding: 16px 20%;
  border-radius: 8px;
  cursor: pointer;
  width: 80%;
`;

const BigBtn = (props: IBigBtnProps) => {
  return <Wrapper onClick={props.onClick}>{props.text}</Wrapper>;
};

export default BigBtn;
