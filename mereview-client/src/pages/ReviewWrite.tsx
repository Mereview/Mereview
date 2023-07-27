import { Form, Container, Row, Col } from "react-bootstrap";
import { useDropzone } from "react-dropzone";
import { Button } from "../components/common";
import Slider from "@material-ui/core/Slider/Slider";
import React, { useState, useRef, useCallback } from "react";
import "../styles/css/ReviewWrite.css";

const ReviewWrite = () => {
  const [nickname, setNickname] = useState("닉네임");
  const [profile, setProfile] = useState("/logo2.png");
  const [selectedImage, setSelectedImage] = useState<string | null>(null);
  const [blurPoint, setBlurPoint] = useState<number>(0);
  const blurPointHandler = (
    event: React.ChangeEvent<{}>,
    newValue: number | number[]
  ) => {
    setBlurPoint(newValue as number);
  };
  const onDrop = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0];
    if (file) {
      const objectURL = URL.createObjectURL(file);
      setSelectedImage(objectURL);
    }
  }, []);
  const { getRootProps, getInputProps } = useDropzone({
    onDrop,
    accept: {
      "image/*": [".jpeg", ".jpg", ".png", ".gif"],
    },
    maxFiles: 1,
  });
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
          <div className="border rounded-2 border-5 i-box">
            {selectedImage ? (
              <img
                src={selectedImage}
                className="img-preview"
                style={{ filter: `blur(${blurPoint / 50}px)` }}
              />
            ) : (
              <img
                src={"/defaultProfile.png"}
                className="img-preview"
                style={{ filter: `blur(${blurPoint / 50}px)` }}
              />
            )}
          </div>
        </Col>
        <Col>
          <Row>
            <Col
              md={9}
              className="my-auto text-center border border-5 rounded-2"
            >
              {/* {selectedImage} */}
            </Col>
            <Col md={3} className="t-right">
              <div {...getRootProps()}>
                <input {...getInputProps()} />
                <Button styles="btn-primary" text="첨부"></Button>
              </div>
            </Col>
          </Row>
          <Row>
            <Col className="my-auto text-center border border-5 rounded-2">
              <p>Blur Guage : {blurPoint}</p>
              <Slider value={blurPoint} onChange={blurPointHandler} />
            </Col>
          </Row>
        </Col>
      </Row>
    </Container>
  );
};

export default ReviewWrite;
