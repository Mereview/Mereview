import "../styles/css/ReviewDetailPage.css";
import Top from "../components/reviewDetail/Top";
import Detail from "../components/reviewDetail/Detail";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { uiActions } from "../store/ui-silce";
import uiSlice from "../store/ui-silce";
import { useDispatch, useSelector } from "react-redux";
import { searchReview } from "../api/review";

export interface DummyRev {
  title: string;
  oneLine: string;
  content: string;
  keyword: { [key: string]: string }; // 키와 값의 타입을 모두 문자열로 정의
  memberId: number;
}
export interface DummyMov {
  title: string;
  released_date: string;
  genres: string[];
}
const ReviewDetail = (props: any) => {
  const [review, setReview] = useState(null);
  const { id } = useParams();
  const userId = localStorage.getItem("id");
  const navigate = useNavigate();
  //리뷰불러오기
  const getReviewDetail = () => {
    const data: Object = {
      reviewId: id,
      loginMemberId: userId,
    };
    const success = (res) => setReview(res.data.data);
    const fail = (err) => console.log(err); // 나중에 404 로직추가
    searchReview(data, success, fail);
  };
  // 영화 불러오기
  const getMovieDetail = () => {};

  useEffect(() => {
    getReviewDetail();
  }, []);

  const imgURL = "/test.jpg";
  const style = {
    backgroundImage: `url(${imgURL})`,
    filter: "blur(5px)",
    zIndex: "-1",
  };
  const dummyReview: DummyRev = {
    title: "믿고 보는 톰크루즈",
    oneLine: "볼 수 밖에 없다",
    content:
      "꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰,꼬마붕붕 카체이싱에 절벽 낙하, 열차 격투까지액션의 쾌감과 스릴의 극단. 거기에 헤일리 앳웰, 클레멘티에프의 활약도 눈부시고, 이 난리 끝에 스르륵 등장하는 사이먼 페그는 살짝 얄밉기도.좀 길고, 자잘한 설명이 많은 게 아쉽다만 그래도 톰 크루즈의 장인 정신은 또 2편을 기대하게 한다.",
    keyword: {
      액션: "5",
      연출: "5",
      스토리: "3",
      톰크루즈: "5",
      노잼: "1",
    },
    memberId: 7,
  };
  const dummyMovie: DummyMov = {
    title: "미션 임파서블: 데드레코닝 PART ONE",
    released_date: "2023-07-12",
    genres: ["액션", "모험", "스릴러"],
  };

  return (
    <div className="section">
      <div
        className="blurred"
        style={{
          backgroundImage: `url(${imgURL})`,
          filter: "blur(15px)",
        }}
      ></div>
      <Top review={dummyReview} movie={dummyMovie} />
      <Detail review={dummyReview} movie={dummyMovie} />
      <div className="topbutton"></div>
    </div>
  );
};

export default ReviewDetail;
