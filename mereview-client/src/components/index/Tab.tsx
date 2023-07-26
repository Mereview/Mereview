import { Container, Row } from "react-bootstrap";
import LoginForm from "./LoginForm";
import SignUp from "./SignUp";
const Tab = () => {
  return (
    <Container>
      <Row className="justify-content-center mt-5 mb-5">
        <img style={{ width: "500px" }} src="logo2.png" alt="Logo" />
      </Row>
      <Row>
        <LoginForm></LoginForm>
      </Row>
      <Row>
        <SignUp></SignUp>
      </Row>
    </Container>
  );
};

export default Tab;
