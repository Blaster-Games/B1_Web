import React, { useEffect, useState } from 'react';
import { getPostPatch, postDelete } from '../../api/boardApi';
import useCustomLogin from '../../hooks/useCustomLogin';
import useCustomMove from '../../hooks/useCustomMove';
import CommentListComponent from './CommentListComponent';
import PostReactionComponent from './PostReactionComponent';
import Modal from '../common/Modal';
import { useDispatch } from 'react-redux';
import { savePost } from '../../slices/editPostSlice';

const initialState = {
  category: '',
  commentCount: 0,
  content: '',
  createdAt: '',
  dislikeCount: 0,
  gameName: '',
  id: 0,
  likeCount: 0,
  memberName: '',
  title: '',
  updatedAt: '',
  viewCount: 0,
};

function PostDetailComponent({ id }) {
  const [postInfo, setPostInfo] = useState(initialState);
  const { loginState } = useCustomLogin();
  const { moveToList, moveToModify } = useCustomMove();
  const [isOpen, setIsOpen] = useState(false);
  const dispatch = useDispatch();

  const toList = (
    <button
      onClick={() => {
        moveToList();
      }}
      className="bg-gray-700 text-white text-xs px-2 py-1 mx-1 rounded-lg"
    >
      목록으로
    </button>
  );

  useEffect(() => {
    getPostPatch(id).then((res) => {
      setPostInfo(res);
    });
  }, []);

  function handleOnClickDelete() {
    setIsOpen(false);
    postDelete(id)
      .then((res) => {
        console.log(res);
        moveToList();
      })
      .catch(console.error);
  }

  function handleOnClickModify() {
    dispatch(
      savePost({
        title: postInfo.title,
        content: postInfo.content,
      }),
    );
    moveToModify();
  }

  return (
    <div className="flex-1 flex flex-col bg-gray-800 text-gray-100 rounded-lg shadow-lg p-8 min-h-[70vh] pt-12">
      {/* 게시글 제목 */}
      <h2 className="text-2xl font-bold mb-4">{postInfo.title}</h2>

      {/* 작성자 및 작성 시간 */}
      <div className="flex justify-between items-center text-sm text-gray-400 mb-6">
        <span>
          {postInfo.memberName} - {postInfo.createdAt}
        </span>
        {loginState.id === postInfo.memberId ? (
          <div>
            {toList}
            <button
              onClick={handleOnClickModify}
              className="bg-indigo-900 text-white text-xs px-2 py-1 mx-1 rounded-lg"
            >
              수정
            </button>
            <button
              onClick={() => setIsOpen(true)}
              className="bg-pink-900 text-white text-xs px-2 py-1 ml-1 rounded-lg"
            >
              삭제
            </button>
          </div>
        ) : (
          toList
        )}
      </div>

      {/* 게시글 내용 */}
      <div className="mb-8 p-4 rounded-lg">
        <div
          className="html-content"
          dangerouslySetInnerHTML={{ __html: postInfo.content }} // HTML 렌더링
        />
      </div>

      {/* 컴포넌트 나누는 기준:  같이 렌더링 되는 것들 끼리 묶기 or 렌더링 빈도 수 */}
      <PostReactionComponent
        likeCount={postInfo.likeCount}
        dislikeCount={postInfo.dislikeCount}
      />
      <hr />
      <CommentListComponent />
      <Modal
        isOpen={isOpen}
        title={'게시글 삭제'}
        content={'정말 게시글을 삭제하시겠습니까?'}
        onClose={() => setIsOpen(false)}
        buttonCloseText={'취소'}
        onClick={handleOnClickDelete}
        buttonText={'확인'}
      />
    </div>
  );
}

export default PostDetailComponent;
