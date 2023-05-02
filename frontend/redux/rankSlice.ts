import { PayloadAction, createSlice } from '@reduxjs/toolkit';

export interface RankSlice {
  filter:
    | {
        languageId: number;
        name: string;
      }[]
    | null;
}

const initialState: RankSlice = {
  filter: null,
};

const rankSlice = createSlice({
  name: 'rank',
  initialState,
  reducers: {
    setFilter(state, action: PayloadAction<{ languageId: number; name: string }>) {
      state.filter?.push(action.payload);
    },
  },
});

export const { setFilter } = rankSlice.actions;
export default rankSlice.reducer;
