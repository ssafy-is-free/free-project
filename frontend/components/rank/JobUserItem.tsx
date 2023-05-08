import styled from 'styled-components';
import { IJobUserItemProps } from './IRank';

const Wrapper = styled.div`
  background-color: ${(props) => props.theme.bgWhite};
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
  bottom: -28px;
  left: 32px;
  box-shadow: 4px 4px 20px #00000026;

  .rank-num {
    width: 25%;
    height: 100%;
    display: flex;
    align-items: center;
    text-align: left;
  }

  .center {
    width: 55%;
    height: 100%;
    display: flex;
    align-items: center;

    .user-photo {
      width: 32px;
      height: 32px;
      border-radius: 50%;
      margin-right: 16px;
    }
    .user-nickname {
      width: 50%;
      display: flex;
      align-items: center;

      .user-tier {
        width: 24px;
        height: 24px;
        margin-left: 8px;
      }
    }
  }

  .user-score {
    width: 20%;
    text-align: right;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: end;
  }
`;

const JobUserItem = (props: IJobUserItemProps) => {
  return (
    <Wrapper>
      <div className="rank-num">
        {props.item?.rank}
        {/* {rankupdown !== 0 && (
          <div className="rank-icon">
            <StyledRankUpDownIcon rankupdown={rankupdown} /> {props.item.rankUpDown}
          </div>
        )} */}
      </div>
      <div className="center">
        <img src={props.item?.avatarUrl} className="user-photo" />
        <div className="user-nickname">
          {props.item?.nickname}
          {props.curRank == 1 && <img src={props.item?.tierUrl} className="user-tier" />}
        </div>
      </div>
      <div className="user-score">{props.item?.score}</div>
    </Wrapper>
  );
};

export default JobUserItem;
