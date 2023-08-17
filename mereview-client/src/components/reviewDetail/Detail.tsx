import "../../styles/css/Detail.css";
import ReviewCard from "../ReviewCard";
import { Button } from "../common";
import { useState, useEffect } from "react";
import {
  createReviewComment,
  searchReview,
  deleteReview,
  evaluationsReview,
  searchReviews,
} from "../../api/review";
import Comments from "./Comments";
import { useNavigate } from "react-router-dom";
import { FormControlLabel, Switch } from "@mui/material";
import Loading from "../common/Loading";
interface evInterface {
  FUN: number;
  USEFUL: number;
  BAD: number;
}

const Detail = ({ review, setReview }: any) => {
  const navigate = useNavigate();
  // 유저아이디 받아오기
  const userId = localStorage.getItem("id");
  // 댓글 관련
  const [isFetched, setFetched] = useState(false);
  const [comments, setComments] = useState(review.comments);
  const [inputComment, setInputComment] = useState("");
  const [commentCNT, setcommentCNT] = useState(comments.length);
  const inputCommentHandler = (event: any) => {
    setInputComment(event.target.value);
  };
  const submitComment = (event: any) => {
    event.preventDefault();
    const data = {
      content: inputComment,
      memberId: userId,
      reviewId: review.reviewId,
      genreId: review.genre.genreId,
    };
    const success = (res) => {
      setcommentCNT((cur) => ++cur);
      const data = { reviewId: review.reviewId, loginMemberId: userId };
      searchReview(
        data,
        (res) => {
          setReview(res.data.data);
          setComments(res.data.data.comments);
        },
        (err) => {
          console.log(err);
        }
      );
      setInputComment("");
    };
    const fail = () => {
      console.log("fail");
    };
    createReviewComment(data, success, fail);
  };
  // 리뷰 평가 받아오기
  const [usefulCount, setUsefulCount] = useState(review.usefulCount);
  const [funCount, setFunCount] = useState(review.funCount);
  const [badCount, setBadCount] = useState(review.badCount);
  useEffect(() => {
    setComments(review.comments);
  }, [review]);
  // 리뷰평가 중복 확인
  const [evIsDone, setEvIsDone] = useState(review.done);
  const [evType, setEvType] = useState(review.reviewEvaluationType);

  // 모든 버튼 동작함수
  const onClick = (event: any) => {
    // 리뷰 수정 / 삭제 동작
    if (event.target.innerText === "수정") {
      navigate(`/review/${review.reviewId}/edit`);
    } else if (event.target.innerText === "삭제") {
      deleteReview(
        Number(review.reviewId),
        (res) => {
          alert("리뷰를 지웠습니다.");
          navigate("/review");
        },
        (err) => {
          console.log(err);
        }
      );
    }
  };

  const onClickUseful = (event: any) => {
    const data = {
      genreId: review.genre.genreId,
      memberId: review.memberId,
      reviewId: review.reviewId,
      type: event.target.id,
    };
    const searchReviewData = {
      loginMemberId: userId,
      reviewId: review.reviewId,
    };
    evaluationsReview(
      data,
      (res) => {
        if (res.data.data.done) {
          setUsefulCount((prev) => ++prev);
        } else {
          setUsefulCount((prev) => --prev);
        }
        searchReview(
          searchReviewData,
          (res) => {
            setReview(res.data.data);
            setEvIsDone(res.data.data.done);
            setEvType(res.data.data.reviewEvaluationType);
          },
          (err) => {}
        );
      },
      (err) => {}
    );
  };
  const onClickFun = (event: any) => {
    const data = {
      genreId: review.genre.genreId,
      memberId: review.memberId,
      reviewId: review.reviewId,
      type: event.target.id,
    };
    const searchReviewData = {
      loginMemberId: userId,
      reviewId: review.reviewId,
    };
    evaluationsReview(
      data,
      (res) => {
        if (res.data.data.done) {
          setFunCount((prev) => ++prev);
        } else {
          setFunCount((prev) => --prev);
        }
        searchReview(
          searchReviewData,
          (res) => {
            setReview(res.data.data);
            setEvIsDone(res.data.data.done);
            setEvType(res.data.data.reviewEvaluationType);
          },
          (err) => {}
        );
      },
      (err) => {
        console.log("");
      }
    );
  };
  const onClickBad = (event: any) => {
    const data = {
      genreId: review.genre.genreId,
      memberId: review.memberId,
      reviewId: review.reviewId,
      type: event.target.id,
    };
    const searchReviewData = {
      loginMemberId: userId,
      reviewId: review.reviewId,
    };
    evaluationsReview(
      data,
      (res) => {
        if (res.data.data.done) {
          setBadCount((prev) => ++prev);
        } else {
          setBadCount((prev) => --prev);
        }
        searchReview(
          searchReviewData,
          (res) => {
            setReview(res.data.data);
            setEvIsDone(res.data.data.done);
            setEvType(res.data.data.reviewEvaluationType);
          },
          (err) => {}
        );
      },
      (err) => {}
    );
  };
  // 리뷰 평가 재미, 유용, 별로에요 동작

  // 동일키워드 추천 리뷰 불러오기
  const [switchToggler, setSwitchToggler] = useState(false);
  const [recommendReview, setRecommendReview] = useState([]);
  const [positiveReview, setPositiveReview] = useState([]);
  const [interestReview, setInterestReview] = useState([]);
  const switchToggleHandler = () => {
    setSwitchToggler((prev) => !prev);
    if (switchToggler) {
      setRecommendReview(positiveReview);
    } else {
      setRecommendReview(interestReview);
    }
  };
  useEffect(() => {
    //memberId, reviewId
    const getPositiveReview = () => {
      const data = {
        orderBy: "POSITIVE",
      };
      searchReviews(
        data,
        (res) => {
          const data = res.data.data.data;
          const filterdReviewList = data.filter((rr) => {
            return (
              rr.reviewId !== review.reviewId && rr.memberId !== Number(userId)
            );
          });
          setPositiveReview(filterdReviewList);
          setRecommendReview(filterdReviewList);
        },
        (err) => {
          console.log("err:", err);
        }
      );
    };
    const getInterestReview = () => {
      const data = {
        orderBy: "POSITIVE",
        myInterest: userId,
      };
      searchReviews(
        data,
        (res) => {
          const data = res.data.data.data;
          const filterdReviewList = data.filter((rr) => {
            return (
              rr.reviewId !== review.reviewId && rr.memberId !== Number(userId)
            );
          });
          setInterestReview(filterdReviewList);
        },
        (err) => {
          console.log("err:", err);
        }
      );
    };
    getPositiveReview();
    getInterestReview();
    setFetched(true);
  }, []);
  if (!isFetched) return <Loading />;
  return (
    <div className="detail">
      <div className="first-line">
        <h1>{review.reviewTitle}</h1>
        <div className="emotionbox">
          <img src="/GraduationCap.png" alt="재밌어요" />
          <span>{usefulCount}</span>
          <img src="/smile.png" alt="유용해요" />
          <span>{funCount}</span>
          <img src="/thumbDown.png" alt="싫어요" />
          <span>{badCount}</span>
        </div>
      </div>
      <hr />
      <div
        className="content"
        dangerouslySetInnerHTML={{ __html: review.reviewContent }}
      ></div>

      <div className="ratingbuttons">
        <button
          id="USEFUL"
          onClick={onClickUseful}
          style={
            evIsDone && evType === "USEFUL"
              ? { backgroundImage: "url(/usefulDisabled.png" }
              : { backgroundImage: "url(/useful.png)" }
          }
          disabled={Number(userId) === review.reviewId}
        ></button>
        <button
          id="FUN"
          onClick={onClickFun}
          style={
            evIsDone && evType === "FUN"
              ? { backgroundImage: "url(/funnyDisabled.png" }
              : { backgroundImage: "url(/funny.png)" }
          }
          disabled={Number(userId) === review.reviewId}
        ></button>
        <button
          id="BAD"
          onClick={onClickBad}
          style={
            evIsDone && evType === "BAD"
              ? { backgroundImage: "url(/dislikeDisabled.png)" }
              : { backgroundImage: "url(/dislike.png)" }
          }
          disabled={Number(userId) === review.reviewId}
        ></button>
      </div>
      {Number(userId) === review.memberId ? (
        <div className="edit">
          <Button text="수정" styles="btn-primary" onClick={onClick} />
          <Button text="삭제" styles="btn-secondary" onClick={onClick} />
        </div>
      ) : null}

      {/* 아랫단 디테일  */}
      <div className="bottom-detail">
        <div className="left" style={{ margin: "0px" }}>
          <form className="input" onSubmit={submitComment}>
            <div className="form-floating mb-3 p-0 ">
              <textarea
                className="form-control text-black"
                id="input"
                placeholder="의견을 입력해주세요. (최대 300자)"
                value={inputComment}
                onChange={inputCommentHandler}
              ></textarea>
              <label
                className={`fw-bold ${
                  inputComment.length > 300 && "text-danger"
                } `}
                htmlFor="input"
              >
                {inputComment.length} / 300bytes
              </label>
            </div>

            <button type="submit">댓글 등록</button>
          </form>

          <div
            className="lst"
            style={{
              border: "solid black 1px",
              borderRadius: "30px",
              marginTop: "20px",
              padding: "50px",
              height: "90vh",
            }}
          >
            <div className="cnt">댓글 : {commentCNT}개</div>
            <br />
            {comments.length ? (
              comments.map((comment, idx) => (
                <Comments
                  key={idx}
                  comment={comment}
                  setComments={setComments}
                  setcommentCNT={setcommentCNT}
                />
              ))
            ) : (
              <div
                className="nocoment"
                style={{ width: "100%", height: "auto" }}
              >
                첫 댓글을 작성해 보세요!
              </div>
            )}
          </div>
        </div>

        <div
          className="vertical"
          style={{
            border: "3px",
            width: "auto",
            height: "103vh",
          }}
        >
          <div className="header">
            <h5>이런 리뷰는 어떠세요?</h5>
            <FormControlLabel
              className="useful-fun-switch"
              control={
                <Switch
                  checked={switchToggler}
                  onChange={switchToggleHandler}
                  color="warning"
                />
              }
              label="관심 장르만 보기"
              labelPlacement="end"
            />
          </div>
          <div className="reviewCard-wrapper">
            {recommendReview.map((review) => (
              <ReviewCard
                className="detailCard"
                key={review.reviewId}
                reviewId={review.reviewId}
                memberId={review.memberId}
                movieId={review.movieId}
                nickname={review.nickname}
                profileImageId={
                  review.profileImage ? review.profileImage.id : undefined
                }
                backgroundImageId={
                  review.backgroundImageResponse
                    ? review.backgroundImageResponse.id
                    : undefined
                }
                oneLineReview={review.highlight}
                funnyCount={review.funCount}
                usefulCount={review.usefulCount}
                dislikeCount={review.badCount}
                commentCount={review.commentCount}
                movieTitle={review.movieTitle}
                releaseYear={review.releaseYear}
                movieGenre={[review.genreResponse.genreName]}
                createDate={review.createdTime.substring(0, 10)}
                recommend={review.movieRecommendType === "YES"}
              />
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Detail;
