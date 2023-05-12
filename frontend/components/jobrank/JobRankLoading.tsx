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
  padding: 0px 14px;
  color: ${(props) => props.theme.fontBlack};
  font-weight: bold;
  font-size: 14px;
  width: calc(100% - 64px);
  position: absolute;
  top: -28px;
  left: 32px;
  box-shadow: 4px 4px 20px #00000026;

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

const JobRankLoading = () => {
  return <Wrapper></Wrapper>;
};

export default JobRankLoading;
