import { combineReducers } from 'redux';
import { HYDRATE } from 'next-redux-wrapper';
import countSlice from './countSlice';
import splashSlice from './splashSlice';

const combinedReducer = combineReducers({
  counter: countSlice,
  splashChecker: splashSlice,
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
