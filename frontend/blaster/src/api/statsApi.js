import axios from 'axios';
import jwtAxios from '../util/jwtUtil';
import { API_SERVER_HOST } from './config';

const prefix = `${API_SERVER_HOST}/api/game`;

export const mapListGet = async () => {
  const res = await axios.get(`${prefix}/maps`);
  return res.data;
};

export const getStatsPost = async (statsParam) => {
  const res = await axios.post(`${prefix}/stats`, statsParam);
  return res.data;
};

export const myStatsGet = async (params) => {
  const res = await jwtAxios.get(`${prefix}/play-time`, { params });
  return res.data;
};

export const visitorStatsGet = async (params) => {
  const res = await axios.get(`${prefix}/visitors`, { params });
  console.log(res.data);
  return res.data;
};
