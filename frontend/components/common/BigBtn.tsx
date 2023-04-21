import styled from 'styled-components';
import MenuArrowIcon from '../../public/Icon/MenuArrowIcon.svg';

interface IProps {
  text: string;
}

const Wrapper = styled.button`
  background-color: ${(props) => props.theme.primary};
  color: ${(props) => props.theme.fontWhite};
  font-size: 20px;
  padding: 16px 20%;
  border-radius: 8px;
  cursor: pointer;
`;

const BigBtn = (props: IProps) => {
  return <Wrapper>{props.text}</Wrapper>;
};

export default BigBtn;
