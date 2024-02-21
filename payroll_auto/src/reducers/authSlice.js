import { createSlice } from '@reduxjs/toolkit';

// createSlice 함수를 사용하여 슬라이스 생성
const authSlice = createSlice({
    name: 'auth', // 슬라이스의 이름
    initialState: {
        currentUser: null, // 초기 상태 설정
    },
    reducers: {
        // 리듀서와 액션을 한 번에 정의
        setUser: (state, action) => {
            state.currentUser = action.payload; // 직접 상태를 수정할 수 있음
        },
    },
});

// 생성된 액션 생성자 함수와 리듀서 함수를 내보냄
export const { setUser } = authSlice.actions;
export default authSlice.reducer;