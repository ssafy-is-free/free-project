import { useEffect, useState } from 'react';
import styled, { css } from 'styled-components';
import { IMainOtherItemProps } from './IRank';
import RankUpDownIcon from '../../public/Icon/RankUpDownIcon.svg';
import { useSelector } from 'react-redux';
import { RootState } from '@/redux';
import Image from 'next/image';

const Wrapper = styled.div<{ rankupdown: number }>`
  background-color: ${(props) => props.theme.bgWhite};
  border: 1px solid ${(props) => props.theme.secondary};
  border-radius: 8px;
  height: 56px;
  display: flex;
  align-items: center;
  padding: 0px 14px;
  color: ${(props) => props.theme.fontBlack};
  font-weight: bold;
  font-size: 14px;
  width: 100%;

  .rank-num {
    width: 25%;
    height: 100%;
    display: flex;
    align-items: center;
    text-align: left;

    .rank-icon {
      width: 100%;
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
      font-weight: 100;
      font-size: 14px;

      ${(props) => {
        if (props.rankupdown > 0) {
          return css`
            color: #02d24e;
          `;
        } else {
          return css`
            color: #ff6f59;
          `;
        }
      }};
    }
  }

  .center {
    width: 55%;
    height: 100%;
    display: flex;
    align-items: center;

    .user-photo {
      /* width: 32px;
      height: 32px; */
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
          /* width: 24px;
          height: 24px; */
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
          fill: #02d24e;
        }
      `;
    } else {
      return css`
        transform: scaleY(-1);
        path {
          fill: #ff6f59;
        }
      `;
    }
  }};

  margin-right: 4px;
  margin-top: 2px;
`;

const MainOtherItem = (props: IMainOtherItemProps) => {
  // =1, 0, 1
  const [rankupdown, setRankupdown] = useState<number>(0);

  // 옵션
  const filter = useSelector<RootState>((selector) => selector.rankChecker.filter);

  useEffect(() => {
    if (props.item?.rankUpDown) {
      if (props.item?.rankUpDown < 0) {
        setRankupdown(-1);
      } else if (props.item?.rankUpDown > 0) {
        setRankupdown(1);
      } else {
        setRankupdown(0);
      }
    }
  }, [props.curRank, props.item]);

  return (
    <Wrapper rankupdown={rankupdown}>
      <div className="rank-num">
        {props.item?.rank}
        {props.isJob || filter
          ? null
          : props.item?.rankUpDown !== 0 && (
              <div className="rank-icon">
                <StyledRankUpDownIcon rankupdown={rankupdown} /> {props.item?.rankUpDown}
              </div>
            )}
      </div>
      <div className="center">
        <Image src={props.item?.avatarUrl} className="user-photo" alt="avatar" width={32} height={32}></Image>
        {/* <img src={props.item?.avatarUrl} className="user-photo" /> */}
        <div className="user-nickname">
          <div className="name">{props.item?.nickname} </div>
          {props.curRank == 1 && (
            <div className="tier">
              {props.item?.tierUrl && (
                <Image src={props.item.tierUrl} className="user-tier" alt="avatar" width={24} height={24}></Image>
              )}
              {/* <img src={props.item?.tierUrl} className="user-tier" /> */}
            </div>
          )}
        </div>
      </div>
      <div className="user-score">{props.item?.score}</div>
    </Wrapper>
  );
};

export default MainOtherItem;
