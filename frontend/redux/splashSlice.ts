import { createSlice } from '@reduxjs/toolkit';

// splash screen 한번만 실행 되게

export interface SplashState {
  check: boolean;
}

const initialState: SplashState = {
  check: false,
};

const splashSlice = createSlice({
  name: 'splash',
  initialState,
  reducers: {
    splashCheck(state) {
      state.check = true;
    },
  },
});

export const { splashCheck } = splashSlice.actions;
export default splashSlice.reducer;
