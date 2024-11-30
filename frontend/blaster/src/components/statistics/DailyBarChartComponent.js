import React from 'react';
import { Bar } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  BarElement,
  CategoryScale,
  LinearScale,
  Tooltip,
  Legend,
} from 'chart.js';

ChartJS.register(BarElement, CategoryScale, LinearScale, Tooltip, Legend);

function DailyBarChartComponent({ info }) {
  const labels = info.labels;
  const data = info.data;
  const label = info.label;
  const y = info.y;

  const chartData = {
    labels: labels,
    datasets: [
      {
        label: label,
        data: data,
        backgroundColor: 'rgba(75, 192, 192, 0.6)',
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1,
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
        labels: {
          color: '#ddddff',
        },
      },
      tooltip: {
        enabled: true,
      },
    },
    scales: {
      x: {
        title: {
          display: true,
          text: '날짜',
        },
        ticks: {
          color: '#ddddff', // x축 눈금 값 색상
        },
        grid: {
          color: '#ddddff', // x축 눈금선 색상
        },
      },
      y: {
        title: {
          display: true,
          text: y,
        },
        ticks: {
          color: '#ddddff', // x축 눈금 값 색상
        },
        grid: {
          color: '#ddddff', // x축 눈금선 색상
        },
        beginAtZero: true,
      },
    },
  };
  return <Bar data={chartData} options={options} />;
}

export default DailyBarChartComponent;
