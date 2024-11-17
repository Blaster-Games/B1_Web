import React, { useEffect, useState } from 'react';
import { postGet } from '../../api/boardApi';
import useCustomLogin from '../../hooks/useCustomLogin';

const initialState = {
  category: '',
  commentCount: 0,
  comments: [],
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

  useEffect(() => {
    postGet(id).then((res) => {
      console.log(res);
      setPostInfo(res);
    });
  }, []);

  return (
    <div className="flex-1 flex flex-col bg-gray-800 text-gray-100 rounded-lg shadow-lg p-8 min-h-[70vh] pt-12">
      {/* 게시글 제목 */}
      <h2 className="text-2xl font-bold mb-4">{postInfo.title}</h2>

      {/* 작성자 및 작성 시간 */}
      <div className="flex justify-end items-center text-sm text-gray-400 mb-6">
        <span>
          {postInfo.memberName} - {postInfo.createdAt}
        </span>
      </div>

      {/* 게시글 내용 */}
      <div className="mb-8 p-4 rounded-lg">
        <p>{postInfo.content}</p>
      </div>

      {/* 추천 및 비추천 */}
      <div className="flex items-center gap-4 mb-6">
        <button className="flex items-center gap-1 text-sm text-gray-400">
          <span role="img" aria-label="like">
            👍
          </span>{' '}
          {postInfo.likeCount}
        </button>
        <button className="flex items-center gap-1 text-sm text-gray-400">
          <span role="img" aria-label="dislike">
            👎
          </span>{' '}
          {postInfo.dislikeCount}
        </button>
      </div>
      {/* 전체 댓글 개수 */}
      <div className="flex items-center justify-between">
        <div className="text-sm text-gray-400 mb-6">
          전체 댓글 개수: {postInfo.commentCount}개
        </div>
        {loginState.id === postInfo.memberId ? (
          <div>
            <button className="bg-indigo-900 text-white text-xs px-2 py-1 mr-1 rounded-lg">
              수정
            </button>
            <button className="bg-pink-900 text-white text-xs px-2 py-1 ml-1 rounded-lg">
              삭제
            </button>
          </div>
        ) : (
          <></>
        )}
      </div>
      <hr />

      {/* 댓글 등록 공간 */}
      <div className=" p-4 rounded-lg">
        <h4 className="font-semibold mb-3">댓글 등록</h4>
        <div className="flex justify-between items-end mb-6">
          <textarea
            className="w-11/12 p-2 rounded-lg bg-gray-700   text-gray-100 mb-3"
            rows="3"
            placeholder="댓글을 입력하세요..."
          ></textarea>
          <button className="bg-blue-600 text-white px-4 py-2 rounded-lg mb-4">
            등록
          </button>
        </div>

        {/* 댓글 목록 */}
        {postInfo.comments.map((comment) => (
          <div key={comment.id} className="mb-4">
            <div className="text-sm text-gray-400">
              {comment.memberName} - {comment.createdAt}
            </div>
            <div>{comment.content}</div>
            <div className="flex items-center gap-4 mb-6">
              <button className="flex items-center gap-1 text-sm text-gray-400">
                <span role="img" aria-label="like">
                  👍
                </span>{' '}
                {postInfo.likeCount}
              </button>
              <button className="flex items-center gap-1 text-sm text-gray-400">
                <span role="img" aria-label="dislike">
                  👎
                </span>{' '}
                {postInfo.dislikeCount}
              </button>
              {loginState.id === comment.memberId ? (
                <div>
                  <button className="bg-indigo-900 text-white text-xs px-2 py-1 mr-1 rounded-lg">
                    수정
                  </button>
                  <button className="bg-pink-900 text-white text-xs px-2 py-1 ml-1 rounded-lg">
                    삭제
                  </button>
                </div>
              ) : (
                <></>
              )}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default PostDetailComponent;
