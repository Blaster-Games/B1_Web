import React from 'react';
import BasicLayout from '../../layouts/BasicLayout';
import GameMapStatComponent from '../../components/statistics/GameMapStatComponent';

function GameMapStatPage() {
  return (
    <BasicLayout>
      <GameMapStatComponent chartName={'맵 별 통계'} />
    </BasicLayout>
  );
}

export default GameMapStatPage;
