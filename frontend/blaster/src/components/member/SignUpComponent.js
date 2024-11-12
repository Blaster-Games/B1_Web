import React, { useRef, useState } from 'react';
import {
  emailCheckGet,
  nicknameCheckGet,
  signUpPost,
} from '../../api/memberApi';
import useCustomLogin from '../../hooks/useCustomLogin';

function SignUpComponent() {
  const nicknameRef = useRef(null);
  const emailRef = useRef(null);
  const password1Ref = useRef(null);
  const password2Ref = useRef(null);

  const { moveToLogin } = useCustomLogin();

  const [isNickNameChecked, setIsNickNameChecked] = useState(false);
  const [isEmailChecked, setIsEmailChecked] = useState(false);

  const nicknameCheckHandler = () => {
    nicknameCheckGet(nicknameRef.current.value)
      .then((res) => {
        if (res) {
          alert('이미 사용 중인 닉네임입니다.');
          nicknameRef.current.value = '';
        } else {
          alert('사용 가능한 닉네임입니다.');
          setIsNickNameChecked(true);
        }
      })
      .catch();
  };

  const emailCheckHandler = () => {
    emailCheckGet(emailRef.current.value)
      .then((res) => {
        if (res) {
          alert('이미 가입한 이메일입니다.');
          emailRef.current.value = '';
        } else {
          alert('사용 가능한 이메일입니다.');
          setIsEmailChecked(true);
        }
      })
      .catch();
  };

  const isEmpty = (ref, message) => {
    if (ref.current.value === '') {
      alert(message + ' 입력해 주세요.');
      ref.current.focus();
      return true;
    }
    return false;
  };

  const isValidated = () => {
    if (isEmpty(nicknameRef, '닉네임을')) return false;
    if (isEmpty(emailRef, '이메일을')) return false;
    if (isEmpty(password1Ref, '비밀번호를')) return false;
    if (isEmpty(password2Ref, '비밀번호 확인을')) return false;
    if (password1Ref.current.value !== password2Ref.current.value) {
      alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
      return false;
    }
    return true;
  };

  const signUpHandler = () => {
    if (!isValidated()) return;

    const signUpData = {
      nickname: nicknameRef.current.value,
      email: emailRef.current.value,
      password: password1Ref.current.value,
    };

    signUpPost(signUpData)
      .then((res) => {
        if (res.request.status === 200) {
          alert(`${res.data.nickname} 님 환영합니다!`);
          moveToLogin();
        }
      })
      .catch((e) => {
        console.error(e);
      });
  };

  return (
    <div className="flex-1 flex bg-gray-800 text-gray-100 rounded-lg shadow-lg p-16 h-full max-h-screen justify-end min-h-[70vh] pt-24">
      <h2 className="basis-1/8 text-3xl font-bold text-center mb-8">
        회원 가입
      </h2>
      <div className="basis-7/8 flex flex-col space-y-4 w-full max-w-lg mx-auto">
        <div className="flex items-center mb-1">
          <label htmlFor="username" className="basis-1/4 w-24 mr-4">
            닉네임:
          </label>
          <input
            ref={nicknameRef}
            onChange={() => {
              setIsNickNameChecked(false);
            }}
            type="text"
            id="username"
            className="flex-1 p-2 rounded bg-gray-600 text-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
          {isNickNameChecked ? (
            <div className="basis-1/5 ml-4 bg-green-500 text-center text-white px-3 py-2 rounded transition duration-300">
              확인 완료
            </div>
          ) : (
            <button
              onClick={nicknameCheckHandler}
              className="basis-1/5 ml-4 bg-blue-500 hover:bg-blue-400 text-white px-3 py-2 rounded transition duration-300"
            >
              중복 확인
            </button>
          )}
        </div>
        <div className="flex items-center mb-4">
          <label htmlFor="email" className="basis-1/4 w-24 mr-4">
            이메일:
          </label>
          <input
            ref={emailRef}
            onChange={() => {
              setIsEmailChecked(false);
            }}
            type="email"
            id="email"
            className="flex-1 p-2 rounded bg-gray-600 text-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
          {isEmailChecked ? (
            <div className="basis-1/5 ml-4 bg-green-500 text-center text-white px-2 py-2 rounded transition duration-300">
              확인 완료
            </div>
          ) : (
            <button
              onClick={emailCheckHandler}
              className="basis-1/5 ml-4 bg-blue-500 hover:bg-blue-400 text-white px-2 py-2 rounded transition duration-300"
            >
              이메일 확인
            </button>
          )}
        </div>
        <div className="flex items-center mb-4">
          <label htmlFor="password" className="basis-1/4 w-24 mr-4">
            비밀번호:
          </label>
          <input
            ref={password1Ref}
            type="password"
            id="password"
            className="flex-1 p-2 rounded bg-gray-600 text-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>
        <div className="flex items-center mb-12">
          <label htmlFor="confirmPassword" className="basis-1/4 w-24 mr-4">
            비밀번호 확인:
          </label>
          <input
            ref={password2Ref}
            type="password"
            id="confirmPassword"
            className="flex-1 p-2 rounded bg-gray-600 text-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>
        <button
          onClick={signUpHandler}
          className="bg-blue-500 hover:bg-blue-400 text-white py-3 rounded-lg transition duration-300 w-full mb-6"
        >
          회원가입
        </button>
      </div>
    </div>
  );
}

export default SignUpComponent;
