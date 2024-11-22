import React from 'react';
import ChartComponent from './ChartComponent';

function GameStatComponent() {
  return (
    <div className="flex-1 flex flex-col bg-gray-700 text-gray-100 rounded-lg shadow-lg p-8 h-full min-h-[70vh]">
      <ChartComponent />
    </div>
  );
}

export default GameStatComponent;