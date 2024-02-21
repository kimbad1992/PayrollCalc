import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import LoginPage from './components/LoginPage';
import MainPage from './components/MainPage';

function App() {
    const currentUser = useSelector((state) => state.auth.currentUser);

    console.log("현재 유저:", currentUser)

    return (
        <Router>
            <Routes>
                <Route path="/login" element={currentUser ? <Navigate to="/" replace /> : <LoginPage />} />
                <Route path="/" element={currentUser ? <MainPage /> : <Navigate to="/login" replace />} />
                {/* 존재하지 않는 모든 경로에 대한 처리 */}
                <Route path="*" element={currentUser ? <Navigate to="/" replace /> : <Navigate to="/login" replace />} />
            </Routes>
        </Router>
    );
}

export default App;
