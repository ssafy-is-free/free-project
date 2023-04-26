import { createSlice } from '@reduxjs/toolkit';

export interface LoginState {
  isLogin: boolean;
}

const initialState: LoginState = {
  isLogin: false,
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    login(state) {
      console.log('로그인');
      state.isLogin = true;
    },
    logout(state) {
      localStorage.removeItem('accessToken');
      state.isLogin = false;
    },
  },
});

export const { login, logout } = authSlice.actions;
export default authSlice.reducer;
