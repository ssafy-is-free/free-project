import styled from 'styled-components';
import MenuArrowIcon from '../../public/Icon/MenuArrowIcon.svg';

interface IProps {
  text: string;
  onClick: () => void;
}

const Wrapper = styled.button`
  background-color: ${(props) => props.theme.primary};
  color: ${(props) => props.theme.fontWhite};
  font-size: 20px;
  padding: 16px 20%;
  border-radius: 8px;
  cursor: pointer;
  width: 80%;
`;

const BigBtn = (props: IProps) => {
  return <Wrapper onClick={props.onClick}>{props.text}</Wrapper>;
};

export default BigBtn;
