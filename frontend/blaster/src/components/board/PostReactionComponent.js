import React, { useEffect, useState } from 'react';
import { REACTION } from '../../constants/boardConstants';
import {
  dislikePost,
  likePost,
  nonePost,
  reactionCountGet,
  reactionGet,
} from '../../api/boardApi';
import { useParams } from 'react-router-dom';

function PostReactionComponent({ likeCount, dislikeCount }) {
  const [likes, setLikes] = useState(likeCount);
  const [dislikes, setDislikes] = useState(dislikeCount);
  const [myReaction, setMyReaction] = useState(null);

  const { id } = useParams();

  useEffect(() => {
    setLikes(likeCount);
  }, [likeCount]);

  useEffect(() => {
    setDislikes(dislikeCount);
  }, [dislikeCount]);

  useEffect(() => {
    reactionGet(id).then((res) => {
      setMyReaction(res.data.reaction);
    });
  }, []);

  useEffect(() => {
    reactionCountGet(id)
      .then((res) => {
        setLikes(res.data.likeCount);
        setDislikes(res.data.dislikeCount);
      })
      .catch(console.error);
  });

  const handleOnClickLike = () => {
    if (myReaction === REACTION.LIKE) {
      nonePost(id)
        .then((res) => {
          setMyReaction(res.data.reaction);
        })
        .catch(console.error);
    } else {
      likePost(id)
        .then((res) => {
          setMyReaction(res.data.reaction);
        })
        .catch(console.error);
    }
  };

  const handleOnClickDisLike = () => {
    if (myReaction === REACTION.DISLIKE) {
      nonePost(id)
        .then((res) => {
          setMyReaction(res.data.reaction);
        })
        .catch(console.error);
    } else {
      dislikePost(id)
        .then((res) => {
          setMyReaction(res.data.reaction);
        })
        .catch(console.error);
    }
  };

  return (
    <div className="flex items-center gap-4 mb-6">
      <button
        onClick={handleOnClickLike}
        className={
          myReaction === REACTION.LIKE
            ? 'bg-blue-500 rounded flex items-center gap-1 text-sm text-gray-100'
            : 'rounded flex items-center gap-1 text-sm text-gray-400'
        }
      >
        <span role="img" aria-label="like">
          👍
        </span>
        {likes}
      </button>
      <button
        onClick={handleOnClickDisLike}
        className={
          myReaction === REACTION.DISLIKE
            ? 'bg-red-800 rounded flex items-center gap-1 text-sm text-gray-100'
            : 'rounded flex items-center gap-1 text-sm text-gray-400'
        }
      >
        <span role="img" aria-label="dislike">
          👎
        </span>
        {dislikes}
      </button>
    </div>
  );
}

export default PostReactionComponent;
