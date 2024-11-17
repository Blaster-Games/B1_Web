import React, { useEffect, useRef, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Modal from '../common/Modal';
import { passwordChangePut } from '../../api/memberApi';

const initModalData = {
  title: '',
  content: '',
  onClose: () => {},
  onClick: () => {},
  buttonCloseText: '',
  buttonText: '',
};

function ChangePasswordComponent() {
  const oldPasswordRef = useRef();
  const newPassword1Ref = useRef();
  const newPassword2Ref = useRef();

  const [isOpen, setIsOpen] = useState(false);
  const [modalData, setModalData] = useState(initModalData);

  const navigate = useNavigate();

  useEffect(() => {
    oldPasswordRef.current?.focus();
  }, []);

  const isEmpty = (ref, message) => {
    if (ref.current.value === '') {
      setIsOpen(true);
      setModalData({
        title: '⚠️',
        content: `${message} 입력해 주세요.`,
        onClose: () => {
          setIsOpen(false);
        },
      });
      ref.current.focus();
      return true;
    }
    return false;
  };

  const checkPasswordMatch = () => {
    if (newPassword1Ref.current?.value !== newPassword2Ref.current?.value) {
      setIsOpen(true);
      setModalData({
        title: '⚠️',
        content: `비밀번호와 비밀번호 확인이 일치하지 않습니다.`,
        onClose: () => {
          setIsOpen(false);
        },
      });
      newPassword2Ref.current?.focus();
    }
  };

  const handlePasswordChangeRequest = () => {
    if (isEmpty(oldPasswordRef, '기존 비밀번호를')) return;
    if (isEmpty(newPassword1Ref, '새 비밀번호를')) return;
    if (isEmpty(newPassword2Ref, '비밀번호 확인을')) return;
    checkPasswordMatch();

    const passwordParam = {
      oldPassword: oldPasswordRef.current?.value,
      newPassword: newPassword1Ref.current?.value,
    };

    passwordChangePut(passwordParam)
      .then(() => {
        setIsOpen(true);
        setModalData({
          title: '✔️비밀번호 변경 성공',
          content: '비밀번호가 변경되었습니다.',
          onClose: () => {
            setIsOpen(false);
            navigate('/member/profile');
          },
        });
      })
      .catch((err) => {
        if (err.status === 409) {
          setIsOpen(true);
          setModalData({
            title: '⚠️',
            content: '기존 비밀번호를 잘못 입력하셨습니다.',
            onClose: () => {
              setIsOpen(false);
            },
          });
          oldPasswordRef.current?.focus();
          return;
        }
        console.log(err);
      });
  };
  return (
    <div className="flex-1 flex flex-col bg-gray-800 text-gray-100 rounded-lg shadow-lg p-8 h-full max-h-screen min-h-[70vh] pt-12">
      <h2 className="text-center text-2xl font-bold mb-8">비밀번호 변경</h2>
      <div className="flex flex-col items-center space-y-6">
        <div className="flex items-center space-x-4 w-full max-w-md">
          <label className="w-32 text-right" htmlFor="current-password">
            기존 비밀번호:
          </label>
          <input
            ref={oldPasswordRef}
            id="current-password"
            type="password"
            className="flex-1 p-2 rounded bg-gray-700 text-gray-100 border border-gray-600 focus:outline-none focus:ring-2 focus:ring-blue-500 w-full"
          />
        </div>
        <div className="flex items-center space-x-4 w-full max-w-md">
          <label className="w-32 text-right" htmlFor="new-password">
            새 비밀번호:
          </label>
          <input
            ref={newPassword1Ref}
            id="new-password"
            type="password"
            className="flex-1 p-2 rounded bg-gray-700 text-gray-100 border border-gray-600 focus:outline-none focus:ring-2 focus:ring-blue-500 w-full"
          />
        </div>
        <div className="flex items-center space-x-4 w-full max-w-md">
          <label className="w-32 text-right" htmlFor="confirm-password">
            비밀번호 확인:
          </label>
          <input
            ref={newPassword2Ref}
            id="confirm-password"
            type="password"
            className="flex-1 p-2 rounded bg-gray-700 text-gray-100 border border-gray-600 focus:outline-none focus:ring-2 focus:ring-blue-500 w-full"
          />
        </div>
        <div className="flex space-x-4 mt-8 w-full max-w-md">
          <button
            onClick={handlePasswordChangeRequest}
            type="button"
            className="flex-1 bg-blue-500 text-white py-2 rounded hover:bg-blue-600 transition-colors duration-300"
          >
            확인
          </button>
          <Link
            to={'/member/profile'}
            className="flex-1 bg-blue-500 text-white text-center py-2 rounded hover:bg-blue-600 transition-colors duration-300"
          >
            취소
          </Link>
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

export default ChangePasswordComponent;
