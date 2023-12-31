import { Container, Row, Col } from "react-bootstrap";
import Right from "../components/index/Right";
import Left from "../components/index/Left";
import "../styles/css/IndexPage.css";
import { useEffect } from "react";

const IndexPage = (props: any) => {
  const browserReset = () => {
    localStorage.removeItem("id");
    localStorage.removeItem("token");
  };
  useEffect(browserReset, []);

  const style = { padding: "0px", margin: "0px" };
  return (
    <div
      className="full-height-container"
      style={{ padding: "0px", margin: "0px", overflow: "hidden" }}
    >
      <Row>
        <Col md={5} style={style}>
          <Left />
        </Col>

        <Col className="right" md={7} style={style}>
          <Right />
        </Col>
      </Row>
    </div>
  );
};

export default IndexPage;
