import React, { lazy, Suspense } from 'react';

const Loading = <div>Loading...</div>;
const MapStats = lazy(() => import('../pages/statistics/GameMapStatPage'));
const VisitorStats = lazy(() => import('../pages/statistics/GameVisitorStatPage'));
const MyGameStats = lazy(() => import('../pages/statistics/MyGameStatPage'));

function gameStatRouter() {
  return [
    {
      path: 'map',
      element: (
        <Suspense fallback={Loading}>
          <MapStats />
        </Suspense>
      ),
    },
    {
      path: 'visitor',
      element: (
        <Suspense fallback={Loading}>
          <VisitorStats />
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
