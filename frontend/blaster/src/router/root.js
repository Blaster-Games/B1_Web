import React from 'react';
import { lazy, Suspense } from 'react';
import { createBrowserRouter } from 'react-router-dom';
import memberRouter from './memberRouter';
import boardRouter from './boardRouter';
import gameStatRouter from './gameStatRouter';

const Loading = <div>Loading...</div>;
const Main = lazy(() => import('../pages/MainPage'));
const GameMain = lazy(() => import('../pages/GameMainPage'));

const root = createBrowserRouter([
  {
    path: '',
    element: (
      <Suspense fallback={Loading}>
        <Main />
      </Suspense>
    ),
  },
  {
    path: ':game',
    element: (
      <Suspense fallback={Loading}>
        <GameMain />
      </Suspense>
    ),
  },
  {
    path: ':game/member',
    children: memberRouter(),
  },
  {
    path: ':game/board',
    children: boardRouter(),
  },
  {
    path: ':game/game-stats',
    children: gameStatRouter(),
  },
]);

export default root;
