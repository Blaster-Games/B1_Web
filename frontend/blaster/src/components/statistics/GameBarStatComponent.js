import React, { useEffect, useState } from 'react';
import DailyBarChartComponent from './DailyBarChartComponent';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { format, subDays } from 'date-fns';

const initialChartData = {
  labels: [
    format(subDays(new Date(), 6), 'yyyy-MM-dd'),
    format(subDays(new Date(), 5), 'yyyy-MM-dd'),
    format(subDays(new Date(), 4), 'yyyy-MM-dd'),
    format(subDays(new Date(), 3), 'yyyy-MM-dd'),
    format(subDays(new Date(), 2), 'yyyy-MM-dd'),
    format(subDays(new Date(), 1), 'yyyy-MM-dd'),
    format(subDays(new Date(), 0), 'yyyy-MM-dd'),
  ],
  data: [0, 0, 0, 0, 0, 0, 0],
};

const initialParams = {
  start: format(subDays(new Date(), 6), 'yyyy-MM-dd'),
  end: format(subDays(new Date(), 0), 'yyyy-MM-dd'),
};

function GameBarStatComponent({ title, fn }) {
  const [params, setParams] = useState(initialParams);
  const [startDate, setStartDate] = useState(subDays(new Date(), 6));
  const [endDate, setEndDate] = useState(new Date());
  const [chartData, setChartData] = useState(initialChartData);

  useEffect(() => {
    setParams({
      ...params,
      start: format(startDate, 'yyyy-MM-dd'),
      end: format(endDate, 'yyyy-MM-dd'),
    });
  }, [startDate, endDate]);

  useEffect(() => {
    fn(params)
      .then((res) => {
        setChartData(res);
      })
      .catch(console.error);
  }, [params]);

  return (
    <div className="flex-1 flex flex-col bg-gray-700 text-gray-100 rounded-lg shadow-lg p-8 h-full min-h-[70vh]">
      <h2 className="text-xl font-semibold mb-4 text-center">
        {title}
      </h2>
      <DailyBarChartComponent info={chartData} />
      <div className={'flex gap-5 mt-4'}>
        <DatePicker
          className="bg-gray-800 rounded-lg text-center text-gray-300 py-1"
          selected={startDate} // 선택된 날짜
          onChange={(date) => setStartDate(date)} // 날짜 변경 시 업데이트
          // minDate={subDays(endDate, 30)}
          maxDate={endDate}
          dateFormat="yyyy-MM-dd" // 날짜 형식 지정
        />
        <DatePicker
          className="bg-gray-800 rounded-lg text-center text-gray-300 py-1"
          selected={endDate} // 선택된 날짜
          onChange={(date) => setEndDate(date)} // 날짜 변경 시 업데이트
          minDate={startDate}
          maxDate={new Date()}
          dateFormat="yyyy-MM-dd" // 날짜 형식 지정
        />
      </div>
    </div>
  );
}

export default GameBarStatComponent;
