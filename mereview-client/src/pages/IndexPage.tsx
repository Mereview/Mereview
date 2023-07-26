import { Container, Row, Col } from "react-bootstrap";
import Tab from "../components/index/Tab";
import "../styles/css/IndexPage.css";
const IndexPage = (props: any) => {
  return (
    <Container fluid className="full-height-container">
      <Row>
        <Col md={4}>
          <div style={{ backgroundColor: "tomato" }}>
            <img src="/test.jpg" alt="intro" />
          </div>
        </Col>

        <Col md={8}>
          <Tab />
        </Col>
      </Row>
      <h1>
        크아아아아아아아ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ악!!!!!!!!!!!!!!!!!!!
        안된다고!!!
      </h1>
    </Container>
  );
};

export default IndexPage;
