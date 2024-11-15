import React from 'react';
import { NavLink } from 'react-router-dom';
import useCustomLogin from '../../hooks/useCustomLogin';

function SideMenu() {
  const { loginState, isLogin, doLogout } = useCustomLogin();

  const logoutHandler = () => {
    doLogout();
  };

  return (
    <aside className="w-64 bg-gray-700 text-gray-100 p-5 rounded-lg shadow-lg">
      <div className="mb-6">
        <h1 className="text-lg font-bold">우리 게임</h1>
      </div>
      <nav>
        <ul>
          {isLogin ? (
            <>
              <h2 className="text-blue-200 font-bold mb-4">
                {loginState.nickname}님 환영합니다!
              </h2>
              <li className="mb-4">
                <NavLink
                  to="/member/profile"
                  className={({ isActive }) =>
                    isActive
                      ? 'text-yellow-500 font-bold'
                      : 'hover:text-blue-400 transition duration-300'
                  }
                >
                  마이페이지
                </NavLink>
              </li>
              <li className="mb-4">
                <NavLink
                  to="/"
                  className="hover:text-blue-400 transition duration-300"
                  onClick={logoutHandler}
                >
                  로그아웃
                </NavLink>
              </li>
            </>
          ) : (
            <>
              <li className="mb-4">
                <NavLink
                  to="/member/login"
                  className={({ isActive }) =>
                    isActive
                      ? 'text-yellow-500 font-bold'
                      : 'hover:text-blue-400 transition duration-300'
                  }
                >
                  로그인
                </NavLink>
              </li>
              <li className="mb-4">
                <NavLink
                  to="/member/signup"
                  className={({ isActive }) =>
                    isActive
                      ? 'text-yellow-500 font-bold'
                      : 'hover:text-blue-400 transition duration-300'
                  }
                >
                  회원가입
                </NavLink>
              </li>
            </>
          )}
          <hr className="border-gray-500 mb-4" />
          <h2 className="text-blue-200 font-bold mb-4">통계</h2>
          <li className="mb-4">
            <NavLink
              to="/weapon-stats"
              className={({ isActive }) =>
                isActive
                  ? 'text-yellow-500 font-bold'
                  : 'hover:text-blue-400 transition duration-300'
              }
            >
              맵 별 무기 통계
            </NavLink>
          </li>
          <hr className="border-gray-500 mb-4" />
          <h2 className="text-blue-200 font-bold mb-4">커뮤니티</h2>
          <li className="mb-4">
            <NavLink
              to="/board/notice"
              className={({ isActive }) =>
                isActive
                  ? 'text-yellow-500 font-bold'
                  : 'hover:text-blue-400 transition duration-300'
              }
            >
              공지 게시판
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/board/general"
              className={({ isActive }) =>
                isActive
                  ? 'text-yellow-500 font-bold'
                  : 'hover:text-blue-400 transition duration-300'
              }
            >
              자유 게시판
            </NavLink>
          </li>
        </ul>
      </nav>
    </aside>
  );
}

export default SideMenu;