import React, {useEffect, useState} from 'react';
import Sidebar from "./Sidebar";
import {Navigate, Route, Routes} from "react-router-dom";
import {useSelector} from "react-redux";
import routeMappings from '../setting/routeMapping';
import ErrorPage from "./ErrorPage";
import TopBar from "./TopBar";
import Dashboard from "./Dashboard";

function MainPage() {
    const menuList = useSelector((state) => state.menu.menuList);
    // parent_page_seq가 null인 항목들만 필터링 (실제 Content 페이지를 가질 항목만)
    const routesList = useSelector((state) => state.menu.flattenedMenuList);

    return (
        <div id="wrapper">
            <Sidebar/>
            <div id="content-wrapper" className="d-flex flex-column">
                <div id="content">
                    <TopBar/>
                    {/* MainPage 내부의 Routes 정의를 상대 경로로 변경 */}
                    <Routes>
                        {routesList.map((route, index) => {
                            const Component = routeMappings.find((mapping) => mapping.path === route.page_url)?.component;
                            return Component ? (
                                <Route key={index} path={route.page_url} element={<Component />} />
                            ) : null;
                        })}
                        <Route path="dashboard" element={<Dashboard/>}/>
                        <Route path="*" element={<ErrorPage />} /> {/* 존재하지 않는 경로 처리 */}
                    </Routes>
                </div>
            </div>
        </div>
    );
}

export default MainPage;
