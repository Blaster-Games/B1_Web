import React, { lazy, Suspense } from 'react';

const Loading = <div>Loading...</div>;
const Notice = lazy(() => import('../pages/board/NoticeBoardPage'));
const General = lazy(() => import('../pages/board/GeneralBoardPage'));

function boardRouter() {
  return [
    {
      path: 'notice',
      element: (
        <Suspense fallback={Loading}>
          <Notice />
        </Suspense>
      ),
    },
    {
      path: 'general',
      element: (
        <Suspense fallback={Loading}>
          <General />
        </Suspense>
      ),
    },
  ];
}

export default boardRouter;
