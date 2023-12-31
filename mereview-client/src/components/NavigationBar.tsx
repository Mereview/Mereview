import { Navbar, Nav } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import "../styles/css/NavigationBar.css";
import { useEffect, useState } from "react";
import ProfileModal from "./ProfileModal";
const NavigationBar = ({ user }) => {
  const [isModal, setIsModal] = useState(false);

  const [notification, setNotification] = useState(false);
  const notifications = useSelector((state: any) => state.notification.notific);

  // console.log(notifications);

  const profilURL = user.profileImage?.id
    ? `${process.env.REACT_APP_API_URL}/image/download/profiles/${user.profileImage.id}`
    : "/testProfile.gif";

  const modalToggler = () => {
    setIsModal((currentState: boolean) => !currentState);
  };
  const checkNotifications = (): boolean => {
    return user.notifications?.some((notification) => notification.status === "UNCONFIRMED");
  };
  useEffect(() => {
    // console.log("알람 확인");
    checkNotifications() ? setNotification(true) : setNotification(false);
  });

  return (
    <div>
      <Navbar bg="dark" expand="md">
        {/* 로고 */}
        <Navbar.Brand href="/review" className="ms-4">
          <img src={"/logo1.png"} height="50"></img>
        </Navbar.Brand>

        <Navbar.Toggle aria-controls="basic-navbar-nav" />

        {/* 버튼리스트 */}
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="ms-auto me-4">
            <Nav.Link href="/review" className="text-white fs-5">
              리뷰 전체보기
            </Nav.Link>
            <Nav.Link href="/review/write" className="text-white fs-5">
              리뷰 작성하기
            </Nav.Link>
            <Nav.Link href="/notification" className="text-white fs-5">
              <div className="notification-container" style={{ position: "relative" }}>
                <span className="material-symbols-outlined">notifications</span>
                <div
                  className="alarm"
                  style={{ visibility: notification ? "visible" : "hidden" }}
                ></div>
              </div>
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
      {isModal && <ProfileModal setIsModal={setIsModal} />}
    </div>
  );
};

export default NavigationBar;
