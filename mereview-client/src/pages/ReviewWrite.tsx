import { Form, Container, Row, Col } from "react-bootstrap";
import { useDropzone } from "react-dropzone";
import { Button } from "../components/common";
import React, { useState, useRef, useCallback } from "react";
import "../styles/css/ReviewWrite.css";
import Slide from "../components/common/Slide";
import { Input } from "../components/common";

const ReviewWrite = () => {
  const [nickname, setNickname] = useState("닉네임");
  const [profile, setProfile] = useState("/logo2.png");
  const [selectedImage, setSelectedImage] = useState<string | null>(null);
  const [imgName, setImgName] = useState<string | null>(null);
  const [reviewName, setReviewName] = useState<string | null>(null);
  const onDrop = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0];
    if (file) {
      const objectURL = URL.createObjectURL(file);
      setSelectedImage(objectURL);
      setImgName(file.name);
    }
  }, []);
  const reviewNameHandler = (e) => {
    setReviewName(e.target.value);
  };
  const { getRootProps, getInputProps } = useDropzone({
    onDrop,
    accept: {
      "image/*": [".jpeg", ".jpg", ".png", ".gif"],
    },
    maxFiles: 1,
  });
  return (
    <Container
      className="mx-auto my-4 vh-100 border border-dark border-5 rounded-5"
      style={{
        backgroundImage: `url(${selectedImage})`,
        margin: "auto",
      }}
    >
      <Row className="mt-5 mx-4">
        <Col md={6}>
          <Input
            id="reviewName"
            placeholder="리뷰 제목을 입력하세요"
            styles="input-box"
            onChange={reviewNameHandler}
            type="text"
          ></Input>
          {/* <Form.Control
            placeholder="영화 제목을 입력하세요"
            className="border rounded-2 text-lg"
            size="lg"
          ></Form.Control> */}
        </Col>
        <Col className="t-right me-4">
          <p className="fs-4">
            {nickname}
            <img src={profile} height="30px" className="c-img"></img>
          </p>
        </Col>
      </Row>
      <Row className="mx-4">
        <Col md={6}>
          <Input
            id="reviewName"
            placeholder="영화 제목을 입력하세요"
            styles="input-box"
            onChange={reviewNameHandler}
            type="text"
          ></Input>
          {/* <Form.Control
            placeholder="영화 제목을 입력하세요"
            className="border rounded-2 text-lg"
          ></Form.Control> */}
        </Col>
        <Col md={2} />
        <Col md={2}>
          <Col
            className="text-center border border-5 rounded-2"
            style={{ overflow: "hidden", height: "100%" }}
          >
            {imgName}
          </Col>
        </Col>
        <Col md={1}>
          <div {...getRootProps()}>
            <input {...getInputProps()} />
            <Button styles="btn-primary" text="첨부"></Button>
          </div>
        </Col>
      </Row>
      <Row className="mx-4 my-4">
        <Col>
          <Input
            id="reviewName"
            placeholder="한줄평을 입력하세요"
            styles="input-box"
            onChange={reviewNameHandler}
            type="text"
          ></Input>
          {/* <Form.Control
            placeholder="영화 제목을 입력하세요"
            className="border rounded-2 text-lg"
            size="sm"
          ></Form.Control> */}
        </Col>

        <Col />
      </Row>
      <Row className="mx-4 my-4">
        <Col md={6}>
          <textarea
            className="border rounded-2 border-5 i-box form-control"
            style={{ resize: "none", height: "100%" }}
          >
            {/* {selectedImage ? (
              <img src={selectedImage} className="img-preview" />
            ) : (
              <img src={"/defaultProfile.png"} className="img-preview" />
            )} */}
          </textarea>
        </Col>
        <Col md={2} />
        <Col>
          <Row>
            {/* <Col
              md={6}
              className="my-auto text-center border border-5 rounded-2"
            ></Col>
            <Col md={3} className="t-right">
              <div {...getRootProps()}>
                <input {...getInputProps()} />
                <Button styles="btn-primary" text="첨부"></Button>
              </div>
            </Col> */}
          </Row>
          <Row>
            <Col
              className="my-auto text-center border border-5 rounded-2"
              style={{ backgroundColor: "white" }}
            >
              <p>키워드</p>
              <Slide />
              <Slide />
              <Slide />
              <Slide />
              <Slide />
            </Col>
          </Row>
        </Col>
      </Row>
    </Container>
  );
};

export default ReviewWrite;
