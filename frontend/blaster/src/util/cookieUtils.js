import { Cookies } from 'react-cookie';

const cookies = new Cookies();

export const setCookie = (name, value, days) => {
  const expires = new Date();
  expires.setUTCDate(expires.getUTCDate() + days);
  cookies.set(name, value, { expires: expires, path: '/' });
};

export const getCookie = (name) => {
  const cookieValue = cookies.get(name);
  try {
    return cookieValue ? JSON.parse(cookieValue) : null;
  } catch (error) {
    return cookieValue;
  }
};

export const removeCookie = (name, path = '/') => {
  cookies.remove(name, { path: path });
};
