import styled, { keyframes } from 'styled-components';

const loading = keyframes`
 100% {
      transform: translateX(230%);
    }
    `;

const Wrapper = styled.div`
  display: flex;
  width: 100%;
  margin-bottom: 48px;

  .commit-box2 {
    background-color: #e6e6e6;
    width: 45%;
    height: 156px;
    margin-bottom: 8px;
    margin-right: 8px;
    display: flex;
    flex-direction: column;
    justify-content: end;
    border-radius: 16px;
    position: relative;

    &::after {
      display: block;
      content: '';
      position: absolute;
      bottom: 0px;
      width: 30%;
      height: 100%;
      transform: translate(0%);
      background: linear-gradient(90deg, transparent, #ffffff44, transparent);
      animation: ${loading} 1.2s infinite;
    }
  }

  .info-sub-box2 {
    display: flex;
    flex-direction: column;
    width: 55%;

    .star-box2,
    .repo-box2 {
      background-color: #e6e6e6;
      height: 74px;
      width: 100%;
      margin-bottom: 8px;
      display: flex;
      flex-direction: column;
      border-radius: 16px;
      position: relative;

      &::after {
        display: block;
        content: '';
        position: absolute;
        bottom: 0px;
        width: 30%;
        height: 100%;
        transform: translate(0%);
        background: linear-gradient(90deg, transparent, #ffffff44, transparent);
        animation: ${loading} 1.2s infinite;
      }
    }
  }
`;

const OtherInfoLoading = () => {
  return (
    <Wrapper>
      <div className="commit-box2" />
      <div className="info-sub-box2">
        <div className="star-box2" />
        <div className="repo-box2" />
      </div>
    </Wrapper>
  );
};

export default OtherInfoLoading;
