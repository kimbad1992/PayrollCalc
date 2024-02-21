import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

// 비동기 액션 생성
export const fetchMenuList = createAsyncThunk('menu/fetchMenuList', async () => {
    const response = await axios.get('/api/system/menuList');
    return response.data.data;
});

// 메뉴 리스트를 평탄화하는 함수
const flattenMenuList = (menuList) => {
    let flattened = [];

    const flatten = (menus) => {
        menus.forEach(menu => {
            if (menu.sub_menus && menu.sub_menus.length > 0) {
                flatten(menu.sub_menus);
            } else {
                flattened.push(menu);
            }
        });
    };

    flatten(menuList);
    return flattened;
};

const menuSlice = createSlice({
    name: 'menu',
    initialState: {
        menuList: [],
        flattenedMenuList: [], // 평탄화된 메뉴 리스트를 저장
        status: 'idle', // 'idle', 'loading', 'succeeded', 'failed'
        error: null,
    },
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(fetchMenuList.pending, (state) => {
                state.status = 'loading';
            })
            .addCase(fetchMenuList.fulfilled, (state, action) => {
                state.status = 'succeeded';
                state.menuList = action.payload;
                state.flattenedMenuList = flattenMenuList(action.payload); // 평탄화된 메뉴 리스트를 상태에 저장
            })
            .addCase(fetchMenuList.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            });
    },
});

export default menuSlice.reducer;