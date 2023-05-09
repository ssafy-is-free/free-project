import { createSlice } from '@reduxjs/toolkit';

export interface LoginState {
  isLogin: boolean;
  isNew: boolean;
  loginStart: boolean;
  // clickProfile: boolean;
}

const initialState: LoginState = {
  isLogin: false,
  isNew: false,
  loginStart: false,
  // clickProfile: false,
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    login(state) {
      // console.log('로그인');
      state.isLogin = true;
    },
    logout(state) {
      localStorage.removeItem('accessToken');
      state.isLogin = false;
    },
    setNew(state) {
      state.isNew = !state.isNew;
    },
    setLoginStart(state) {
      state.loginStart = true;
    },
    // setClickProfile(state) {
    //   state.clickProfile = !state.clickProfile;
    // },
  },
});

export const { login, logout, setNew, setLoginStart } = authSlice.actions;
export default authSlice.reducer;
