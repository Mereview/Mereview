import { Form, Container, Row, Col } from "react-bootstrap";
import { useDropzone } from "react-dropzone";
import { Button } from "../components/common";
import React, { useState, useRef, useCallback, useEffect } from "react";
import "../styles/css/ReviewWrite.css";
import KeywordSlider from "../components/reviewWrite/KeywordSlider";
import TextEditor from "../components/reviewWrite/TextEditor";
import { useSelector } from "react-redux";
import { ReviewDataInterface } from "../components/interface/ReviewWriteInterface";
import axios from "axios";
import MovieList from "../components/MovieList";
const ReviewWrite = () => {
  const url = "http://localhost:8080/api";
  const userid = useSelector((state: any) => state.user.id);
  const nickname = useSelector((state: any) => state.user.nickname);
  const profile = useSelector((state: any) => state.user.profile_URL);
  const [selectedImage, setSelectedImage] = useState<string | null>("");
  const [imgName, setImgName] = useState<string>("");
  const [reviewName, setReviewName] = useState<string | null>("");
  const movieName = useRef("");
  const autoCompleteData = useRef([]);
  const [movieList, setMovieList] = useState([]);
  const [oneSentance, setOneSentance] = useState<string | null>("");
  const [badBtn, setBadBtn] = useState<boolean | null>(false);
  const [goodBtn, setGoodBtn] = useState<boolean | null>(false);
  const inputData = useRef<ReviewDataInterface>({
    title: null,
    content: null,
    highlight: null,
    type: null,
    memberId: null,
    movieId: 1,
    genreId: 0,
    keywordRequests: [],
  });
  const childRef1 = useRef(null);
  const childRef2 = useRef(null);
  const childRef3 = useRef(null);
  const childRef4 = useRef(null);
  const childRef5 = useRef(null);
  const contentRef = useRef(null);
  const [typingTimeout, setTypingTimeout] = useState(null);
  const onMovieNameHandler = (event) => {
    const searchValue = event.target.value;
    if (typingTimeout) {
      clearTimeout(typingTimeout);
    }
    const timeout = setTimeout(() => {
      const encodedKeyword = encodeURIComponent(searchValue);

      axios
        .get(`http://localhost:8080/api/movies?keyword=${encodedKeyword}`)
        .then((res) => {
          console.log(res.data.data);
          // autoCompleteData.current = res.data.data;
          setMovieList(res.data.data);
        })
        .catch(() => {
          console.log("error");
        });
    }, 300);
    setTypingTimeout(timeout);
  };
  const movieSelectHandler = (event) => {
    movieName.current = event.target.value;
    const encodedKeyword = encodeURIComponent(movieName.current);
    axios
      .get(`http://localhost:8080/api/movies?keyword=${encodedKeyword}`)
      .then((res) => {
        // console.log(res.data.data);
        const movie = res.data.data;
        console.log(movie);
        inputData.current.movieId = movie[0].movieContentId;
        inputData.current.genreId = movie[0].genres;
        console.log(inputData.current);
      });
  };
  const handleBtnClick = () => {
    if (inputData.current.title == null) {
      alert("제목을 입력해주세요");
      return;
    }
    if (inputData.current.movieId == null) {
      alert("영화 제목을 입려해주세요");
      return;
    }
    if (inputData.current.highlight == null) {
      alert("한줄평을 입력해주세요");
      return;
    }
    const keywordList = [];
    keywordList.push(childRef1.current.getKeyInfo());
    keywordList.push(childRef2.current.getKeyInfo());
    keywordList.push(childRef3.current.getKeyInfo());
    keywordList.push(childRef4.current.getKeyInfo());
    keywordList.push(childRef5.current.getKeyInfo());
    if (keywordList == null) {
      alert("키워드 목록을 입력해주세요");
      return;
    }
    // console.log(contentRef.current.getContent());
    const reviewContent = contentRef.current.getContent();
    inputData.current.memberId = userid;
    inputData.current.keywordRequests = keywordList;
    inputData.current.content = reviewContent;
    // console.log(reviewContent);
    if (inputData.current.content == null) {
      alert("리뷰 내용을 입력해주세요");
      return;
    }
    console.log(inputData.current);
    const formData = new FormData();
    formData.append(
      "request",
      new Blob([JSON.stringify(inputData.current)], {
        type: "application/json",
      })
    );
    formData.append(
      "uploadFile",
      new Blob([JSON.stringify(selectedImage)], {
        type: "application/json",
      })
    );
    axios
      .post(url + "/reviews", formData)
      .then(() => {
        console.log("success");
      })
      .catch(() => {
        console.log("fail");
      });
  };
  const onChangeHandler = (event) => {
    let { id, value } = event.target;
    inputData.current[id] = value;
    console.log(id + " " + inputData[id]);
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
    if (e.target.value === "BAD") {
      setBadBtn(true);
      setGoodBtn(false);
    } else {
      setBadBtn(false);
      setGoodBtn(true);
    }
    let { id, value } = e.target;
    inputData.current[id] = value;
  };
  return (
    <Container
      className="mx-auto my-4 vh-100 border border-dark border-5 rounded-5"
      id="reviewForm"
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
            onChange={onMovieNameHandler}
            id="movie"
          ></Form.Control>
          <select value={movieName.current} onChange={movieSelectHandler}>
            {movieList.map((option) => (
              <option key={option.title} value={option.title}>
                {option.title}
              </option>
            ))}
          </select>
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
            id="type"
            className="bg-danger feed-btn mx-1 my-1"
            type="button"
            style={{
              backgroundImage: "url(/thumbDown.png)",
              boxShadow: badBtn ? "2px 2px 4px rgba(0, 0, 0, 0.5)" : "",
              transform: badBtn ? "scale(0.95)" : "",
            }}
            onClick={feedbackHandler}
            value={"BAD"}
          ></button>
          <button
            id="type"
            className="bg-primary feed-btn mx-1 my-1"
            type="button"
            style={{
              backgroundImage: "url(/thumbUp.png)",
              boxShadow: goodBtn ? "2px 2px 4px rgba(0, 0, 0, 0.5)" : "",
              transform: goodBtn ? "scale(0.95)" : "",
            }}
            onClick={feedbackHandler}
            value={"LIKE"}
          ></button>
        </Col>
        <Col>
          <Button
            styles="btn-primary"
            text="등록"
            onClick={handleBtnClick}
          ></Button>
        </Col>
      </Row>
    </Container>
  );
};

export default ReviewWrite;
