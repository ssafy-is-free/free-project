import { useState } from 'react';
import styled from 'styled-components';
import { Spinner } from '../common/Spinner';

const DarkBg = styled.div`
  position: fixed;
  z-index: 5;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: ${(props) => props.theme.modalGray};
`;
const StatuModalDiv = styled.div`
  .statuscontent {
    width: 100vw;
    bottom: 0;
    left: 0;
    position: fixed;
    z-index: 10;
    background-color: white;
    .statusList {
      margin: 1rem;
      .statusitem {
        text-align: center;
        padding: 1rem;
      }
    }
  }
`;

export interface IStatus {
  id: number;
  name: string;
}

const ddata: IStatus[] = [
  {
    id: 1,
    name: '면접 탈락',
  },
  {
    id: 2,
    name: '면접 후 대기중',
  },
  {
    id: 3,
    name: '서류 탈락',
  },
  {
    id: 4,
    name: '서류 통과',
  },
  {
    id: 5,
    name: '최종 면접 탈락',
  },
  {
    id: 6,
    name: '스킵',
  },
];

interface IStatusModalProps {
  close: () => void;
  result: (status: IStatus) => void;
}

const StatusModal = ({ close, result }: IStatusModalProps) => {
  const [statusData, setStatusData] = useState<IStatus[] | null>(ddata);

  if (!statusData) {
    return (
      <StatuModalDiv>
        <DarkBg></DarkBg>
        <Spinner></Spinner>
      </StatuModalDiv>
    );
  } else {
    return (
      <StatuModalDiv>
        <DarkBg onClick={close}></DarkBg>
        <div className="statuscontent">
          <div className="statusList">
            {statusData.map((status) => (
              <div
                className="statusitem"
                key={status.id}
                onClick={() => {
                  result(status);
                  close();
                }}
              >
                {status.name}
              </div>
            ))}
          </div>
        </div>
      </StatuModalDiv>
    );
  }
};

export default StatusModal;
