import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  title: '',
  content: '',
};

const editPostSlice = createSlice({
  name: 'editPostSlice',
  initialState: initialState,
  reducers: {
    savePost: (state, action) => {
      state.title = action.payload.title;
      state.content = action.payload.content;
    },
    clear: () => {
      return initialState;
    },
  },
});

export const { savePost, clear } = editPostSlice.actions;
export default editPostSlice.reducer;
