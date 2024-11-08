import { createSlice } from '@reduxjs/toolkit';

const initState = {
  email: '',
}

const loginSlice = createSlice({
  name: 'loginSlice',
  initialState: initState,
  reducers: {
    test: (state, action) => {},
  }
})

export const { test } = loginSlice.actions;
export default loginSlice.reducer;
