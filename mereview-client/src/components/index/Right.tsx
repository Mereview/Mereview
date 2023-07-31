import { Container, Row, Col } from "react-bootstrap";
import LoginForm from "./LoginForm";
import "../../styles/css/Right.css";
import SignUp from "./SignUp";
import { useSelector } from "react-redux";

const Right = () => {
  const style = { padding: "0px", margin: "0px" };
  const tab = useSelector((state: any) => state.ui.tabToggle);
  return (
    <Container style={style}>
      <Row className="justify-content-center mt-5 mb-5" style={style}>
        <img
          style={{ width: "500px", marginBottom: "100px", marginTop: "80px" }}
          src="logo2.png"
          alt="Logo"
        />
      </Row>
      <Row style={style}>{tab === "login" ? <LoginForm /> : <SignUp />}</Row>
    </Container>
  );
};

export default Right;
