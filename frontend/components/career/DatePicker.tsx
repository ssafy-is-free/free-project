import { useState } from 'react';
import WheelPicker from 'react-simple-wheel-picker';
import styled from 'styled-components';

const yearList = new Array();
for (let i = 2020; i < 2030; i++) {
  yearList.push({ id: i.toString(), value: i.toString() });
}

const monthList = new Array();
for (let i = 1; i < 13; i++) {
  monthList.push({ id: i.toString(), value: i.toString() });
}

const dayList = new Array();
for (let i = 1; i < 32; i++) {
  dayList.push({ id: i.toString(), value: i.toString() });
}

const DatePickerdiv = styled.div`
  .title {
    margin: 1rem;
  }
  .wheel {
    display: flex;
    justify-content: center;
    margin-bottom: 1rem;
  }
  ul {
    ::-webkit-scrollbar {
      display: none;
    }
  }
`;

interface IDatePickerProps {
  updateDate: (date: string) => void;
}

const DatePicker = ({ updateDate }: IDatePickerProps) => {
  const [year, setYear] = useState<string>('');
  const [month, setMonth] = useState<string>('');
  const [day, setDay] = useState<string>('');

  const handleYear = (target: any) => {
    setYear(target.value);
    updateDate(`${target.value}-${month}-${day}`);
  };
  const handleMonth = (target: any) => {
    setMonth(target.value);
    updateDate(`${year}-${target.value}-${day}`);
  };
  const handleDay = (target: any) => {
    setDay(target.value);
    updateDate(`${year}-${month}-${target.value}`);
  };

  return (
    <DatePickerdiv>
      <div className="title">날짜입력 </div>
      <div className="wheel">
        <WheelPicker
          data={yearList}
          onChange={handleYear}
          height={150}
          width={100}
          itemHeight={30}
          selectedID={yearList[3].id}
          color="#ccc"
          activeColor="#333"
          backgroundColor="#fff"
          shadowColor="#fff"
        />
        <WheelPicker
          data={monthList}
          onChange={handleMonth}
          height={150}
          width={100}
          itemHeight={30}
          selectedID={monthList[0].id}
          color="#ccc"
          activeColor="#333"
          backgroundColor="#fff"
          shadowColor="#fff"
        />
        <WheelPicker
          data={dayList}
          onChange={handleDay}
          height={150}
          width={100}
          itemHeight={30}
          selectedID={dayList[0].id}
          color="#ccc"
          activeColor="#333"
          backgroundColor="#fff"
          shadowColor="#fff"
        />
      </div>
    </DatePickerdiv>
  );
};
export default DatePicker;
