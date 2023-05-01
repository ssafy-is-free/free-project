import { combineReducers } from 'redux';
import { HYDRATE } from 'next-redux-wrapper';
import countSlice from './countSlice';
import splashSlice from './splashSlice';
import authSlice from './authSlice';
import storage from 'redux-persist/lib/storage';
import { persistReducer } from 'redux-persist';
import storageSession from 'redux-persist/lib/storage/session';

const persistConfig = {
  key: 'root',
  storage,
};

const reducers = combineReducers({
  counter: countSlice,
  splashChecker: splashSlice,
  authChecker: authSlice,
});

const rootReducer = persistReducer(persistConfig, reducers);

const persistedReducer: typeof rootReducer = (state, action) => {
  if (action.type === HYDRATE) {
    const nextState = {
      ...state,
      ...action.payload,
    };
    return nextState;
  } else {
    return rootReducer(state, action);
  }
};

export default persistedReducer;

// const combinedReducer = combineReducers({
//   counter: countSlice,
//   splashChecker: splashSlice,
//   authChecker: authSlice,
// });

// const rootReducer: typeof combinedReducer = (state, action) => {
//   if (action.type === HYDRATE) {
//     const nextState = {
//       ...state,
//       ...action.payload,
//     };
//     return nextState;
//   } else {
//     return combinedReducer(state, action);
//   }
// };
// export default rootReducer;
