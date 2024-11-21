import React, { lazy, Suspense } from 'react';

const Loading = <div>Loading...</div>;
const GameStats = lazy(() => import('../pages/statistics/GameStatPage'));

function gameStatRouter() {
  return [
    {
      path: '',
      element: (
        <Suspense fallback={Loading}>
          <GameStats />
        </Suspense>
      ),
    },
  ];
}

export default gameStatRouter;
