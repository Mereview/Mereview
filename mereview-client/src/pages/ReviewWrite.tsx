import { Form, Container, Row, Col } from "react-bootstrap";
import { useDropzone } from "react-dropzone";
import { Button } from "../components/common";
import React, { useState, useRef, useCallback } from "react";
import "../styles/css/ReviewWrite.css";
import KeywordSlider from "../components/reviewWrite/KeywordSlider";
import TextEditor from "../components/reviewWrite/TextEditor";
import { useSelector } from "react-redux";
import { ReviewDataInterface } from "../components/interface/ReviewWriteInterface";
import axios from "axios";
import writeReview from "../api/review";

const ReviewWrite = () => {
  // const userid = useSelector((state: any) => state.user.id);
  // const userid = "test";
  // const nickname = useSelector((state: any) => state.user.email);
  const userid = "test";
  const nickname = "test";
  const movieList = ["test1", "test2", "test3"];
  const profile = "/logo2.png";
  const [selectedImage, setSelectedImage] = useState<string | null>("");
  const [imgName, setImgName] = useState<string>("");
  const [reviewName, setReviewName] = useState<string | null>("");
  const [movieName, setMovieName] = useState<string | null>("");
  const [autoCompleteData, setAutoCompleteData] = useState([]);
  const [oneSentance, setOneSentance] = useState<string | null>("");
  const [feedback, setFeedback] = useState<number | null>(0);
  const [badBtn, setBadBtn] = useState<boolean | null>(false);
  const [goodBtn, setGoodBtn] = useState<boolean | null>(false);
  const [inputData, setInputData] = useState<ReviewDataInterface>({
    title: null,
    content: "test",
    highlight: null,
    type: "LIKE",
    memberId: 1,
    movieId: 1,
    genreId: 0,
    keywordRequests: null,
  });
  const childRef1 = useRef(null);
  const childRef2 = useRef(null);
  const childRef3 = useRef(null);
  const childRef4 = useRef(null);
  const childRef5 = useRef(null);
  const contentRef = useRef(null);

  const handleBtnClick = (event) => {
    event.preventDefault();
    const keywordList = [];
    keywordList.push(childRef1.current.getKeyInfo());
    keywordList.push(childRef2.current.getKeyInfo());
    keywordList.push(childRef3.current.getKeyInfo());
    keywordList.push(childRef4.current.getKeyInfo());
    keywordList.push(childRef5.current.getKeyInfo());

    setInputData((prevInputData) => ({
      ...prevInputData,
      keywordRequests: keywordList,
    }));
    console.log(inputData);

    const formData = new FormData();
    formData.append(
      "request",
      new Blob([JSON.stringify(inputData)], { type: "application/json" })
    );

    axios
      .post("http://localhost:8080/api/reviews", formData)
      .then(() => {
        console.log("success");
      })
      .catch(() => {
        console.log("fail");
      });
  };
  const onChangeHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    let { id, value } = event.target;
    setInputData((prevInputData) => ({
      ...prevInputData,
      [id]: value,
    }));
  };
  const onDrop = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0];
    if (file) {
      const objectURL = URL.createObjectURL(file);
      setSelectedImage(objectURL);
      setImgName(file.name);
    }
  }, []);

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
    setFeedback(e.target.Value);
  };
  return (
    <Form
      className="mx-auto my-4 vh-100 border border-dark border-5 rounded-5"
      id="reviewForm"
      onSubmit={handleBtnClick}
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
            id="title"
            onChange={onChangeHandler}
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
            onChange={onChangeHandler}
            id="movie"
            defaultValue={movieName}
            list="autoList"
          ></Form.Control>
          <datalist id="autoList">
            autoCompleteData.length != 0 ?
            {autoCompleteData.map((item) => (
              <option key={item} value={item} />
            ))}{" "}
            :
            <option value={"데이터 없음"} />
          </datalist>
        </Col>
      </Row>
      <Row className="mx-4 my-4 align-items-center">
        <Col sm={6}>
          <Form.Control
            placeholder="한줄평을 입력하세요"
            className="border rounded-2 text-lg"
            id="highlight"
            onChange={onChangeHandler}
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
            <Button styles="btn-fourth" btnType="button" text="첨부"></Button>
          </div>
        </Col>
        <Col />
      </Row>
      <Row className="mx-4">
        <Col md={6}>
          <TextEditor ref={contentRef}></TextEditor>
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
            btnType="submit"
            text="등록"
            // onClick={handleBtnClick}
          ></Button>
        </Col>
      </Row>
    </Form>
  );
};

export default ReviewWrite;
