import React, { lazy, Suspense } from 'react';

const Loading = <div>Loading...</div>;
const GameStats = lazy(() => import('../pages/statistics/GameStatPage'));
const MyGameStats = lazy(() => import('../pages/statistics/MyGameStatPage'));

function gameStatRouter() {
  return [
    {
      path: 'game',
      element: (
        <Suspense fallback={Loading}>
          <GameStats />
        </Suspense>
      ),
    },
    {
      path: 'my',
      element: (
        <Suspense fallback={Loading}>
          <MyGameStats />
        </Suspense>
      ),
    },
  ];
}

export default gameStatRouter;
