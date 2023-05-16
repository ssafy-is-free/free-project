import styled, { css } from 'styled-components';
import { IJobUserItemProps } from '../rank/IRank';
import RankUpDownIcon from '../../public/Icon/RankUpDownIcon.svg';

const NoUserWrapper = styled.div`
  background-color: ${(props) => props.theme.bgWhite};
  border-radius: 8px;
  height: 56px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0px 14px;
  color: ${(props) => props.theme.fontBlack};
  font-weight: bold;
  font-size: 14px;
  width: calc(100% - 64px);
  position: absolute;
  top: -28px;
  left: 32px;
  box-shadow: 4px 4px 20px #00000026;
`;

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
  top: -28px;
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
      margin-right: 8px;
    }
    .user-nickname {
      width: 70%;
      display: flex;
      align-items: center;

      .name {
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
      }

      .tier {
        .user-tier {
          width: 24px;
          height: 24px;
          /* border-radius: 50%; */
          margin-left: 8px;
        }
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

const StyledRankUpDownIcon = styled(RankUpDownIcon)<{ rankupdown: number }>`
  ${(props) => {
    if (props.rankupdown > 0) {
      return css`
        path {
          fill: #1ae665;
        }
      `;
    } else {
      return css`
        transform: scaleY(-1);
        path {
          fill: #ff9650;
        }
      `;
    }
  }};

  margin-right: 4px;
  margin-top: 2px;
`;

const JobUserItem = (props: IJobUserItemProps) => {
  return (
    <Wrapper>
      <div className="rank-num">{props.item?.rank}</div>
      <div className="center">
        <img src={props.item?.avatarUrl} className="user-photo" />
        <div className="user-nickname">
          <div className="name">{props.item?.nickname}</div>
          {props.curRank == 1 && (
            <div className="tier">
              <img src={props.item?.tierUrl} className="user-tier" />
            </div>
          )}
        </div>
      </div>
      <div className="user-score">{props.item?.score}</div>
    </Wrapper>
  );
};

export default JobUserItem;