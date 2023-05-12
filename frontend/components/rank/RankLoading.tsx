import styled, { keyframes } from 'styled-components';

const loading = keyframes`
 100% {
      transform: translateX(230%);
    }
    `;

const Wrapper = styled.div`
  background-color: #e6e6e6;
  border-radius: 8px;
  height: 56px;
  display: flex;
  align-items: center;
  color: ${(props) => props.theme.fontWhite};
  font-weight: bold;
  font-size: 14px;
  width: 100%;
  position: relative;

  &::after {
    display: block;
    content: '';
    position: absolute;
    width: 30%;
    height: 100%;
    transform: translate(0%);
    background: linear-gradient(90deg, transparent, #ffffff44, transparent);
    animation: ${loading} 1.2s infinite;
  }
`;

const RankLoading = () => {
  return <Wrapper></Wrapper>;
};

export default RankLoading;
