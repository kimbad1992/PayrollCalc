import React, {useEffect, useState} from 'react';
import { Link } from 'react-router-dom';
import {useSelector} from "react-redux";
import {Collapse} from "react-bootstrap";

// 가정: 메뉴 데이터와 현재 경로 정보는 상위 컴포넌트 또는 컨텍스트에서 받아옴
function Sidebar({ servletPath }) {
    const menuList = useSelector((state) => state.menu.menuList);
    const [open, setOpen] = useState(false);
    const [openMenuId, setOpenMenuId] = useState(null);

    // 메뉴 아이템 클릭 핸들러
    const handleMenuClick = (menuId) => {
        // 이미 열려 있는 메뉴를 클릭한 경우 메뉴를 닫고, 그렇지 않은 경우 메뉴를 엽니다.
        setOpenMenuId(openMenuId === menuId ? null : menuId);
    };


    return (
        <ul className="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
            <a className="sidebar-brand d-flex align-items-center justify-content-center" href="/main">
                <div className="sidebar-brand-icon rotate-n-15">
                    <i className="fas fa-laugh-wink"></i>
                </div>
                <div className="sidebar-brand-text mx-3">Payroll_Auto</div>
            </a>

            <hr className="sidebar-divider my-0"/>

            <li className="nav-item active">
                <Link className="nav-link" to="/">
                    <i className="fas fa-fw fa-tachometer-alt"></i>
                    <span>Main</span>
                </Link>
            </li>

            <hr className="sidebar-divider"/>

            <div className="sidebar-heading">
                메뉴
            </div>
            {menuList.map((menu, index) => (
                <li key={index} className={`nav-item ${menu.sub_menus ? 'has-sub-menu' : ''}`}>
                    <Link className="nav-link" onClick={() => handleMenuClick(menu.page_seq)}>
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
                                        <Link key={subIndex} className="collapse-item" to={subMenu.page_url}>
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