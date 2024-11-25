import React from 'react';
import BasicLayout from '../../layouts/BasicLayout';
import { visitorStatsGet } from '../../api/statsApi';
import GameBarStatComponent from '../../components/statistics/GameBarStatComponent';

function GameVisitorStatPage() {
  return (
    <BasicLayout>
      <GameBarStatComponent
        title={'일별 게임 접속자 수'}
        fn={visitorStatsGet}
      />
    </BasicLayout>
  );
}

export default GameVisitorStatPage;
