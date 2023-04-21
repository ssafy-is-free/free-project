import styled from 'styled-components';

const Wrapper = styled.div`
  background-color: ${(props) => props.theme.primary};
`;

const Main = () => {
  return <Wrapper>main 입니다.</Wrapper>;
};

export default Main;
