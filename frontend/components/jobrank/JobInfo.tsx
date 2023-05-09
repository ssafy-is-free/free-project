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
      <div className="posting-name">2023 상반기 신입/Junior 공개 채용 </div>
      <div className="enterprise-name">안랩</div>
      <div className="period">
        <TimeIcon />
        <p> 2023.03.31 14:00 ~ 2023.04.16 23:59</p>
      </div>
    </Wrapper>
  );
};

export default JobInfo;
