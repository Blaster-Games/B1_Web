import React from 'react';
import BasicLayout from '../../layouts/BasicLayout';
import GameStatComponent from '../../components/statistics/GameStatComponent';

function GameStatPage() {
  return (
    <BasicLayout>
      <GameStatComponent chartName={'맵 별 통계'} />
    </BasicLayout>
  );
}

export default GameStatPage;
