import React from 'react';
import { lazy, Suspense } from 'react';
import { createBrowserRouter } from 'react-router-dom';
import memberRouter from './memberRouter';
import boardRouter from './boardRouter';

const Loading = <div>Loading...</div>;
const Main = lazy(() => import('../pages/MainPage'));

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
    path: 'member',
    children: memberRouter(),
  },
  {
    path: 'board',
    children: boardRouter(),
  },
]);

export default root;
