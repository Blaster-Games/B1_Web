import React, { useEffect, useRef, useState } from 'react';
import { Line } from 'react-chartjs-2';
import { getStatsPost, mapListGet } from '../../api/statsApi';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { format, subDays } from 'date-fns';

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
);

const initialData = {
  labels: [],
  datasets: [],
};

const initialRequest = {
  mapName: 'testMap',
  startDate: getDay(7),
  endDate: getDay(0),
};

const options = (text) => {
  return {
    responsive: true, // 반응형 지원
    plugins: {
      legend: {
        position: 'bottom', // 범례
        labels: {
          boxHeight: 12, // 범례 박스 높이
          boxWidth: 20, // 범례 박스 너비
          padding: 20, // 범례 항목 간 간격
        },
      },
      title: {
        display: true,
        text: text,
        font: {
          size: 30, // 제목 글자 크기
        },
        color: '#ddddff',
      },
      tooltip: {
        backgroundColor: 'rgba(0,0,0,0.7)', // 툴팁 배경 색상
        titleColor: '#777777', // 툴팁 제목 색상
        bodyColor: '#777777', // 툴팁 본문 색상
      },
    },
    elements: {
      line: {
        tension: 0.2, // 선의 곡률 (0은 직선)
      },
      point: {
        radius: 5, // 데이터 점 크기
        hoverRadius: 7, // 마우스 오버 시 점 크기
      },
    },
  };
};

function getDay(n) {
  const today = new Date();
  today.setDate(today.getDate() - n);
  const yyyy = today.getFullYear(); // 연도
  const mm = String(today.getMonth() + 1).padStart(2, '0'); // 월 (0부터 시작하므로 +1 필요)
  const dd = String(today.getDate()).padStart(2, '0'); // 일

  return `${yyyy}-${mm}-${dd}`;
}

function ChartComponent() {
  const [mapList, setMapList] = useState([]);
  const [startDate, setStartDate] = useState(initialRequest.startDate);
  const [endDate, setEndDate] = useState(initialRequest.endDate);
  const [request, setRequest] = useState(initialRequest);
  const [chartData, setChartData] = useState(initialData);
  const [responseData, setResponseData] = useState(null);
  const [visibleDatasets, setVisibleDatasets] = useState({
    buff: true,
    throwable: true,
    weapon: true,
  });

  const mapRef = useRef(null);

  useEffect(() => {
    mapListGet()
      .then((res) => {
        setMapList(res);
      })
      .catch(console.error);
  }, []);

  useEffect(() => {
    setRequest({
      ...request,
      startDate,
      endDate,
    });
  }, [startDate, endDate]);

  useEffect(() => {
    getStatsPost(request).then((res) => {
      console.log(res);
      setResponseData(res);
    });
  }, [request]);

  useEffect(() => {
    if (responseData) {
      chartData.labels = responseData.labels;
      const buff = visibleDatasets.buff ? responseData.buff : [];
      const throwable = visibleDatasets.throwable ? responseData.throwable : [];
      const weapon = visibleDatasets.weapon ? responseData.weapon : [];
      chartData.datasets = [...buff, ...throwable, ...weapon];
      setChartData({ ...chartData });
    }
  }, [visibleDatasets, responseData]);

  const toggleDataset = (key) => {
    visibleDatasets[key] = !visibleDatasets[key];
    setVisibleDatasets({ ...visibleDatasets });
  };

  const handleChangeMap = () => {
    setRequest({
      ...request,
      mapName: mapRef.current.value,
    });
  };

  return (
    <div className="flex flex-col">
      <Line data={chartData} options={options('맵 별 통계')} />
      <div className="flex justify-between items-center mt-10">
        <div className="flex gap-4">
          <DatePicker
            className="bg-gray-800 rounded-lg text-center text-gray-300 py-1"
            selected={startDate} // 선택된 날짜
            onChange={(date) => setStartDate(format(date, 'yyyy-MM-dd'))} // 날짜 변경 시 업데이트
            // minDate={subDays(endDate, 30)}
            maxDate={endDate}
            dateFormat="yyyy-MM-dd" // 날짜 형식 지정
          />
          <DatePicker
            className="bg-gray-800 rounded-lg text-center text-gray-300 py-1"
            selected={endDate} // 선택된 날짜
            onChange={(date) => setEndDate(format(date, 'yyyy-MM-dd'))} // 날짜 변경 시 업데이트
            minDate={startDate}
            maxDate={new Date()}
            dateFormat="yyyy-MM-dd" // 날짜 형식 지정
          />
          <select
            onClick={handleChangeMap}
            ref={mapRef}
            className="bg-gray-800 text-gray-300 text-center px-12 py-1 rounded-lg hover:bg-blue-600 mr-6"
          >
            {mapList ? (
              mapList.map((map) => (
                <option key={map} value={map}>
                  {map}
                </option>
              ))
            ) : (
              <option>로딩 중</option>
            )}
          </select>
        </div>
        <div className="flex gap-4">
          <button
            className={`px-4 py-2 rounded-lg text-sm font-bold ${
              visibleDatasets.buff ? 'bg-blue-900' : 'bg-gray-600'
            } hover:opacity-80`}
            onClick={() => toggleDataset('buff')}
          >
            버프
          </button>
          <button
            className={`px-4 py-2 rounded-lg text-sm font-bold ${
              visibleDatasets.throwable ? 'bg-indigo-900' : 'bg-gray-600'
            } hover:opacity-80`}
            onClick={() => toggleDataset('throwable')}
          >
            투척
          </button>
          <button
            className={`px-4 py-2 rounded-lg text-sm font-bold ${
              visibleDatasets.weapon ? 'bg-pink-900' : 'bg-gray-600'
            } hover:opacity-80`}
            onClick={() => toggleDataset('weapon')}
          >
            무기
          </button>
        </div>
      </div>
    </div>
  );
}

export default ChartComponent;
