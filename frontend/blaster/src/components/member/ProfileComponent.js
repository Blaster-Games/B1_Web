import React, { useEffect, useRef, useState } from 'react';
import useCustomLogin from '../../hooks/useCustomLogin';
import Modal from '../common/Modal';
import { nicknameChangePost } from '../../api/memberApi';
import { Link } from 'react-router-dom';
import { changeNickname } from '../../slices/loginSlice';
import { useDispatch } from 'react-redux';

const initModalData = {
  title: '',
  content: '',
  onClose: () => {},
  onClick: () => {},
  buttonCloseText: '',
  buttonText: '',
};

function ProfileComponent() {
  const { loginState } = useCustomLogin();

  const nicknameRef = useRef(null);

  const [isEditingNickname, setIsEditingNickname] = useState(false);
  const [newNickname, setNewNickname] = useState(loginState.nickname);
  const [isOpen, setIsOpen] = useState(false);
  const [modalData, setModalData] = useState(initModalData);
  const [focusTarget, setFocusTarget] = useState(null);

  const dispatch = useDispatch();

  useEffect(() => {
    if (focusTarget) {
      focusTarget.focus();
      setFocusTarget(null);
    }
  }, [focusTarget]);

  const closeModal = () => {
    setIsOpen(false);
  };

  const handleOnChangeNicknameInput = (e) => {
    setNewNickname(e.target.value);
  };

  const handleOnClickNicknameUpdateButton = () => {
    if (nicknameRef.current.value === '') {
      setIsOpen(true);
      setModalData({
        title: '닉네임 변경 실패',
        content: '새 닉네임을 입력해 주세요.',
        onClose: closeModal,
      });
      nicknameRef.current.focus();
      return;
    }
    if (nicknameRef.current.value === loginState.nickname) {
      setIsOpen(true);
      setModalData({
        title: '닉네임 변경 실패',
        content: '기존 닉네임과 다른 닉네임을 입력해 주세요.',
        onClose: closeModal,
      });
      nicknameRef.current.focus();
      return;
    }
    nicknameChangePost(nicknameRef.current.value)
      .then((res) => {
        dispatch(changeNickname({ nickname: res.message }));
        setIsOpen(true);
        setModalData({
          title: '닉네임 변경 성공',
          content: `닉네임이 ${res.message}로 변경되었습니다.`,
          onClose: closeModal,
        });
        setIsEditingNickname(false);
      })
      .catch((err) => {
        if (err.response && err.response.status === 409) {
          setIsOpen(true);
          setModalData({
            title: '닉네임 변경 실패',
            content:
              '이미 사용 중인 닉네임입니다. 다른 닉네임을 입력해 주세요.',
            onClose: closeModal,
          });
          setFocusTarget(nicknameRef.current);
        } else {
          console.error('다른 오류 발생:', err);
        }
      });
  };

  return (
    <div className="flex-1 flex flex-col bg-gray-800 text-gray-100 rounded-lg shadow-lg p-8 h-full max-h-screen min-h-[70vh] pt-12">
      <h2 className="text-center text-2xl font-bold mb-4">프로필</h2>
      <div className="mb-6 text-center">
        <p className={'my-3'}>가입일: {loginState.createdAt}</p>
        <p className={'my-3'}>이메일: {loginState.email}</p>
        <div className="flex items-center justify-center mt-2">
          {isEditingNickname ? (
            <>
              <label htmlFor="nickname" className="mr-3 text-gray-300">
                닉네임:
              </label>
              <input
                type="text"
                id="nickname"
                ref={nicknameRef}
                onChange={handleOnChangeNicknameInput}
                value={newNickname}
                className="bg-gray-900 text-gray-200 rounded-md px-2 py-1 border border-gray-600 w-36"
              />
              <button
                onClick={handleOnClickNicknameUpdateButton}
                className="ml-4 bg-blue-500 text-white px-3 py-1 rounded hover:bg-blue-600"
              >
                완료
              </button>
              <button
                onClick={() => {
                  setIsEditingNickname(false);
                }}
                className="ml-2 bg-blue-500 text-white px-3 py-1 rounded hover:bg-blue-500"
              >
                취소
              </button>
            </>
          ) : (
            <>
              <p>닉네임: {loginState.nickname}</p>
              <button
                onClick={() => setIsEditingNickname(true)}
                className="ml-4 bg-blue-500 text-white px-3 py-1 rounded"
              >
                변경
              </button>
            </>
          )}
        </div>
      </div>
      <div className="flex justify-center mb-6">
        <Link
          to={'/member/change-password'}
          className="bg-blue-500 text-white px-4 py-2 rounded mr-1"
        >
          비밀번호 변경
        </Link>
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
      <Modal
        isOpen={isOpen}
        title={modalData.title}
        content={modalData.content}
        buttonCloseText={modalData.buttonCloseText}
        buttonText={modalData.buttonText}
        onClose={modalData.onClose}
        onClick={modalData.onClick}
      />
    </div>
  );
}

export default ProfileComponent;
