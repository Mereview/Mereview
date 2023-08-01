import { Navbar, Nav } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import "../styles/css/NavigationBar.css";
import { useState } from "react";
import ProfileModal from "./ProfileModal";
const NavigationBar = () => {
  const profileURL = useSelector((state: any) => state.user.profileURL);
  const style = profileURL
    ? { backgroundImage: `url(${profileURL})` }
    : { backgroundImage: "url(/testProfile.gif)" };
  const [isModal, setIsModal] = useState(false);
  const modalToggler = () => {
    setIsModal((currentState: boolean) => !currentState);
    console.log(isModal);
  };

  return (
    <div>
      <Navbar bg="dark" expand="lg">
        {/* 로고 */}
        <Navbar.Brand href="/" className="ms-4">
          <img src={"/logo1.png"} height="60"></img>
        </Navbar.Brand>

        <Navbar.Toggle aria-controls="basic-navbar-nav" />

        {/* 버튼리스트 */}
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="ms-auto me-4">
            <Nav.Link href="/review" className="text-white fs-4">
              review
            </Nav.Link>
            <Nav.Link href="/profile" className="text-white fs-4">
              profile
            </Nav.Link>
            <Nav.Item>
              <div
                className="profile"
                style={style}
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
