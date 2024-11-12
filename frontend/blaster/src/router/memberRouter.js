import React from 'react';
import { lazy, Suspense } from 'react';

const Loading = <div>Loading...</div>;
const Login = lazy(() => import('../pages/member/LoginPage'));
const SignUp = lazy(() => import('../pages/member/SignUpPage'));
const Profile = lazy(() => import('../pages/member/ProfilePage'));

function memberRouter() {
  return [
    {
      path: 'login',
      element: (
        <Suspense fallback={Loading}>
          <Login />
        </Suspense>
      ),
    },
    {
      path: 'signup',
      element: (
        <Suspense fallback={Loading}>
          <SignUp />
        </Suspense>
      ),
    },
    {
      path: 'profile',
      element: (
        <Suspense fallback={Loading}>
          <Profile />
        </Suspense>
      ),
    },
  ];
}

export default memberRouter;
