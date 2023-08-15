import { Form, Container, Row, Col } from "react-bootstrap";
import { useDropzone } from "react-dropzone";
import { Button } from "../components/common";
import React, { useState, useRef, useCallback, useEffect } from "react";
import "../styles/css/EditReview.css";
import KeywordSlider from "../components/reviewWrite/KeywordSlider";
import TextEditor from "../components/reviewWrite/TextEditor";
import { useSelector } from "react-redux";
import { ReviewDataInterface } from "../components/interface/ReviewDataInterface";
import axios from "axios";
import Select from "react-select";
import { useNavigate } from "react-router-dom";
import { positions } from "@mui/system";
import { useParams } from "react-router-dom";
import { searchReview } from "../api/review";
import { FormControlLabel, Switch } from "@mui/material";
const ReviewWrite = () => {
  const url = `${process.env.REACT_APP_API_URL}`;
  const navigate = useNavigate();
  //유저 정보 받아오기
  const userid = useSelector((state: any) => state.user.user.id);
  const { id } = useParams();
  const reviewId = Number(id);
  const data = { loginMemberId: userid, reviewId: reviewId };
  // const [reviewData, setReviewData] = useState(null);
  useEffect(() => {
    const getEditRiview = () => {
      console.log(userid, reviewId);
      const data = { loginMemberId: userid, reviewId: reviewId };
      searchReview(
        data,
        (res) => {
          const review = res.data.data;
          console.log(review);
          const image = review.backgroundImage;
          if (image !== null) {
            setImgName(image.fileName);
            setSelectedImage(`${url}/image/download/backgrounds/${image.id}`);
            fileDataRef.current = new File([selectedImage], imgName);
          }
          setReviewName(review.reviewTitle);
          setOneSentance(review.reviewHighlight);
          movieName.current = review.movieTitle;
          genreName.current = review.genre.genreName;
          keywordId1.current = review.keywords[0].keywordId;
          keywordId2.current = review.keywords[1].keywordId;
          keywordId3.current = review.keywords[2].keywordId;
          keywordId4.current = review.keywords[3].keywordId;
          keywordId5.current = review.keywords[4].keywordId;
          childRef1.current.setKey(review.keywords[0]);
          childRef2.current.setKey(review.keywords[1]);
          childRef3.current.setKey(review.keywords[2]);
          childRef4.current.setKey(review.keywords[3]);
          childRef5.current.setKey(review.keywords[4]);
          contentRef.current.setCont(review.reviewContent);
          if (review.movieEvaluatedType === "YES") {
            setGoodBtn(true);
            setBadBtn(false);
            inputData.current.type = "YES";
          } else {
            setGoodBtn(false);
            setBadBtn(true);
            inputData.current.type = "NO";
          }
          setSelectMovie({ value: review.movieId, label: review.movieTitle });
          setSelectGenre({
            value: review.genre.genreId,
            label: review.genre.genreName,
          });
          inputData.current.title = review.reviewTitle;
          inputData.current.highlight = review.reviewHighlight;
          inputData.current.movieId = review.movieId;
          inputData.current.genreId = review.genreId;
        },
        (err) => {
          console.log("err: ", err);
        }
      );
    };
    getEditRiview();
  }, []);
  //리뷰 정보
  const [reviewName, setReviewName] = useState<string | null>();
  const [oneSentance, setOneSentance] = useState<string | null>("");
  //영화에 대한 평가 버튼
  const [badBtn, setBadBtn] = useState<boolean | null>(false);
  const [goodBtn, setGoodBtn] = useState<boolean | null>(false);
  //키워드 정보 저장 변수
  const childRef1 = useRef(null);
  const childRef2 = useRef(null);
  const childRef3 = useRef(null);
  const childRef4 = useRef(null);
  const childRef5 = useRef(null);
  const keywordId1 = useRef(null);
  const keywordId2 = useRef(null);
  const keywordId3 = useRef(null);
  const keywordId4 = useRef(null);
  const keywordId5 = useRef(null);
  //리뷰의 배경이미지 정보(선택한 이미지 url, 이미지 이름, 서버로 보낼 이미지 파일)
  const [selectedImage, setSelectedImage] = useState<string | null>("");
  const [imgName, setImgName] = useState<string>("");
  const fileDataRef = useRef<File>(null);
  const [changeImg, setChangeImg] = useState<boolean>(false);
  //영화이름에 따른 자동완성과 해당 영화의 장르 저장을 위한 변수
  const [typingTimeout, setTypingTimeout] = useState(null); //자동완성을 위한 딜레이용 변수
  const movieName = useRef("");
  const genreName = useRef("");
  const [movieList, setMovieList] = useState([]);
  const [genreList, setGenreList] = useState([]);
  const [selectMovie, setSelectMovie] = useState(null);
  const [selectGenre, setSelectGenre] = useState(null);
  //상세리뷰 저장 변수
  const contentRef = useRef(null);

  //배경이미지
  const frameImg = "/filmframe.png";

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

  //이미지 변경여부 조정 함수
  const changeImgHandler = () => {
    setChangeImg(!changeImg);
    if (changeImg === true) {
    }
  };

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

  const onChangeHandler = (event) => {
    let { id, value } = event.target;
    inputData.current[id] = value;
  };

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

  //영화 제목에 따라 자동완성으로 목록을 저장 및 선택한 영화를 inputData에 저장, 해당 영화의 장르 정보를 장르 리스트에 저장
  const movieNameHandler = (input) => {
    movieName.current = input;
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
    console.log(selected.value);
    setSelectGenre(selected);
    inputData.current.genreId = selected.value;
  };

  //리뷰 정보를 서버에 보내기 위한 함수
  const reviewCreateHandler = () => {
    if (inputData.current.title === null) {
      alert("제목을 입력해주세요");
      return;
    }
    if (inputData.current.movieId === null) {
      alert("영화 제목을 입려해주세요");
      return;
    }
    if (inputData.current.highlight === null) {
      alert("한줄평을 입력해주세요");
      return;
    }
    const keywordList = [];
    keywordList.push({
      keywordId: keywordId1.current,
      name: childRef1.current.getKeyInfo().name,
      weight: childRef1.current.getKeyInfo().weight,
    });
    keywordList.push({
      keywordId: keywordId2.current,
      name: childRef2.current.getKeyInfo().name,
      weight: childRef2.current.getKeyInfo().weight,
    });
    keywordList.push({
      keywordId: keywordId3.current,
      name: childRef3.current.getKeyInfo().name,
      weight: childRef3.current.getKeyInfo().weight,
    });
    keywordList.push({
      keywordId: keywordId4.current,
      name: childRef4.current.getKeyInfo().name,
      weight: childRef4.current.getKeyInfo().weight,
    });
    keywordList.push({
      keywordId: keywordId5.current,
      name: childRef5.current.getKeyInfo().name,
      weight: childRef5.current.getKeyInfo().weight,
    });
    console.log(keywordList);
    if (keywordList == null) {
      alert("키워드 목록을 입력해주세요");
      return;
    }
    const reviewContent = contentRef.current.getContent();
    console.log(reviewContent);
    inputData.current.memberId = userid;
    inputData.current.keywordRequests = keywordList;
    inputData.current.content = reviewContent;
    if (inputData.current.content == null) {
      alert("리뷰 내용을 입력해주세요");
      return;
    }
    const formData = new FormData();
    // formData.append(
    //   "reviewId",
    //   new Blob([JSON.stringify(reviewId)], {
    //     type: "application/json",
    //   })
    // );
    console.log(inputData.current.title);
    console.log(inputData.current.highlight);
    console.log(inputData.current.content);
    console.log(inputData.current.keywordRequests);
    console.log(inputData.current.type);
    formData.append(
      "request",
      new Blob(
        [
          JSON.stringify({
            title: inputData.current.title,
            highlight: inputData.current.highlight,
            content: inputData.current.content,
            keywordRequests: inputData.current.keywordRequests,
            type: inputData.current.type,
            updateBackGround: changeImg,
          }),
        ],
        {
          type: "application/json",
        }
      )
    );
    console.log(fileDataRef.current);
    if (fileDataRef.current != null) {
      formData.append("file", fileDataRef.current);
    }
    axios
      .put(url + `/reviews/${reviewId}`, formData)
      .then(() => {
        console.log("success");
        navigate("/review");
      })
      .catch(() => {
        console.log("fail");
      });
  };

  return (
    <div
      style={{
        // backgroundColor: "rgba(0, 0, 0, 0.3)",
        // backgroundImage: `url(${topImg})`,
        position: "absolute",
        backgroundSize: "fill",
        height: "auto",
        width: "100vw",
      }}
      className="align-items-center"
    >
      <img
        src={frameImg}
        style={{
          width: "100%",
          height: "120px",
          objectFit: "fill",
        }}
      />
      {/* <div
        style={{
          width: "100%",
          backgroundImage: `url(${topImg})`,
        }}
      ></div> */}
      <Container
        // className="mx-auto border border-5 border-dark rounded-5"
        id="reviewContainer"
        className="mx-auto"
        style={{
          position: "relative",
          height: "auto",
          flex: "1",
          backgroundColor: `${
            selectedImage != "" ? "rgba(0, 0, 0, 0.5)" : "rgb(255, 255, 255)"
          }`,
          // backgroundColor: "rgba(0, 0, 0, 0.5)",
          // boxShadow: "2px 2px 4px rgba(0, 0, 0, 0.2)",
        }}
      >
        <div
          id="reviewForm"
          style={{
            backgroundImage: `url(${selectedImage})`,
          }}
        ></div>
        <Row />
        <Row className="top mx-2">
          <Col
            md-lg={7}
            className="frame me-4 p-4 rounded-3 d-flex flex-column"
          >
            <Row className="mb-3">
              <Form.Control
                placeholder="리뷰 제목을 입력하세요"
                className="rounded-2 text-lg inputBox"
                size="lg"
                id="title"
                onChange={onChangeHandler}
                defaultValue={reviewName}
                style={{ width: "100%" }}
              ></Form.Control>
            </Row>
            <Row className="justify-content-start my-5">
              <Col lg={6} className="p-0">
                <Form.Control value={movieName.current} readOnly></Form.Control>
                {/* <Select
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
                ></Select> */}
              </Col>
              <Col lg={6}>
                <Form.Control value={genreName.current} readOnly></Form.Control>
                {/* <Select
                  value={selectGenre}
                  className="inputBox"
                  options={genreList.map((option) => ({
                    value: option.genreId,
                    label: option.genreName,
                  }))}
                  onChange={selectGenreHandler}
                  placeholder="장르를 선택하세요"
                ></Select> */}
              </Col>
            </Row>
            <Row className="mt-5">
              <Col lg={11} className="p-1">
                <Form.Control
                  placeholder="한줄평을 입력하세요"
                  className="rounded-2 text-lg inputBox"
                  id="highlight"
                  onChange={onChangeHandler}
                  defaultValue={oneSentance}
                ></Form.Control>
              </Col>
            </Row>
          </Col>
          <Col lg={4} className="ms-5 p-4 rounded-2 frame">
            <Row className="align-items-center justify-content-center mb-3">
              <Col md-lg={6} className="p-0">
                <Form.Control
                  className="text-center border border-5 rounded-2"
                  value={imgName}
                  readOnly
                  placeholder="배경 이미지를 첨부해주세요"
                ></Form.Control>
              </Col>
              <Col lg={4}>
                <div {...getRootProps()}>
                  <input {...getInputProps()} />
                  <Button
                    styles="btn-fourth"
                    btnType="button"
                    text="첨부"
                    disabled={!changeImg}
                  ></Button>
                </div>
              </Col>
              <FormControlLabel
                className="i-box"
                control={
                  <Switch checked={changeImg} onChange={changeImgHandler} />
                }
                label="수정하기"
                labelPlacement="end"
              />
            </Row>
            <Row className="">
              <Col
                className="my-auto text-center border border-5 rounded-2 i-box justify-content-center align-items-center"
                style={{ backgroundColor: "white" }}
                id="keywordBox"
              >
                <KeywordSlider ref={childRef1}></KeywordSlider>
                <KeywordSlider ref={childRef2}></KeywordSlider>
                <KeywordSlider ref={childRef3}></KeywordSlider>
                <KeywordSlider ref={childRef4}></KeywordSlider>
                <KeywordSlider ref={childRef5}></KeywordSlider>
              </Col>
            </Row>
          </Col>
        </Row>
        <Row className="mx-2 my-4">
          <TextEditor ref={contentRef}></TextEditor>
        </Row>
        <Row className="align-items-center justify-content-end mb-3">
          <Col lg={7} />
          <Col lg={3}>
            <button
              id="type"
              className="bg-danger feed-btn mx-1 mt-1"
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
              className="bg-primary feed-btn ms-1 me-5 my-1"
              type="button"
              style={{
                backgroundImage: "url(/thumbUp.png)",
                boxShadow: goodBtn ? "2px 2px 4px rgba(0, 0, 0, 0.5)" : "",
                transform: goodBtn ? "scale(0.95)" : "",
              }}
              onClick={feedbackHandler}
              value={"YES"}
            ></button>
            <Button
              styles="btn-primary"
              text="등록"
              onClick={reviewCreateHandler}
            ></Button>
          </Col>
        </Row>
        <Row />
      </Container>
      <img
        src={frameImg}
        style={{
          width: "100%",
          height: "120px",
          objectFit: "fill",
        }}
      />
    </div>
  );
};

export default ReviewWrite;
