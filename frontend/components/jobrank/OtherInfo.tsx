import styled from 'styled-components';
import { IOtherInfoProps } from './IJobrank';
import CommitIcon from '../../public/Icon/CommitIcon.svg';
import RepoIcon from '../../public/Icon/RepoIcon.svg';
import StarIcon from '../../public/Icon/StarIcon.svg';

const Wrapper = styled.div`
  display: flex;
  width: 100%;
  margin-bottom: 48px;

  .commit-box {
    background-color: ${(props) => props.theme.bgWhite};
    border: 1px solid ${(props) => props.theme.secondary};
    width: 45%;
    margin-bottom: 8px;
    margin-right: 8px;
    display: flex;
    flex-direction: column;
    justify-content: end;
    padding: 14px 16px;
    border-radius: 16px;
    position: relative;

    .info-label {
      font-size: 14px;
      color: ${(props) => props.theme.fontDarkGray};
    }

    .data {
      font-size: 20px;
      font-weight: bold;
      color: ${(props) => props.theme.fontBlack};
      margin-bottom: 4px;
    }
  }

  .info-sub-box {
    display: flex;
    flex-direction: column;
    width: 55%;

    .star-box,
    .repo-box {
      background-color: ${(props) => props.theme.bgWhite};
      border: 1px solid ${(props) => props.theme.secondary};
      width: 100%;
      margin-bottom: 8px;
      display: flex;
      flex-direction: column;
      padding: 14px 16px;
      border-radius: 16px;
      position: relative;

      .info-label {
        font-size: 14px;
        color: ${(props) => props.theme.fontDarkGray};
      }

      .data {
        font-size: 20px;
        font-weight: bold;
        color: ${(props) => props.theme.fontBlack};
        margin-bottom: 4px;
      }
    }
  }

  .icon-box {
    width: 32px;
    height: 32px;
    position: absolute;
    top: 8px;
    right: 8px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
  }
`;

const OtherInfo = (props: IOtherInfoProps) => {
  return (
    <Wrapper>
      <div className="commit-box">
        {props.otherInfo?.commit && <span className="data">{Math.floor(props.otherInfo?.commit)}</span>}
        <span className="info-label">Commits</span>
        <div className="icon-box" style={{ backgroundColor: '#ff8a60' }}>
          <CommitIcon />
        </div>
      </div>
      <div className="info-sub-box">
        <div className="star-box">
          {props.otherInfo?.star && <span className="data">{Math.floor(props.otherInfo?.star)}</span>}
          <span className="info-label">Stars</span>
          <div className="icon-box" style={{ backgroundColor: '#ffdd60' }}>
            <StarIcon />
          </div>
        </div>
        <div className="repo-box">
          {props.otherInfo?.repositories && <span className="data">{Math.floor(props.otherInfo?.repositories)}</span>}
          <span className="info-label">Repositories</span>
          <div className="icon-box" style={{ backgroundColor: '#60e7ff' }}>
            <RepoIcon />
          </div>
        </div>
      </div>
    </Wrapper>
  );
};

export default OtherInfo;
