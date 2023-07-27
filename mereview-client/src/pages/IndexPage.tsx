import { Container, Row, Col } from "react-bootstrap";
import Tab from "../components/index/Tab";
import "../styles/css/IndexPage.css";
const IndexPage = (props: any) => {
  return (
    <Container
      fluid
      className="full-height-container"
      style={{ padding: "0px" }}
    >
      <Row>
        <Col md={5}>
          <div style={{ backgroundColor: "tomato" }}>
            <img src="/test.jpg" alt="intro" />
          </div>
        </Col>

        <Col md={7}>
          <Tab />
        </Col>
      </Row>
    </Container>
  );
};

export default IndexPage;
