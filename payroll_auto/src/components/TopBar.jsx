import React, { useState } from 'react';
import { Navbar, Nav, NavDropdown, Modal, Button } from 'react-bootstrap';
import { useSelector } from "react-redux";
import {Link} from "react-router-dom";

function TopBar() {
    const currentUser = useSelector((state) => state.auth.currentUser);
    const [showModal, setShowModal] = useState(false); // 로그아웃 모달 상태 관리

    // 로그아웃 모달 표시/숨김 토글 함수
    const toggleModal = () => setShowModal(!showModal);

    return (
        <>
            <Navbar bg="light" expand="lg" className="mb-4 shadow">
                <Link to="/dashboard">
                    <Navbar.Brand>App Name</Navbar.Brand>
                </Link>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="ml-auto">
                        <NavDropdown title="사용자 메뉴" id="userDropdown" menuVariant="dark">
                            <NavDropdown.Item href="/system/profile">
                                <i className="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i> 프로필
                            </NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item onClick={toggleModal}>
                                <i className="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i> 로그아웃
                            </NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>

            {/* 로그아웃 확인 모달 */}
            <Modal show={showModal} onHide={toggleModal} centered>
                <Modal.Header closeButton>
                    <Modal.Title>로그아웃 하시겠습니까?</Modal.Title>
                </Modal.Header>
                <Modal.Body>현재 세션을 종료할 준비가 되어 있다면 아래의 "로그아웃"을 선택합니다.</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={toggleModal}>취소</Button>
                    <Button variant="primary" href="/logout">로그아웃</Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default TopBar;