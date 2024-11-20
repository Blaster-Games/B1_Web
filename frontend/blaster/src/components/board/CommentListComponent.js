import React, { useEffect, useRef, useState } from 'react';
import { commentListGet, commentPost } from '../../api/boardApi';
import { useParams } from 'react-router-dom';
import CommentComponent from './CommentComponent';

function CommentListComponent() {
  const [comments, setComments] = useState([]);
  const [refresh, setRefresh] = useState(false);

  const textRef = useRef(null);

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
        <CommentComponent
          key={comment.id}
          commentInfo={comment}
          refresh={refresh}
          setRefresh={setRefresh}
        />
      ))}
    </div>
  );
}

export default CommentListComponent;
