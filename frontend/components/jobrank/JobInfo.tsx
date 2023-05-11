import styled from 'styled-components';
import { IJobInfoProps } from './IJobrank';
import TimeIcon from '../../public/Icon/TimeIcon.svg';

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  color: ${(props) => props.theme.fontWhite};
  margin-top: 8px;
  margin-bottom: 32px;

  .posting-name {
    margin-bottom: 8px;
    font-size: 14px;
  }

  .enterprise-name {
    font-weight: bold;
    font-size: 20px;
    margin-bottom: 8px;
  }

  .period {
    display: flex;
    align-items: center;
    font-size: 14px;

    p {
      margin-left: 8px;
    }
  }
`;

const JobInfo = (props: IJobInfoProps) => {
  return (
    <Wrapper>
      <div className="posting-name">{props.postingName} </div>
      <div className="enterprise-name">{props.companyName}</div>
      <div className="period">
        <TimeIcon />
        <p>
          {props.startTime} ~ {props.endTime}
        </p>
      </div>
    </Wrapper>
  );
};

export default JobInfo;
