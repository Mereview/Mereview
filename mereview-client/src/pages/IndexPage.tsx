import { Container, Row, Col } from "react-bootstrap";
import Right from "../components/index/Right";
import Left from "../components/index/Left";
import "../styles/css/IndexPage.css";

const IndexPage = (props: any) => {
  return (
    <div className="full-height-container" style={{ padding: "0px" }}>
      <Row>
        <Col>
          <Left />
        </Col>

        <Col className="right" md={7}>
          <Right />
        </Col>
      </Row>
    </div>
  );
};

export default IndexPage;
