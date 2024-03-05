import React, {useEffect, useState} from 'react';
import {Link, useLocation} from 'react-router-dom';
import {useSelector} from "react-redux";
import {Collapse} from "react-bootstrap";

function Sidebar({ servletPath }) {
    const location = useLocation(); // 현재 경로를 가져옵니다.
    const menuList = useSelector((state) => state.menu.menuList);
    const [openMenuId, setOpenMenuId] = useState(null);

    const handleMenuClick = (menuId) => {
        setOpenMenuId(openMenuId === menuId ? null : menuId);
    };

    useEffect(() => {
        const handleOutsideClick = (event) => {
            if (!event.target.closest("#accordionSidebar")) {
                setOpenMenuId(null); // 사이드바 외부 클릭 시 메뉴 닫기
            }
        };
        document.addEventListener("mousedown", handleOutsideClick);
        return () => document.removeEventListener("mousedown", handleOutsideClick);
    }, []);

    const isActive = (path) => location.pathname === path || location.pathname.includes(path);

    return (
        <ul className="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
            <a className="sidebar-brand d-flex align-items-center justify-content-center" href="#">
                <div className="sidebar-brand-icon rotate-n-15">
                    <i className="fas fa-laugh-wink"></i>
                </div>
                <div className="sidebar-brand-text mx-3">Payroll_Auto</div>
            </a>

            <hr className="sidebar-divider my-0"/>

            <li className={`nav-item ${isActive('/dashboard') ? 'active' : ''}`}>
                <Link className="nav-link" to="/dashboard">
                    <i className="fas fa-fw fa-tachometer-alt"></i>
                    <span>Main</span>
                </Link>
            </li>

            <hr className="sidebar-divider"/>

            <div className="sidebar-heading">
                메뉴
            </div>
            {menuList.map((menu, index) => (
                <li key={index}
                    className={`nav-item ${menu.sub_menus && openMenuId === menu.page_seq ? 'active' : ''}`}>
                    <Link className="nav-link" to='#' onClick={() => handleMenuClick(menu.page_seq)}>
                        <i className={menu.icon_class || ''}></i>
                        <span>{menu.gnb_name}</span>
                    </Link>
                    {menu.sub_menus && (
                        <Collapse in={openMenuId === menu.page_seq} id={`collapse${menu.page_seq}`}
                                  className="collapse">
                            <div>
                                <div className="bg-white py-2 collapse-inner rounded">
                                    <h6 className="collapse-header">{menu.page_name}: </h6>
                                    {menu.sub_menus.map((subMenu, subIndex) => (
                                        <Link key={subIndex} className={`collapse-item ${isActive(subMenu.page_url) ? 'active' : ''}`} to={subMenu.page_url}>
                                            {subMenu.gnb_name}
                                        </Link>
                                    ))}
                                </div>
                            </div>
                        </Collapse>
                    )}
                </li>
            ))}

            <div className="text-center d-none d-md-inline">
                <button className="rounded-circle border-0" id="sidebarToggle"></button>
            </div>

            <div className="sidebar-card d-none d-lg-flex">
            </div>
        </ul>
    );
}

export default Sidebar;