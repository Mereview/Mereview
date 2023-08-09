import React, { useState } from "react";
import { Navbar, Nav } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import "../styles/css/NavigationBar.css";
import ProfileModal from "./ProfileModal";

const NavigationBar = ({ user }) => {
  const [isModal, setIsModal] = useState(false);
  const navigate = useNavigate();

  const profilURL = user.profileImage?.id
    ? `http://localhost:8080/api/image/download/profiles/${user.profileImage.id}`
    : "/testProfile.gif";

  const modalToggler = () => {
    setIsModal((currentState) => !currentState);
  };

  return (
    <div>
      <Navbar bg="dark" expand="lg">
        {/* 로고 */}
        <Navbar.Brand href="/review" className="ms-4">
          <img src={"/logo1.png"} height="60" alt="Logo" />
        </Navbar.Brand>

        <Navbar.Toggle aria-controls="basic-navbar-nav" />

        {/* 버튼리스트 */}
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="ms-auto me-4">
            <Nav.Link href="/review" className="text-white fs-4">
              리뷰 전체보기
            </Nav.Link>
            <Nav.Link href="/review/write" className="text-white fs-4">
              리뷰 작성하기
            </Nav.Link>
            <Nav.Link href="/profile" className="text-white fs-4">
              프로필
            </Nav.Link>
            <Nav.Item>
              <div
                className="profile"
                style={{ backgroundImage: `url(${profilURL})` }}
                onClick={modalToggler}
              ></div>
            </Nav.Item>
          </Nav>
        </Navbar.Collapse>
      </Navbar>
      {isModal && <ProfileModal />}
    </div>
  );
};

export default NavigationBar;
