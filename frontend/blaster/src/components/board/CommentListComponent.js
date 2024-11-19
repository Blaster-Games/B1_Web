import React, { useEffect, useRef, useState } from 'react';
import useCustomLogin from '../../hooks/useCustomLogin';
import { commentDelete, commentListGet, commentPost } from '../../api/boardApi';
import { useParams } from 'react-router-dom';

function CommentListComponent() {
  const [comments, setComments] = useState([]);
  const [isEditingComment, setIsEditingComment] = useState(false);
  const [refresh, setRefresh] = useState(false);

  const textRef = useRef(null);
  const changeCommentTextareaRefs = useRef([]);

  const { loginState } = useCustomLogin();
  const { id } = useParams();

  useEffect(() => {
    if (id) {
      commentListGet(id)
        .then((res) => {
          setComments(res.data);
        })
        .catch(console.error);
    }
  }, [id, refresh]);

  const handleCommentSubmit = () => {
    commentPost(textRef.current.value, id)
      .then(() => {
        textRef.current.value = '';
        setRefresh(!refresh);
      })
      .catch(console.error);
  };

  const handleCommentDelete = (commentId) => {
    commentDelete(commentId)
      .then(() => {
        setIsEditingComment(false);
        setRefresh(!refresh);
      })
      .catch(console.error);
  };

  const button = (text, color, fn) => (
    <button
      onClick={fn}
      className={`${color} text-white text-xs px-2 py-1 mr-1 rounded-lg`}
    >
      {text}
    </button>
  );

  return (
    /*댓글 등록*/
    <div className=" p-4 rounded-lg">
      <h4 className="font-semibold mb-3">댓글 ({comments.length}개)</h4>
      <div className="flex justify-between items-end mb-6">
        <textarea
          ref={textRef}
          className="w-11/12 p-2 rounded-lg bg-gray-700   text-gray-100 mb-3"
          rows="3"
          placeholder="여기에 댓글 입력"
        ></textarea>
        <button
          onClick={handleCommentSubmit}
          className="bg-blue-600 text-white px-4 py-2 rounded-lg mb-4"
        >
          등록
        </button>
      </div>

      {/* 댓글 목록 */}
      {comments.map((comment) => (
        <div key={comment.id} className="mb-4">
          <div className="text-sm text-gray-400">
            {comment.memberName} - {comment.createdAt}
          </div>
          <div>
            {isEditingComment ? (
              <textarea
                ref={(el) => {
                  changeCommentTextareaRefs.current[comment.id] = el;
                }}
                className="w-11/12 p-2 rounded-lg bg-gray-700   text-gray-100 mb-3"
                rows="3"
                placeholder="댓글을 입력하세요"
              ></textarea>
            ) : (
              comment.content
            )}
          </div>
          <div className="flex items-center gap-4 mb-6">
            <button className="flex items-center gap-1 text-sm text-gray-400">
              <span role="img" aria-label="like">
                👍
              </span>{' '}
              {comment.likeCount}
            </button>
            <button className="flex items-center gap-1 text-sm text-gray-400">
              <span role="img" aria-label="dislike">
                👎
              </span>{' '}
              {comment.dislikeCount}
            </button>
            {loginState.id === comment.memberId ? (
              isEditingComment ? (
                <div>
                  {button('확인', 'bg-indigo-900', () =>
                    setIsEditingComment(false),
                  )}
                  {button('취소', 'bg-pink-900', () =>
                    setIsEditingComment(false),
                  )}
                </div>
              ) : (
                <div>
                  {button('수정', 'bg-indigo-900', () =>
                    setIsEditingComment(true),
                  )}
                  {button('삭제', 'bg-pink-900', () =>
                    handleCommentDelete(comment.id),
                  )}
                </div>
              )
            ) : (
              <></>
            )}
          </div>
        </div>
      ))}
    </div>
  );
}

export default CommentListComponent;
