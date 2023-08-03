import { Form, Container, Row, Col } from "react-bootstrap";
import { useDropzone } from "react-dropzone";
import { Button } from "../components/common";
import React, { useState, useRef, useCallback } from "react";
import "../styles/css/ReviewWrite.css";
import KeywordSlider from "../components/reviewWrite/KeywordSlider";
import TextEditor from "../components/reviewWrite/TextEditor";
const ReviewWrite = () => {
  const nickname = "닉네임";
  const profile = "/logo2.png";
  const [selectedImage, setSelectedImage] = useState<string | null>("");
  const [imgName, setImgName] = useState<string>("");
  const [reviewName, setReviewName] = useState<string | null>("");
  const [movieName, setMovieName] = useState<string | null>("");
  const [oneSentance, setOneSentance] = useState<string | null>("");
  const [feedback, setFeedback] = useState<number | null>(0);
  const [badBtn, setBadBtn] = useState<boolean | null>(false);
  const [goodBtn, setGoodBtn] = useState<boolean | null>(false);
  const childRef1 = useRef(null);
  const childRef2 = useRef(null);
  const childRef3 = useRef(null);
  const childRef4 = useRef(null);
  const childRef5 = useRef(null);
  const handleBtnClick = () => {
    if (childRef1.current) {
      const valueFromChild1 = childRef1.current.getKeyInfo();
      console.log("Value from Child: ", valueFromChild1);
    }
    if (childRef2.current) {
      const valueFromChild2 = childRef2.current.getKeyInfo();
      console.log("Value from Child: ", valueFromChild2);
    }
    if (childRef3.current) {
      const valueFromChild3 = childRef3.current.getKeyInfo();
      console.log("Value from Child: ", valueFromChild3);
    }
    if (childRef4.current) {
      const valueFromChild4 = childRef4.current.getKeyInfo();
      console.log("Value from Child: ", valueFromChild4);
    }
    if (childRef5.current) {
      const valueFromChild5 = childRef5.current.getKeyInfo();
      console.log("Value from Child: ", valueFromChild5);
    }
  };
  const onDrop = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0];
    if (file) {
      const objectURL = URL.createObjectURL(file);
      setSelectedImage(objectURL);
      setImgName(file.name);
    }
  }, []);
  const reviewNameHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setReviewName(e.target.value);
  };
  const movieNameHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setMovieName(e.target.value);
  };
  const oneSentanceNameHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setOneSentance(e.target.value);
  };
  const { getRootProps, getInputProps } = useDropzone({
    onDrop,
    accept: {
      "image/*": [".jpeg", ".jpg", ".png", ".gif"],
    },
    maxFiles: 1,
  });
  const feedbackHandler = (e) => {
    if (e.target.id === "badBtn") {
      setBadBtn(true);
      setGoodBtn(false);
    } else {
      setBadBtn(false);
      setGoodBtn(true);
    }
    // console.log(goodBtn + " " + badBtn);
    setFeedback(e.target.Value);
  };
  return (
    <Container
      className="mx-auto my-4 vh-100 border border-dark border-5 rounded-5"
      style={{
        backgroundImage: `url(${selectedImage})`,
        margin: "auto",
      }}
    >
      <Row className="mt-5 mx-4">
        <Col lg={8}>
          <Form.Control
            placeholder="리뷰 제목을 입력하세요"
            className="border rounded-2 text-lg"
            size="lg"
            onChange={reviewNameHandler}
            defaultValue={reviewName}
          ></Form.Control>
        </Col>
        <Col className="t-right" lg={4}>
          <p className="fs-4">
            <label htmlFor="profile">{nickname}</label>
            <img
              src={profile}
              height="30px"
              className="c-img"
              id="profile"
            ></img>
          </p>
        </Col>
      </Row>
      <Row className="mx-4 align-items-center">
        <Col md={6}>
          <Form.Control
            placeholder="영화 제목을 입력하세요"
            className="border rounded-2 text-lg"
            onChange={movieNameHandler}
            defaultValue={movieName}
          ></Form.Control>
        </Col>
      </Row>
      <Row className="mx-4 my-4 align-items-center">
        <Col sm={6}>
          <Form.Control
            placeholder="한줄평을 입력하세요"
            className="border rounded-2 text-lg"
            onChange={oneSentanceNameHandler}
            defaultValue={oneSentance}
          ></Form.Control>
        </Col>
        <Col sm={2} />
        <Col sm={2}>
          <Form.Control
            className="text-center border border-5 rounded-2"
            value={imgName}
            readOnly
          ></Form.Control>
        </Col>
        <Col sm={1}>
          <div {...getRootProps()}>
            <input {...getInputProps()} />
            <Button styles="btn-fourth" text="첨부"></Button>
          </div>
        </Col>
        <Col />
      </Row>
      <Row className="mx-4">
        <Col md={6}>
          <TextEditor
          // className="border rounded-2 border-5 i-box form-control"
          // style={{ resize: "none" }}
          ></TextEditor>
        </Col>
        <Col md={2} />
        <Col
          className="my-auto text-center border border-5 rounded-2 i-box"
          style={{ backgroundColor: "white" }}
        >
          <KeywordSlider ref={childRef1}></KeywordSlider>
          <KeywordSlider ref={childRef2}></KeywordSlider>
          <KeywordSlider ref={childRef3}></KeywordSlider>
          <KeywordSlider ref={childRef4}></KeywordSlider>
          <KeywordSlider ref={childRef5}></KeywordSlider>
        </Col>
      </Row>
      <Row lg={12} className="mt-3 align-items-center">
        <Col lg={8} />
        <Col lg={2}>
          <button
            id="badBtn"
            className="bg-danger feed-btn mx-1 my-1"
            style={{
              backgroundImage: "url(/thumbDown.png)",
              boxShadow: badBtn ? "2px 2px 4px rgba(0, 0, 0, 0.5)" : "",
              transform: badBtn ? "scale(0.95)" : "",
            }}
            onClick={feedbackHandler}
            value={-1}
          ></button>
          <button
            id="goodBtn"
            className="bg-primary feed-btn mx-1 my-1"
            style={{
              backgroundImage: "url(/thumbUp.png)",
              boxShadow: goodBtn ? "2px 2px 4px rgba(0, 0, 0, 0.5)" : "",
              transform: goodBtn ? "scale(0.95)" : "",
            }}
            onClick={feedbackHandler}
            value={1}
          ></button>
        </Col>
        <Col>
          <Button
            styles="btn-primary"
            onClick={handleBtnClick}
            // onClick={() => {
            //   console.log(goodBtn + " " + badBtn);
            // }}
            text="등록"
          ></Button>
        </Col>
      </Row>
    </Container>
  );
};

export default ReviewWrite;
