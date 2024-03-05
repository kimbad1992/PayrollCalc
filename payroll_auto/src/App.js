import React, { useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import {useDispatch, useSelector} from 'react-redux';
import LoginPage from './components/LoginPage';
import MainPage from './components/MainPage';
import {setUser} from "./reducers/authSlice";
import {fetchMenuList} from "./reducers/menuSlice";

function App() {
    const dispatch = useDispatch();
    const currentUser = useSelector((state) => state.auth.currentUser);
    useEffect(() => {
        // 로컬 스토리지에서 토큰(또는 사용자 정보) 검사
        const storedUser = sessionStorage.getItem('jwtToken'); // 예시로 jwtToken 사용
        if (storedUser) {
            dispatch(setUser(storedUser)); // 저장된 사용자 정보로 상태 업데이트
        }
        dispatch(fetchMenuList()) // 메뉴 데이터 업데이트
    }, [dispatch]);

    return (
        <Router>
            <Routes>
                <Route path="/login" element={currentUser ? <Navigate to="/" replace/> : <LoginPage/>}/>
                <Route path="/*" element={currentUser ? <MainPage/> : <Navigate to="/login" replace/>}/>
                {/* 존재하지 않는 모든 경로에 대한 처리 */}
                {/*<Route path="*" element={currentUser ? <Navigate to="/" replace /> : <Navigate to="/login" replace />} />*/}
            </Routes>
        </Router>
    );
}

export default App;
