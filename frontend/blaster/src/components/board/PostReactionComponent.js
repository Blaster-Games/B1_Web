import React, { useState } from 'react';

function PostReactionComponent({likeCount, dislikeCount}) {
  const [likes, setLikes] = useState(likeCount);
  const [dislikes, setDislikes] = useState(dislikeCount);
  return (
    <div className="flex items-center gap-4 mb-6">
      <button className="flex items-center gap-1 text-sm text-gray-400">
        <span role="img" aria-label="like">
          👍
        </span>{' '}
        {likes}
      </button>
      <button className="flex items-center gap-1 text-sm text-gray-400">
        <span role="img" aria-label="dislike">
          👎
        </span>{' '}
        {dislikes}
      </button>
    </div>
  );
}

export default PostReactionComponent;
