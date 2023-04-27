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
      console.log('로그인');
      state.isLogin = true;
    },
    logout(state) {
      localStorage.removeItem('accessToken');
      state.isLogin = false;
    },
    setNew(state) {
      state.isNew = true;
    },
  },
});

export const { login, logout, setNew } = authSlice.actions;
export default authSlice.reducer;
