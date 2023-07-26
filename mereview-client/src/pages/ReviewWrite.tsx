import React from "react";
import { Form, Container, Row, Col } from "react-bootstrap";
import { useState } from "react";
import "../styles/css/NavBar.css";

const ReviewWrite = () => {
  const [nickname, setNickname] = useState("닉네임");
  const [profile, setProfile] = useState("/logo2.png");
  return (
    <Container className="mx-auto my-4 vh-100 border border-5 rounded-5">
      <Row className="mt-5">
        <Col className="t-right">
          <p className="fs-4">
            {nickname}
            <img src={profile} height="30px" className="c-img"></img>
          </p>
        </Col>
      </Row>
      <Row>
        <Col>
          <Form.Control
            placeholder="영화 제목을 입력하세요"
            className="border rounded-2 text-lg"
            size="lg"
          ></Form.Control>
        </Col>
      </Row>
    </Container>
  );
};

export default ReviewWrite;
