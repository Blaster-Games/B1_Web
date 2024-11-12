import React from 'react';
import useCustomLogin from '../../hooks/useCustomLogin';

function ProfileComponenet() {
  const { loginState } = useCustomLogin();
  return (
    <div className="flex-1 flex flex-col bg-gray-800 text-gray-100 rounded-lg shadow-lg p-8 h-full max-h-screen min-h-[70vh] pt-12">
      <h2 className="text-center text-2xl font-bold mb-4">프로필</h2>
      <div className="mb-6 text-center">
        <p>가입일: {loginState.createdAt}</p>
        <p>이메일: {loginState.email}</p>
        <div className="flex items-center justify-center mt-2">
          <p>닉네임: {loginState.nickname}</p>
          <button className="ml-4 bg-blue-500 text-white px-3 py-1 rounded">
            변경
          </button>
        </div>
      </div>
      <div className="flex justify-center mb-6">
        <button className="bg-blue-500 text-white px-4 py-2 rounded mr-1">
          비밀번호 변경
        </button>
        <button className="bg-red-600 text-white px-4 py-2 rounded ml-1">
          회원 탈퇴
        </button>
      </div>
      <div className="flex justify-start mb-4">
        <button className="bg-blue-700 text-white px-3 py-1 mr-2 rounded">
          내가 쓴 글
        </button>
        <button className="bg-blue-700 text-white px-3 py-1 rounded">
          내가 쓴 댓글
        </button>
      </div>
      <div className="bg-gray-700 p-4 rounded mb-4">
        <h3 className="font-bold">
          [이벤트] 플레이 100판 인증하면 개발자가 치킨 쏜다.
        </h3>
        <p>이번주에 한 해 해당 이벤트가 진행...</p>
        <div className="flex items-center justify-between mt-4">
          <div className="flex items-center">
            <button className="flex items-center text-sm mr-4">
              <span className="mr-1">👍</span>12
            </button>
            <button className="flex items-center text-sm mr-4">
              <span className="mr-1">💬</span>17
            </button>
            <button className="flex items-center text-sm">
              <span className="mr-1">👁</span>1207
            </button>
          </div>
          <p className="text-sm">곽동렬 - 24.11.07</p>
        </div>
      </div>
    </div>
  );
}

export default ProfileComponenet;
