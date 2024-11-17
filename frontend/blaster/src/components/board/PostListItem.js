import React from 'react';
import useCustomMove from '../../hooks/useCustomMove';

function PostListItem({
  id,
  title,
  content,
  author,
  createdAt,
  likes,
  commentCount,
  viewCount,
}) {
  const { moveToDetail } = useCustomMove();
  return (
    <div onClick={() => moveToDetail(id)} className="space-y-4 my-2">
      <div className="bg-gray-700 p-4 rounded-lg">
        <h2 className="text-xl font-semibold">{title}</h2>
        <p className="mt-2">{content}</p>
        <div className="flex justify-between items-center mt-4">
          <span className="text-sm text-gray-400">
            {author} - {createdAt}
          </span>
          <div className="flex items-center space-x-4">
            <span className="text-sm text-gray-400">👍 {likes}</span>
            <span className="text-sm text-gray-400">💬 {commentCount}</span>
            <span className="text-sm text-gray-400">👁️ {viewCount}</span>
          </div>
        </div>
      </div>
    </div>
  );
}

export default PostListItem;
