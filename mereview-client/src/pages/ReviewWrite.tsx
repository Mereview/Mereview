import { Form, Container, Row, Col } from "react-bootstrap";
import React, { useState, useRef } from "react";
import "../styles/css/ReviewWrite.css";
import { Button } from "../components/common";

const ReviewWrite = () => {
  const [nickname, setNickname] = useState("닉네임");
  const [profile, setProfile] = useState("/logo2.png");
  const [selectedFile, setSelectedFile] = useState(null);
  const selectedFileHandler = (event: any) => {
    setSelectedFile(event.target.files[0].name);
  };
  const imgInput = useRef<HTMLInputElement>(null);
  const onClickImgInput = (event: React.MouseEvent<HTMLButtonElement>) => {
    if (imgInput.current) {
      imgInput.current.click();
    }
  };
  return (
    <Container className="mx-auto my-4 vh-100 border border-dark border-5 rounded-5">
      <Row className="mt-5">
        <Col className="t-right me-4">
          <p className="fs-4">
            {nickname}
            <img src={profile} height="30px" className="c-img"></img>
          </p>
        </Col>
      </Row>
      <Row className="mx-4 my-4">
        <Col>
          <Form.Control
            placeholder="영화 제목을 입력하세요"
            className="border rounded-2 text-lg"
            size="lg"
          ></Form.Control>
        </Col>
        <Col />
      </Row>
      <Row className="mx-4 my-4">
        <Col>
          <div className="border rounded-2 border-5 i-box"></div>
        </Col>
        <Col>
          <Row>
            <div>
              <input
                type="file"
                id="imgFile"
                ref={imgInput}
                style={{ display: "none" }}
                onChange={selectedFileHandler}
              ></input>
              <p>{selectedFile}</p>
              <Button onClick={onClickImgInput} text="버튼"></Button>
            </div>
          </Row>
          <Row>
            <div>tets</div>
          </Row>
        </Col>
      </Row>
    </Container>
  );
};

export default ReviewWrite;
