import axios from 'axios';
import jwtAxios from '../util/jwtUtil';

const API_SERVER_HOST = 'http://localhost:8080';

const prefix = `${API_SERVER_HOST}/api/post`;

export const postListGet = async (requestParam) => {
  const res = await axios.get(`${prefix}/list`, { params: requestParam });
  return res.data;
};

export const postGet = async (id) => {
  const res = await axios.get(`${prefix}/${id}`);
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

export const createPostPost = async (category, title, updatedHtml) => {
  return await jwtAxios.post(`${prefix}`, {
    category: category.toUpperCase(),
    title: title,
    content: updatedHtml,
  });
};
