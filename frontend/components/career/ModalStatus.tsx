import { useState, useEffect } from 'react';
import styled from 'styled-components';
import { getJobStatus } from '@/pages/api/careerAxios';
import { ICareerStatus, IStatusModalProps } from './ICareer';
import { DarkBg } from './SCareer';

const StatuModalDiv = styled.div`
  .modalTitle {
    margin: 1rem;
    border-bottom: 2px solid ${(props) => props.theme.primary};
    text-align: center;
    font-size: large;
  }
  .statuscontent {
    width: 100vw;
    bottom: 0;
    left: 0;
    position: fixed;
    z-index: 10;
    background-color: white;
    .statusList {
      height: 20rem;
      overflow: auto;
      margin: 1rem;
      .statusitem {
        text-align: center;
        padding: 1rem;
      }
    }
  }
`;

const StatusModal = ({ close, result }: IStatusModalProps) => {
  const [statusData, setStatusData] = useState<ICareerStatus[] | null>(null);
  const getStatus = async () => {
    const res = await getJobStatus();
    setStatusData(res.data);
  };

  useEffect(() => {
    getStatus();
  }, []);

  return (
    <StatuModalDiv>
      <DarkBg onClick={close}></DarkBg>
      <div className="statuscontent">
        <div className="modalTitle">현재 상태 변경하기</div>
        {statusData ? (
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
        ) : (
          <div className="statusList"></div>
        )}
      </div>
    </StatuModalDiv>
  );
};

export default StatusModal;
