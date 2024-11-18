import React, { lazy, Suspense } from 'react';

const Loading = <div>Loading...</div>;
const Board = lazy(() => import('../pages/board/BoardPage'));
const Post = lazy(() => import('../pages/board/PostDetailPage'));
const CreatePost = lazy(() => import('../pages/board/CreatePostPage'));

function boardRouter() {
  return [
    {
      path: ':category',
      element: (
        <Suspense fallback={Loading}>
          <Board />
        </Suspense>
      ),
    },
    {
      path: ':category/:id',
      element: (
        <Suspense fallback={Loading}>
          <Post />
        </Suspense>
      ),
    },
    {
      path: ':category/create',
      element: (
        <Suspense fallback={Loading}>
          <CreatePost />
        </Suspense>
      ),
    },
  ];
}

export default boardRouter;
