import { configureStore } from '@reduxjs/toolkit';
import authReducer from './reducers/authSlice'; // 리팩토링된 리듀서의 경로
import menuReducer from './reducers/menuSlice'; // 리팩토링된 리듀서의 경로

const store = configureStore({
    reducer: {
        auth: authReducer, // 스토어에 리듀서를 등록
        menu: menuReducer,
    },
});

export default store;