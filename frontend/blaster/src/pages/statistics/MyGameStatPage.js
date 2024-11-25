import React from 'react';
import BasicLayout from '../../layouts/BasicLayout';
import GameBarStatComponent from '../../components/statistics/GameBarStatComponent';
import { myStatsGet } from '../../api/statsApi';

function MyGameStatPage() {
  return (
    <BasicLayout>
      <GameBarStatComponent title={'일별 게임 이용 시간'} fn={myStatsGet} />
    </BasicLayout>
  );
}

export default MyGameStatPage;
