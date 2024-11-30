import React, { useEffect, useRef, useState } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import useEditor from '../../hooks/useEditor';
import useCustomMove from '../../hooks/useCustomMove';
import useCustomLogin from '../../hooks/useCustomLogin';
import Modal from '../common/Modal';
import { clear } from '../../slices/editPostSlice';
import { useDispatch } from 'react-redux';

const initModalData = {
  title: '',
  content: '',
  onClose: () => {},
  onClick: () => {},
  buttonCloseText: '',
  buttonText: '',
};

function EditPostComponent() {
  const titleRef = useRef(null);
  const dispatch = useDispatch();

  const [isOpen, setIsOpen] = useState(false);
  const [modalData, setModalData] = useState(initModalData);

  const {
    content,
    handleChangeContent,
    modules,
    formats,
    handleUpload,
    editorRef,
    title,
    handleChangeTitle,
  } = useEditor();
  const { moveToList, page, size, sort, category, id, moveToDetail } =
    useCustomMove();
  const { loginState } = useCustomLogin();

  useEffect(() => {
    if (category === 'notice' && loginState.userRole === 'USER') {
      setIsOpen(true);
      setModalData({
        title: '⚠️',
        content: `공지사항 작성 권한이 없습니다.`,
        onClose: () => {
          setIsOpen(false);
          moveToList();
        },
      });
    }
  }, []);

  const button = (name, color, fn) => (
    <button
      onClick={fn}
      className={`${color} text-white text-lg px-5 py-2 rounded-lg mx-3`}
    >
      {name}
    </button>
  );

  return (
    <div className="flex-1 flex flex-col justify-between bg-gray-800 text-gray-100 rounded-lg shadow-lg p-8 h-full min-h-[70vh] pt-12">
      <div>
        <div className="mb-4 text-gray-400 text-center">
          <label>제목: </label>
          <input
            value={title}
            onChange={(value) => handleChangeTitle(value)}
            ref={titleRef}
            type="text"
            className="w-4/5 ml-2 flex-1 p-2 rounded bg-gray-600 text-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>
        <div className="mb-4 bg-gray-500">
          <ReactQuill
            ref={editorRef}
            value={content}
            onChange={(value) => handleChangeContent(value)}
            modules={modules}
            formats={formats}
          />
        </div>
      </div>
      <div className="flex justify-end">
        {button('취소', 'bg-pink-900', () => {
          if (id === null) {
            moveToList({ page, size, sort });
            return;
          }
          dispatch(clear());
          moveToDetail(id);
        })}
        {button('글 쓰기', 'bg-indigo-900', () => {
          handleUpload();
        })}
      </div>
      <Modal
        isOpen={isOpen}
        title={modalData.title}
        content={modalData.content}
        buttonCloseText={modalData.buttonCloseText}
        onClose={modalData.onClose}
      />
    </div>
  );
}

export default EditPostComponent;
