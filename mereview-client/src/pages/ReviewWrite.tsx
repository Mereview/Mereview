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
import { useNavigate } from "react-router-dom";

const ReviewWrite = () => {
  const url = `${process.env.REACT_APP_API_URL}`;
  const navigate = useNavigate();
  //유저 정보 받아오기
  const userid = useSelector((state: any) => state.user.user.id);

  //서버로 보낼 리뷰 데이터
  const inputData = useRef<ReviewDataInterface>({
    title: null,
    content: null,
    highlight: null,
    type: null,
    memberId: null,
    movieId: 0,
    genreId: 0,
    keywordRequests: [],
  });

  //리뷰의 배경이미지 정보(선택한 이미지 url, 이미지 이름, 서버로 보낼 이미지 파일)
  const [selectedImage, setSelectedImage] = useState<string | null>("");
  const [imgName, setImgName] = useState<string>("");
  const fileDataRef = useRef<File>(null);

  //배경이미지 첨부 함수
  const onDrop = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0];
    if (file) {
      const objectURL = URL.createObjectURL(file);
      setSelectedImage(objectURL);
      setImgName(file.name);
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

  //리뷰 정보를 넘길 데이터들(리뷰 제목, 한줄평) : useState를 사용하지만 input 내에 값을 넣는용도로만 사용
  const [reviewName, setReviewName] = useState<string | null>("");
  const [oneSentance, setOneSentance] = useState<string | null>("");
  const onChangeHandler = (event) => {
    let { id, value } = event.target;
    inputData.current[id] = value;
  };

  //영화에 대한 평가 버튼
  const [badBtn, setBadBtn] = useState<boolean | null>(false);
  const [goodBtn, setGoodBtn] = useState<boolean | null>(false);

  //영화에 대한 반응을 저장하는 함수
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

  //키워드 정보 저장 변수
  const childRef1 = useRef(null);
  const childRef2 = useRef(null);
  const childRef3 = useRef(null);
  const childRef4 = useRef(null);
  const childRef5 = useRef(null);

  //상세리뷰 저장 변수
  const contentRef = useRef(null);

  //영화이름에 따른 자동완성과 해당 영화의 장르 저장을 위한 변수
  const [typingTimeout, setTypingTimeout] = useState(null); //자동완성을 위한 딜레이용 변수
  const movieName = useRef("");
  const [movieList, setMovieList] = useState([]);
  const [genreList, setGenreList] = useState([]);
  const [selectMovie, setSelectMovie] = useState(null);
  const [selectGenre, setSelectGenre] = useState(null);

  //영화 제목에 따라 자동완성으로 목록을 저장 및 선택한 영화를 inputData에 저장, 해당 영화의 장르 정보를 장르 리스트에 저장
  const movieNameHandler = (input) => {
    movieName.current = input;
    console.log(input);
    console.log(url);
    if (typingTimeout) {
      clearTimeout(typingTimeout);
    }
    const timeout = setTimeout(() => {
      const encodedKeyword = encodeURIComponent(movieName.current);

      axios
        .get(url + `/movies?keyword=${encodedKeyword}`)
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
      .get(url + `/movies/${selected.value}`)
      .then((res) => {
        const movie = res.data.data;
        inputData.current.movieId = movie.id;
        console.log(movie.genres);
        setGenreList(movie.genres);
        console.log(genreList);
      })
      .catch(() => {
        console.log("error");
      });
  };

  //장르 리스트 중 선택한 장르를 inputData에 저장
  const selectGenreHandler = (selected) => {
    setSelectGenre(selected);
    inputData.current.genreId = selected.value;
  };

  //리뷰 정보를 서버에 보내기 위한 함수
  const reviewCreateHandler = () => {
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
    console.log(fileDataRef.current);
    const formData = new FormData();
    formData.append(
      "request",
      new Blob([JSON.stringify(inputData.current)], {
        type: "application/json",
      })
    );
    formData.append("file", fileDataRef.current);
    axios
      .post(url + "/reviews", formData)
      .then(() => {
        console.log("success");
        navigate("/review");
      })
      .catch(() => {
        console.log("fail");
      });
  };

  return (
    <Container
      className="mx-auto my-4 border border-5 border-dark rounded-5 mainForm"
      style={{
        position: "relative",
        height: "auto",
        flex: "1",
        backgroundColor: "rgba(0, 0, 0, 0.2)",
        boxShadow: "2px 2px 4px rgba(0, 0, 0, 0.2)",
      }}
    >
      <div
        id="reviewForm"
        style={{
          backgroundImage: `url(${selectedImage})`,
        }}
      ></div>
      <Row className="top mx-2">
        <Col lg={8}>
          <Row className="mb-3">
            <Form.Control
              placeholder="리뷰 제목을 입력하세요"
              className="rounded-2 text-lg inputBox"
              size="lg"
              id="title"
              onChange={onChangeHandler}
              defaultValue={reviewName}
            ></Form.Control>
          </Row>
          <Row className="justify-content-start my-5">
            <Col lg={6} className="p-0">
              <Select
                value={selectMovie}
                className="inputBox"
                options={movieList.map((option) => ({
                  value: option.id,
                  label: option.title,
                }))}
                inputValue={movieName.current}
                onInputChange={movieNameHandler}
                onChange={selectMovieHandler}
                placeholder="영화 제목을 입력하세요"
              ></Select>
            </Col>
            <Col lg={5}>
              <Select
                value={selectGenre}
                className="inputBox"
                options={genreList.map((option) => ({
                  value: option.genreId,
                  label: option.genreName,
                }))}
                onChange={selectGenreHandler}
                placeholder="장르를 선택하세요"
              ></Select>
            </Col>
          </Row>
          <Row className="my-5">
            <Col lg={11} className="p-0">
              <Form.Control
                placeholder="한줄평을 입력하세요"
                className="rounded-2 text-lg inputBox"
                id="highlight"
                onChange={onChangeHandler}
                defaultValue={oneSentance}
              ></Form.Control>
            </Col>
          </Row>
          <Row>
            <Col lg={11} className="p-0">
              <TextEditor ref={contentRef}></TextEditor>
            </Col>
          </Row>
        </Col>
        <Col lg={4}>
          <Row className="align-items-center justify-content-center mb-3">
            <Col lg={1} />
            <Col lg={5}>
              <Form.Control
                className="text-center border border-5 rounded-2"
                value={imgName}
                readOnly
              ></Form.Control>
            </Col>
            <Col>
              <div {...getRootProps()}>
                <input {...getInputProps()} />
                <Button
                  styles="btn-fourth"
                  btnType="button"
                  text="첨부"
                ></Button>
              </div>
            </Col>
          </Row>
          <Row className="my-5" />
          <Row className="my-5" />
          <Row className="my-5" />
          <Row className="my-5">
            {/* <label htmlFor="keywordBox">키워드</label> */}
            <Col
              className="my-auto text-center border border-5 rounded-2 i-box justify-content-center align-items-center"
              style={{ backgroundColor: "white" }}
              id="keywordBox"
            >
              <KeywordSlider ref={childRef1}></KeywordSlider>
              <Row />
              <KeywordSlider ref={childRef2}></KeywordSlider>
              <Row />
              <KeywordSlider ref={childRef3}></KeywordSlider>
              <Row />
              <KeywordSlider ref={childRef4}></KeywordSlider>
              <Row />
              <KeywordSlider ref={childRef5}></KeywordSlider>
            </Col>
          </Row>
          <Row className="my-5" />
          <Row className="my-5" />
          <Row className="my-5" />
          <Row className="my-5" />
          <Row className="align-items-center justify-content-end">
            {/* <Col lg={3} /> */}
            <Col lg={8}>
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
            <Col lg={4}>
              <Button
                styles="btn-primary"
                text="등록"
                onClick={reviewCreateHandler}
              ></Button>
            </Col>
          </Row>
        </Col>
      </Row>
    </Container>
  );
};

export default ReviewWrite;
