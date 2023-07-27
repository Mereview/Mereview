import { Container, Row, Col } from "react-bootstrap";
import LoginForm from "./LoginForm";
import "../../styles/css/Tab.css";
import SignUp from "./SignUp";
import { Input, Button, FloatLabelInput } from "../common/index";
import { useState } from "react";
import { Modal } from "react-bootstrap";

const Tab = () => {
  const [tabToggle, setTabToggle] = useState(true);
  const toggleHandler = (event: any) => {
    const id = event.target.id;
    console.log(event.target.id);
    if (id === "login Tab") {
      setTabToggle(true);
    } else if (id === "signup Tab") {
      setTabToggle(false);
    }
  };
  console.log(tabToggle);
  return (
    <Container style={{ overflow: "hidden" }}>
      <Row style={{ height: "100%vh - 86px" }}>
        <Col lg={1} className="align-item-center">
          <div style={{ height: "400px" }}></div>
          <div>
            <button className="Tab" id="login Tab" onClick={toggleHandler}>
              LOGIN
            </button>
          </div>
          <div>
            <button className="Tab" id="signup Tab" onClick={toggleHandler}>
              SIGN UP
            </button>
          </div>
        </Col>

        <Col lg={11}>
          <Row className="justify-content-center mt-5 mb-5">
            <img
              style={{ width: "500px", marginBottom: "100px" }}
              src="logo2.png"
              alt="Logo"
            />
          </Row>
          <Row>{tabToggle ? <LoginForm /> : <SignUp />}</Row>
        </Col>
      </Row>
    </Container>
  );
};

export default Tab;
