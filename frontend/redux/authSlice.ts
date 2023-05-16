import { createSlice } from '@reduxjs/toolkit';

export interface LoginState {
  isLogin: boolean;
  isNew: boolean;
  isBoj: boolean;
}

const initialState: LoginState = {
  isLogin: false,
  isNew: false,
  isBoj: false,
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
    setBoj(state) {
      // 로그인할 때 있으면 실행, 백준 등록할떄도
      state.isBoj = true;
    },
  },
});

export const { login, logout, setNew, setBoj } = authSlice.actions;
export default authSlice.reducer;
