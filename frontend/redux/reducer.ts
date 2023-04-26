import { combineReducers } from 'redux';
import { HYDRATE } from 'next-redux-wrapper';
import countSlice from './countSlice';
import splashSlice from './splashSlice';
import authSlice from './authSlice';

const combinedReducer = combineReducers({
  counter: countSlice,
  splashChecker: splashSlice,
  authChecker: authSlice,
});

const rootReducer: typeof combinedReducer = (state, action) => {
  if (action.type === HYDRATE) {
    const nextState = {
      ...state,
      ...action.payload,
    };
    return nextState;
  } else {
    return combinedReducer(state, action);
  }
};
export default rootReducer;
