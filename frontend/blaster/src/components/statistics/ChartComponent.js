import React, { useEffect, useRef, useState } from 'react';
import { Line } from 'react-chartjs-2';
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
import { getStatsPost, mapListGet } from '../../api/statsApi';
import { SORT } from '../../constants/boardConstants';

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
      },
      title: {
        display: true,
        text: text,
        font: {
          size: 30, // 제목 글자 크기
        },
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
        console.log(res);
        setMapList(res);
      })
      .catch(console.error);
  }, []);

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
    <div className="flex flex-col items-center">
      <Line data={chartData} options={options('맵 별 통계')} />
      <div className="flex gap-4 mt-10">
        <select
          onClick={handleChangeMap}
          ref={mapRef}
          className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 ml-4"
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
        <button
          className={`px-4 py-2 rounded-lg text-sm font-bold ${
            visibleDatasets.buff ? 'bg-blue-900' : 'bg-gray-600'
          } hover:opacity-80`}
          onClick={() => toggleDataset('buff')}
        >
          Buff
        </button>
        <button
          className={`px-4 py-2 rounded-lg text-sm font-bold ${
            visibleDatasets.throwable ? 'bg-indigo-900' : 'bg-gray-600'
          } hover:opacity-80`}
          onClick={() => toggleDataset('throwable')}
        >
          Throwable
        </button>
        <button
          className={`px-4 py-2 rounded-lg text-sm font-bold ${
            visibleDatasets.weapon ? 'bg-pink-900' : 'bg-gray-600'
          } hover:opacity-80`}
          onClick={() => toggleDataset('weapon')}
        >
          Weapon
        </button>
      </div>
    </div>
  );
}

export default ChartComponent;
