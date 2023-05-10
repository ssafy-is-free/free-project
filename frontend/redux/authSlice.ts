import { createSlice } from '@reduxjs/toolkit';

export interface LoginState {
  isLogin: boolean;
  isNew: boolean;
}

const initialState: LoginState = {
  isLogin: false,
  isNew: false,
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    login(state) {
      state.isLogin = true;
    },
    logout(state) {
      localStorage.removeItem('accessToken');
      state.isLogin = false;
    },
    setNew(state) {
      state.isNew = !state.isNew;
    },
  },
});

export const { login, logout, setNew } = authSlice.actions;
export default authSlice.reducer;
