import styled from 'styled-components';
import { INoAccountProps } from './IRank';

const Wrapper = styled.div`
  background-color: ${(props) => props.theme.secondary};
  border-radius: 8px;
  height: 56px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0px 16px;
  color: ${(props) => props.theme.fontBlack};
  font-size: 14px;
`;

const NoAccount = (props: INoAccountProps) => {
  return (
    <Wrapper onClick={props.onClick}>
      {props.curRank == 0 ? <>로그인을 해서 랭킹을 확인해보세요</> : <>백준 계정 등록 후 랭킹을 확인해보세요</>}
    </Wrapper>
  );
};

export default NoAccount;
