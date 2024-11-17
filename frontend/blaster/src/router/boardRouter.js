import React, { lazy, Suspense } from 'react';

const Loading = <div>Loading...</div>;
const Board = lazy(() => import('../pages/board/BoardPage'));
const Post = lazy(() => import('../pages/board/PostDetailPage'));
const CreatePost = lazy(() => import('../pages/board/CreatePostPage'));
const Notice = lazy(() => import('../pages/board/NoticeBoardPage'));
const General = lazy(() => import('../pages/board/GeneralBoardPage'));

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
    // {
    //   path: 'notice',
    //   element: (
    //     <Suspense fallback={Loading}>
    //       <Notice />
    //     </Suspense>
    //   ),
    // },
    // {
    //   path: 'general',
    //   element: (
    //     <Suspense fallback={Loading}>
    //       <General />
    //     </Suspense>
    //   ),
    // },
    // {
    //   path: 'notice/:id',
    //   element: (
    //     <Suspense fallback={Loading}>
    //       <Post />
    //     </Suspense>
    //   ),
    // },
    // {
    //   path: 'general/:id',
    //   element: (
    //     <Suspense fallback={Loading}>
    //       <Post />
    //     </Suspense>
    //   ),
    // },
  ];
}

export default boardRouter;
