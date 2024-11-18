import React, { useEffect, useRef, useState } from 'react';
import { Link } from 'react-router-dom';
import useCustomLogin from '../../hooks/useCustomLogin';
import Modal from '../common/Modal';

function LoginComponent() {
  const emailRef = useRef(null);
  const passwordRef = useRef(null);

  const [isOpen, setIsOpen] = useState(false);

  useEffect(() => {
    emailRef.current.focus();
  }, [isOpen]);

  function closeModal() {
    setIsOpen(false);
  }

  const { doLogin, moveToFrom } = useCustomLogin();

  useEffect(() => {
    emailRef.current.focus();
  }, []);

  const loginHandler = () => {
    const userInfo = {
      username: emailRef.current.value,
      password: passwordRef.current.value,
    };

    doLogin(userInfo).then((data) => {
      if (data && data.error) {
        setIsOpen(true);
      } else {
        moveToFrom();
      }
    });
  };

  return (
    <div className="flex-1 flex bg-gray-800 text-gray-100 rounded-lg shadow-lg p-16 h-full max-h-screen justify-center min-h-[70vh] ml-4 pt-36">
      <h2 className="text-3xl font-bold text-center mb-8">로그인</h2>
      <div className="flex flex-col space-y-4 w-full max-w-md mx-auto">
        <div className="flex items-center mb-4">
          <label htmlFor="email" className="w-24 mr-4">
            이메일:
          </label>
          <input
            onKeyDown={(event) => {
              if (event.key === 'Enter') {
                loginHandler();
              }
            }}
            ref={emailRef}
            type="email"
            id="email"
            className="flex-1 p-2 rounded bg-gray-600 text-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>
        <div className="flex items-center mb-4">
          <label htmlFor="password" className="w-24 mr-4">
            비밀번호:
          </label>
          <input
            onKeyDown={(event) => {
              if (event.key === 'Enter') {
                loginHandler();
              }
            }}
            ref={passwordRef}
            type="password"
            id="password"
            className="flex-1 p-2 rounded bg-gray-600 text-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-400 mb-4"
          />
        </div>
        <button
          onClick={loginHandler}
          className="bg-blue-500 hover:bg-blue-400 text-white py-3 rounded-lg transition duration-300 mb-6 w-full"
        >
          로그인
        </button>
        <div className="flex justify-around text-blue-400 w-full">
          <Link to={'/'} className="hover:text-blue-300">
            비밀번호 재발급
          </Link>
          <Link to={'/member/signup'} className="hover:text-blue-300">
            회원가입
          </Link>
        </div>
      </div>
      <Modal
        isOpen={isOpen}
        title={'로그인 실패'}
        content={'이메일이나 비밀번호를 확인해 주세요'}
        onClose={closeModal}
      />
    </div>
  );
}

export default LoginComponent;
