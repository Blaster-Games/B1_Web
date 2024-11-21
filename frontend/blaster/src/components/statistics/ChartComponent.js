import React from 'react';
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

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
);

const b = [
  {
    label: 'Monthly Sales', // 데이터 라벨
    data: [65, 59, 80, 81, 56, 55], // 데이터 값
    borderColor: 'rgba(75, 192, 192, 1)', // 선 색상
    backgroundColor: 'rgba(75, 192, 192, 0.2)', // 배경 색상
  },
  {
    label: 'Monthly Expenses',
    data: [40, 48, 60, 70, 45, 50],
    borderColor: 'rgba(255, 99, 132, 1)',
    backgroundColor: 'rgba(255, 99, 132, 0.2)',
  },
  {
    label: 'abc',
    data: [75, 49, 90, 71, 66, 45],
    borderColor: 'rgba(85, 182, 182, 1)',
    backgroundColor: 'rgba(85, 182, 182, 0.2)',
  },
];

const data = {
  labels: ['January', 'February', 'March', 'April', 'May', 'June'],
  datasets: b,
};

const options = {
  responsive: true, // 반응형 지원
  plugins: {
    legend: {
      position: 'bottom', // 범례
    },
    title: {
      display: true,
      text: 'Sales and Expenses Over Time',
      font: {
        size: 20, // 제목 글자 크기
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

function ChartComponent() {
  return <Line data={data} options={options} />;
}

export default ChartComponent;
