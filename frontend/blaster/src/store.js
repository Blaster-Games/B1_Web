import { configureStore } from '@reduxjs/toolkit';
import loginSlice from './slices/loginSlice';
import editPostSlice from './slices/editPostSlice';

export default configureStore({
  reducer: {
    loginSlice: loginSlice,
    editPostSlice: editPostSlice,
  },
});
