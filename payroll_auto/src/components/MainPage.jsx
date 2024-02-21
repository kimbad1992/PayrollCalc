import React, {useEffect, useState} from 'react';
import Sidebar from "./Sidebar";
import {Navigate, Route, Routes} from "react-router-dom";
import {useSelector} from "react-redux";

function MainPage() {
    const menuList = useSelector((state) => state.menu.menuList);
    // parent_page_seq가 null인 항목들만 필터링 (실제 Content 페이지를 가질 항목만)
    const routesList = useSelector((state) => state.menu.flattenedMenuList);

    return (
        <div>
            <Sidebar/>
            <div id="contents">
                {/* MainPage 내부의 Routes 정의를 상대 경로로 변경 */}
                <Routes>
                    {routesList.map((route, index) => (
                        <Route key={index} path={route.page_url} element={<div><h1 style={{color:'red'}}>{route.gnb_name}</h1></div>} />
                    ))}
                </Routes>
            </div>
        </div>
    );
}

export default MainPage;
