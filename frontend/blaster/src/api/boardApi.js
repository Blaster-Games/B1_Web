import axios from 'axios';
import jwtAxios from '../util/jwtUtil';

const API_SERVER_HOST = 'https://native-pika-possibly.ngrok-free.app';

const postPrefix = `${API_SERVER_HOST}/api/post`;
const commentPrefix = `${API_SERVER_HOST}/api/comment`;
const postReactionPrefix = `${API_SERVER_HOST}/api/reaction/post`;

export const postListGet = async (requestParam) => {
  const res = await axios.get(`${postPrefix}/list`, { params: requestParam });
  return res.data;
};

export const getPostPatch = async (id) => {
  const res = await axios.patch(`${postPrefix}/${id}`);
  return res.data;
};

export const getImageUrlsPost = async (formData) => {
  const res = await jwtAxios.post(
    `${API_SERVER_HOST}/api/upload/images`,
    formData,
    {
      headers: { 'Content-Type': 'multipart/form-data' },
    },
  );
  console.log(res);
  return res;
};

export const postPost = async (category, title, updatedHtml) => {
  return await jwtAxios.post(`${postPrefix}`, {
    category: category.toUpperCase(),
    title: title,
    content: updatedHtml,
  });
};

export const commentListGet = async (postId) => {
  return await axios.get(`${commentPrefix}/list/${postId}`);
};

export const commentPost = async (content, postId) => {
  return await jwtAxios.post(`${commentPrefix}/`, {
    content: content,
    postId: postId,
  });
};

export const commentPut = async (commentId, content) => {
  return await jwtAxios.put(`${commentPrefix}/`, {
    commentId: commentId,
    content: content,
  });
};

export const commentDelete = async (commentId) => {
  return await jwtAxios.delete(`${commentPrefix}/${commentId}`);
};

export const reactionGet = async (postId) => {
  return await axios.get(`${postReactionPrefix}/${postId}`);
};

export const likePost = async (postId) => {
  return await jwtAxios.post(`${postReactionPrefix}/like/${postId}`);
};

export const dislikePost = async (postId) => {
  return await jwtAxios.post(`${postReactionPrefix}/dislike/${postId}`);
};

export const nonePost = async (postId) => {
  return await jwtAxios.post(`${postReactionPrefix}/none/${postId}`);
};

export const reactionCountGet = async (postId) => {
  return await axios.get(`${postPrefix}/reaction/${postId}`);
};
