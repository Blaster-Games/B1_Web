import axios from 'axios';
import jwtAxios from '../util/jwtUtil';

const API_SERVER_HOST = 'http://localhost:8080';

const prefix = `${API_SERVER_HOST}/api/post`;

export const postListGet = async (requestParam) => {
  const res = await axios.get(`${prefix}/list`, { params: requestParam });
  return res.data;
};
