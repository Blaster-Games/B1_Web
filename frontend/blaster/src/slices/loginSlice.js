import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { getCookie, removeCookie, setCookie } from '../util/cookieUtils';
import { loginPost } from '../api/memberApi';

const initialState = {
  email: '',
};

export const loginPostAsync = createAsyncThunk('loginPostAsync', (param) =>
  loginPost(param),
);

const loginSlice = createSlice({
  name: 'loginSlice',
  initialState: getCookie('member') || initialState,
  reducers: {
    logout: () => {
      removeCookie('member');
      return { ...initialState };
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(loginPostAsync.fulfilled, (state, action) => {
        const payload = action.payload;
        if (!payload.error) {
          setCookie('member', JSON.stringify(payload), 1);
        }
        return payload;
      })
      .addCase(loginPostAsync.pending, (state, action) => {
        // console.log(action);
      })
      .addCase(loginPostAsync.rejected, (state, action) => {
        // console.log(action);
      });
  },
});

export const { logout } = loginSlice.actions;
export default loginSlice.reducer;
