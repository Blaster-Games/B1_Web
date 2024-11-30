import React, { useState } from 'react';
import useCustomLogin from '../../hooks/useCustomLogin';
import { commentDelete, commentPut } from '../../api/boardApi';

function CommentComponent({ commentInfo, refresh, setRefresh }) {
  const [isEditingComment, setIsEditingComment] = useState(false);
  const [comment, setComment] = useState(commentInfo);
  const [EditingComment, setEditingComment] = useState(comment.content);

  const { loginState } = useCustomLogin();

  const button = (text, color, fn) => (
    <button
      onClick={fn}
      className={`${color} text-white text-xs px-2 py-1 mr-1 rounded-lg`}
    >
      {text}
    </button>
  );

  const handleOnChange = (e) => {
    setEditingComment(e.target.value);
  };

  const handleCommentChange = () => {
    commentPut(comment.id, EditingComment)
      .then(
        setComment({
          ...comment,
          content: EditingComment,
        }),
      )
      .catch(console.error);
    setIsEditingComment(false);
  };

  const handleCommentDelete = (commentId) => {
    commentDelete(commentId)
      .then(() => {
        setIsEditingComment(false);
        setRefresh(!refresh);
      })
      .catch(console.error);
  };

  return (
    <div>
      {comment ? (
        <div className="mb-4">
          <div className="text-sm text-gray-400">
            {comment.memberName} - {comment.createdAt}
          </div>
          <div>
            {isEditingComment ? (
              <textarea
                onChange={handleOnChange}
                value={EditingComment}
                className="w-11/12 p-2 rounded-lg bg-gray-700   text-gray-100 mb-3"
                rows="3"
                placeholder="댓글을 입력하세요"
              ></textarea>
            ) : (
              comment.content
            )}
          </div>
          <div className="flex items-center gap-4 mb-6">
            {/*<button className="flex items-center gap-1 text-sm text-gray-400">*/}
            {/*  <span role="img" aria-label="like">*/}
            {/*    👍*/}
            {/*  </span>{' '}*/}
            {/*  {comment.likeCount}*/}
            {/*</button>*/}
            {/*<button className="flex items-center gap-1 text-sm text-gray-400">*/}
            {/*  <span role="img" aria-label="dislike">*/}
            {/*    👎*/}
            {/*  </span>{' '}*/}
            {/*  {comment.dislikeCount}*/}
            {/*</button>*/}
            {loginState.id === comment.memberId ? (
              isEditingComment ? (
                <div>
                  {button('확인', 'bg-indigo-900', handleCommentChange)}
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
      ) : (
        <></>
      )}
    </div>
  );
}

export default CommentComponent;
