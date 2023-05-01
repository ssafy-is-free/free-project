import { configureStore, ThunkAction, Action, EnhancedStore } from '@reduxjs/toolkit';
import { MakeStore, createWrapper } from 'next-redux-wrapper';
import { useDispatch } from 'react-redux';
import persistedReducer from './reducer';
import { persistStore } from 'redux-persist';
// import rootReducer from './reducer';

const isDev = process.env.NODE_ENV === 'development';
const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) => [...getDefaultMiddleware()],
  devTools: isDev,
});

// const makeStore = () => {
//   const store = configureStore({
//     // reducer: rootReducer,
//     reducer: persistedReducer,
//     middleware: (getDefaultMiddleware) => [...getDefaultMiddleware()],
//     devTools: isDev,
//   });
//   return store;
// };

// 아래 추가
const setupStore = (context: any): EnhancedStore => store;
const makeStore: MakeStore<any> = (context: any) => setupStore(context);
const wrapper = createWrapper(makeStore);

/*
  아래는 wrapper로 스토어를 생성하고 클라이언트에서 사용할 typeScript용 
  dispatch와 state 및 asynchronous dispatch의 타입 작성 코드이다.
  */
type AppStore = ReturnType<typeof makeStore>;
type AppDispatch = AppStore['dispatch'];
export type RootState = ReturnType<AppStore['getState']>;
export const useAppDispatch = () => useDispatch<AppDispatch>();
export type AppThunk<ReturnType = void> = ThunkAction<ReturnType, RootState, unknown, Action>;

// 추가
export const persistor = persistStore(store);

export default wrapper;
