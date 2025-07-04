import axios from 'axios';
import jwtAxios from '../util/jwtUtil';
import { API_SERVER_HOST } from './config';

const prefix = `${API_SERVER_HOST}/api/member`;

export const loginPost = async (loginParam) => {
  const header = { headers: { 'Content-Type': 'x-www-form-urlencoded' } };

  const form = new FormData();
  form.append('username', loginParam.username);
  form.append('password', loginParam.password);

  const res = await axios.post(`${prefix}/login`, form, header);

  return res.data;
};

export const nicknameCheckGet = async (nickname) => {
  const res = await axios.get(`${prefix}/signup/nickname/${nickname}`);
  return res.data;
};

export const emailCheckGet = async (email) => {
  const res = await axios.get(`${prefix}/signup/email/${email}`);
  return res.data;
};

export const signUpPost = async (signUpParam) => {
  return await axios.post(`${prefix}/signup`, signUpParam);
};

export const nicknameChangePost = async (nickname) => {
  const res = await jwtAxios.put(`${prefix}/nickname`, { nickname });
  return res.data;
};

export const passwordChangePut = async (passwordParam) => {
  return await jwtAxios.put(`${prefix}/password`, passwordParam);
};
