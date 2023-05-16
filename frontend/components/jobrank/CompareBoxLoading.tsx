import styled, { keyframes } from 'styled-components';

const loading = keyframes`
 100% {
      transform: translateX(230%);
    }
    `;

const Wrapper = styled.div`
  width: 100%;
  margin-top: 24px;

  .content-wrapper {
    border-radius: 16px;
    width: 100%;

    .content-top {
      width: 100%;
      display: flex;

      .content-top-left,
      .content-top-right {
        width: 50%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;

        .my-img {
          background-color: #e6e6e6;
          border-radius: 50%;
          width: 64px;
          height: 64px;
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
        }

        .name {
          background-color: #e6e6e6;
          width: 64px;
          height: 16px;
          border-radius: 50px;
          margin-top: 16px;
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
        }
      }
    }

    .content {
      margin-top: 24px;
      width: 100%;
      height: 460px;
      background-color: #e6e6e6;
      border-radius: 8px;
      display: flex;
      flex-direction: column;
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
    }
  }
`;

const CompareBoxLoading = () => {
  return (
    <Wrapper>
      <div className="content-wrapper">
        <div className="content-top">
          <div className="content-top-left">
            <div className="my-img"></div>
            <div className="name"></div>
          </div>
          <div className="content-top-right">
            <div className="my-img"></div>
            <div className="name"></div>
          </div>
        </div>
        <div className="content"></div>
      </div>
    </Wrapper>
  );
};

export default CompareBoxLoading;
