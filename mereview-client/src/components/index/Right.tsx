import { Container, Row, Col } from "react-bootstrap";
import LoginForm from "./LoginForm";
import "../../styles/css/Tab.css";
import SignUp from "./SignUp";
import { useSelector } from "react-redux";

const Right = () => {
  const tab = useSelector((state: any) => state.ui.tabToggle);
  return (
    <Container style={{ overflow: "hidden", padding: "0px" }}>
      <Row className="justify-content-center mt-5 mb-5">
        <img
          style={{ width: "500px", marginBottom: "100px" }}
          src="logo2.png"
          alt="Logo"
        />
      </Row>
      <Row>{tab === "login" ? <LoginForm /> : <SignUp />}</Row>
    </Container>
  );
};

export default Right;
