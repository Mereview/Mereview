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
import Select from "react-select";
const ReviewWrite = () => {
  const url = "http://localhost:8080/api";
  const userid = useSelector((state: any) => state.user.id);
  const [selectedImage, setSelectedImage] = useState<string | null>("");
  const [imgName, setImgName] = useState<string>("");
  const [reviewName, setReviewName] = useState<string | null>("");
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
  const [genreList, setGenreList] = useState([]);
  const [genreName, setGenreName] = useState("");
  const [fileData, setFileData] = useState<File>(null);
  const fileDataRef = useRef<File>(null);
  const genreSelectHandler = (event) => {
    const genre = JSON.parse(event.target.value);
    setGenreName(genre.genreName);
    console.log(genre.genreId);
    inputData.current.genreId = genre.genreId;
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
    keywordList.push({
      movieId: inputData.current.movieId,
      name: childRef1.current.getKeyInfo().name,
      weight: childRef1.current.getKeyInfo().weight,
    });
    keywordList.push({
      movieId: inputData.current.movieId,
      name: childRef2.current.getKeyInfo().name,
      weight: childRef2.current.getKeyInfo().weight,
    });
    keywordList.push({
      movieId: inputData.current.movieId,
      name: childRef3.current.getKeyInfo().name,
      weight: childRef3.current.getKeyInfo().weight,
    });
    keywordList.push({
      movieId: inputData.current.movieId,
      name: childRef4.current.getKeyInfo().name,
      weight: childRef4.current.getKeyInfo().weight,
    });
    keywordList.push({
      movieId: inputData.current.movieId,
      name: childRef5.current.getKeyInfo().name,
      weight: childRef5.current.getKeyInfo().weight,
    });
    if (keywordList == null) {
      alert("키워드 목록을 입력해주세요");
      return;
    }
    const reviewContent = contentRef.current.getContent();
    inputData.current.memberId = userid;
    inputData.current.keywordRequests = keywordList;
    inputData.current.content = reviewContent;
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
    console.log(selectedImage);
    // formData.append(
    //   "uploadFile",
    //   selectedImage
    //   // new Blob([JSON.stringify(selectedImage)], {
    //   //   type: "application/json",
    //   // })
    // );
    formData.append(
      "file",
      // fileData
      fileDataRef.current
      // new Blob([JSON.stringify(selectedImage)], {
      //   type: "application/json",
      // })
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
  // const [file, setFile] = useState(null);
  const onDrop = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0];
    if (file) {
      const objectURL = URL.createObjectURL(file);
      setSelectedImage(objectURL);
      setImgName(file.name);
      // setFileData(file);
      fileDataRef.current = file;
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
    if (e.target.value === "NO") {
      setBadBtn(true);
      setGoodBtn(false);
    } else {
      setBadBtn(false);
      setGoodBtn(true);
    }
    let { id, value } = e.target;
    inputData.current[id] = value;
  };
  const movieName = useRef("");
  const [movieList, setMovieList] = useState([]);
  const [selectMovie, setSelectMovie] = useState(null);
  const movieNameHandler = (input) => {
    movieName.current = input;
    if (typingTimeout) {
      clearTimeout(typingTimeout);
    }
    const timeout = setTimeout(() => {
      const encodedKeyword = encodeURIComponent(movieName.current);

      axios
        .get(`http://localhost:8080/api/movies?keyword=${encodedKeyword}`)
        .then((res) => {
          setMovieList(res.data.data);
        })
        .catch(() => {
          console.log("error");
        });
    }, 300);
    setTypingTimeout(timeout);
  };
  const selectMovieHandler = (selected) => {
    setSelectMovie(selected);
    axios
      .get(`http://localhost:8080/api/movies/${selected.value}`)
      .then((res) => {
        const movie = res.data.data;
        inputData.current.movieId = movie.movieContentId;
        setGenreList(movie.genres);
      })
      .catch(() => {
        console.log("error");
      });
  };
  return (
    <Container
      className="mx-auto my-4 vh-100 border border-dark border-5 rounded-5"
      id="reviewForm"
      style={{
        backgroundImage: `url(${selectedImage})`,
        margin: "auto",
        overflowY: "auto",
        overflowX: "hidden",
        flex: "1",
      }}
    >
      <Row className="my-5 mx-4">
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
        {/* <Col className="t-right" lg={4}>
          <p className="fs-4">
            <label htmlFor="profile">{nickname}</label>
            <img
              src={profile}
              height="30px"
              className="c-img"
              id="profile"
            ></img>
          </p>
        </Col> */}
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
      </Row>
      <Row className="mx-4 my-5 align-items-center">
        <Col md={6}>
          <Select
            value={selectMovie}
            options={movieList.map((option) => ({
              value: option.id,
              label: option.title,
            }))}
            inputValue={movieName.current}
            onInputChange={movieNameHandler}
            onChange={selectMovieHandler}
          ></Select>
          <select onChange={genreSelectHandler}>
            <option value={genreName}></option>
            {genreList.map((option) => (
              <option key={option.genreId} value={JSON.stringify(option)}>
                {option.genreName}
              </option>
            ))}
          </select>
        </Col>
        <Col sm={2} />
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
            value={"NO"}
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
            value={"YES"}
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
