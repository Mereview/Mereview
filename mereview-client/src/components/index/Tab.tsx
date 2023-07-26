import { Container, Row, Col } from "react-bootstrap";
import LoginForm from "./LoginForm";
import "../../styles/css/Tab.css";
import SignUp from "./SignUp";
import { Input, Button, FloatLabelInput } from "../common/index";
import { useState } from "react";

const Tab = () => {
  const [tabToggle, setTabToggle] = useState(true);
  const toggleHandler = (event: any) => {
    if (event.target.id === "login tab") {
    }
  };
  return (
    <Container>
      <Row>
        <Col className="align-item-center">
          <div>
            <button className="Tab" id="login Tab" onClick={toggleHandler}>
              LOGIN
            </button>
          </div>
          <div>
            <button id="signup Tab">SIGN UP</button>
          </div>
        </Col>
        <Col>
          <Row className="justify-content-center mt-5 mb-5">
            <img style={{ width: "500px" }} src="logo2.png" alt="Logo" />
          </Row>
          <Row>
            <LoginForm></LoginForm>
          </Row>
          <Row>
            <SignUp></SignUp>
          </Row>
        </Col>
      </Row>
    </Container>
  );
};

export default Tab;
