import React from 'react';
import { lazy, Suspense } from 'react';
import { createBrowserRouter } from 'react-router-dom';
import memberRouter from './memberRouter';
import boardRouter from './boardRouter';
import gameStatRouter from './gameStatRouter';

const Loading = <div>Loading...</div>;
const GameMain = lazy(() => import('../pages/GameMainPage'));

const root = createBrowserRouter([
  {
    path: '',
    element: (
      <Suspense fallback={Loading}>
        <GameMain />
      </Suspense>
    ),
  },
  {
    path: 'member',
    children: memberRouter(),
  },
  {
    path: 'board',
    children: boardRouter(),
  },
  {
    path: 'game-stats',
    children: gameStatRouter(),
  },
]);

export default root;
