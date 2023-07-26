import { Container, Row, Col } from "react-bootstrap";
import LoginForm from "../components/index/LoginForm";
import SignUp from "../components/index/SignUp";
import Tab from "../components/index/Tab";
const IndexPage = (props: any) => {
  return (
    <Container>
      <Row>
        <Col></Col>
        <Col>
          <Tab />
        </Col>
      </Row>
    </Container>
  );
};

export default IndexPage;
