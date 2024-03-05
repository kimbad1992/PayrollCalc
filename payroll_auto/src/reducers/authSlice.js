import {createAsyncThunk, createSlice} from '@reduxjs/toolkit';
import axios from 'axios';

export const requestLogin = createAsyncThunk(
    'auth/loginUser',
    async ({ username, password }, { rejectWithValue }) => {
        try {
            const response = await axios.post('/api/login/sign', {
                username,
                password,
            });

            localStorage.setItem("token", JSON.stringify(response.data.data));
            return response.data.data;
        } catch (err) {
            // 로그인 실패 시 에러 메시지 반환
            return rejectWithValue(err.response.data.message);
        }
    }
);

// createSlice 함수를 사용하여 슬라이스 생성
const authSlice = createSlice({
    name: 'auth', // 슬라이스의 이름
    initialState: {
        currentUser: null, // 초기 상태 설정
        errorMsg: '',
    },
    reducers: {
        setUser: (state, action) => {
            state.currentUser = action.payload; // 직접 상태를 수정할 수 있음
        },
    },
    extraReducers: (builder) => {
        builder
            .addCase(requestLogin.pending, (state) => {
                // 로그인 시도 중 상태 업데이트
                state.errorMsg = '';
            })
            .addCase(requestLogin.fulfilled, (state, action) => {
                // 로그인 성공 처리
                state.currentUser = action.payload;
                state.errorMsg = '';
            })
            .addCase(requestLogin.rejected, (state, action) => {
                // 로그인 실패 처리
                state.errorMsg = action.payload;
            });
    },
});

// 생성된 액션 생성자 함수와 리듀서 함수를 내보냄
export const { setUser } = authSlice.actions;
export default authSlice.reducer;