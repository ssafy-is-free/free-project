import styled, { keyframes } from 'styled-components';

const loading = keyframes`
 100% {
      transform: translateX(230%);
    }
    `;

const Wrapper = styled.div`
  position: relative;
  width: 95%;
  display: flex;
  justify-content: center;
  align-items: center;

  .circle {
    background-color: #e6e6e6;
    width: 95%;
    height: 95%;
    border-radius: 50%;

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
  }
`;

const ChartJobrankLoading = () => {
  return (
    <Wrapper>
      <div className="circle"></div>
    </Wrapper>
  );
};

export default ChartJobrankLoading;
