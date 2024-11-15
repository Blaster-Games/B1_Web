import React from 'react';

function PostListItem({
  title,
  content,
  author,
  createdAt,
  likes,
  commentCount,
  viewCount,
}) {
  return (
    <div className="space-y-4 my-2">
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
