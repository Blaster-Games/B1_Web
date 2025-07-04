import axios from 'axios';
import { getCookie, setCookie } from './cookieUtils';
import { API_SERVER_HOST } from '../api/config';

const jwtAxios = axios.create();

const refreshJWT = async (accessToken, refreshToken) => {
  const host = API_SERVER_HOST;

  const header = { headers: { Authorization: `Bearer ${accessToken}` } };

  const res = await axios.get(
    `${host}/api/member/refresh?refreshToken=${refreshToken}`,
    header,
  );

  return res.data;
};

//before request
const beforeReq = (config) => {
  const memberInfo = getCookie('member');
  if (!memberInfo) {
    console.log('Member NOT FOUND');
    return Promise.reject({ response: { data: { error: 'REQUIRE_LOGIN' } } });
  }
  const { accessToken } = memberInfo;

  // Authorization 헤더 처리
  config.headers.Authorization = `Bearer ${accessToken}`;

  return config;
};

//fail request
const requestFail = (err) => {
  console.log('request error............');
  return Promise.reject(err);
};

//before return response
const beforeRes = async (res) => {
  const data = res.data;

  if (data && data.error) {
    if (data.error === 'Expired') {
      const memberCookieValue = getCookie('member');

      const result = await refreshJWT(
        memberCookieValue.accessToken,
        memberCookieValue.refreshToken,
      );

      memberCookieValue.accessToken = result.accessToken;
      memberCookieValue.refreshToken = result.refreshToken;
      setCookie('member', JSON.stringify(memberCookieValue), 1);

      const originalRequest = res.config;
      originalRequest.headers.Authorization = `Bearer ${result.accessToken}`;

      return await axios(originalRequest);
    } else if (data.error === 'RefreshToken_Expired') {
      return Promise.reject({ response: { data: { error: 'REQUIRE_LOGIN' } } });
    }
  }
  return res;
};

//fail response
const responseFail = (err) => {
  console.log('response fail error.............');
  return Promise.reject(err);
};

jwtAxios.interceptors.request.use(beforeReq, requestFail);
jwtAxios.interceptors.response.use(beforeRes, responseFail);

export default jwtAxios;
