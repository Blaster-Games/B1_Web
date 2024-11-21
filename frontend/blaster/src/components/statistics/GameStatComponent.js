import React from 'react';
import ChartComponent from './ChartComponent';

function GameStatComponent() {
  return (
    <div className="flex-1 flex flex-col bg-gray-800 text-gray-100 rounded-lg shadow-lg p-8 h-full min-h-[70vh]">
      <h1 className="text-3xl font-bold">게임 통계 컴포넌트</h1>
      <ChartComponent />
    </div>
  );
}

export default GameStatComponent;