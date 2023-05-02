import { createSlice } from '@reduxjs/toolkit';

export interface LoginState {
  isLogin: boolean;
  isNew: boolean;
  loginStart: boolean;
  isLoginIng: boolean;
}

const initialState: LoginState = {
  isLogin: false,
  isNew: false,
  loginStart: false,
  isLoginIng: true,
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
    setNew(state) {
      // state.isNew = true;
      state.isNew = !state.isNew;
    },
    setLoginIng(state) {
      state.isLoginIng = false;
    },
    setLoginStart(state) {
      state.loginStart = true;
    },
  },
});

export const { login, logout, setNew, setLoginIng, setLoginStart } = authSlice.actions;
export default authSlice.reducer;
