import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { IMainOtherItemProps } from './IRank';

const Wrapper = styled.div`
  background-color: ${(props) => props.theme.bgWhite};
  border: 1px solid ${(props) => props.theme.secondary};
  border-radius: 8px;
  height: 56px;
  display: flex;
  align-items: center;
  justify-content: space-around;
  padding: 0px 16px;
  color: ${(props) => props.theme.fontBlack};
  font-weight: bold;
  font-size: 14px;

  .rank-num {
    width: 15%;
    text-align: left;
  }
  .user-photo {
    width: 32px;
    height: 32px;
    border-radius: 50%;
  }
  .user-nickname {
    width: 50%;
    display: flex;
    align-items: center;

    .user-tier {
      width: 24px;
      height: 24px;
      /* border-radius: 50%; */
      margin-left: 8px;
    }
  }

  .user-score {
    text-align: right;
  }
`;

const MainOtherItem = (props: IMainOtherItemProps) => {
  return (
    <Wrapper>
      <div className="rank-num">{props.item?.rank}</div>
      <img src={props.item?.avatarUrl} className="user-photo" />
      <div className="user-nickname">
        {props.item?.nickname} {props.curRank == 1 && <img src={props.item?.tierUrl} className="user-tier" />}
      </div>
      <div className="user-score">{props.item?.score}</div>
    </Wrapper>
  );
};

export default MainOtherItem;
