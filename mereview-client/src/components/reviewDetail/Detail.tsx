import "../../styles/css/Detail.css";
import ReviewCard from "../ReviewCard";
import { Button } from "../common";
import { useState, useEffect } from "react";
import { updateAchievementCount } from "../../api/members";
import {
  createReviewComment,
  searchReview,
  deleteReview,
  evaluationsReview,
  searchReviews,
} from "../../api/review";
import Comments from "./Comments";
import { useNavigate } from "react-router-dom";
import {
  Select,
  MenuItem,
  SelectChangeEvent,
  FormControlLabel,
  Switch,
} from "@mui/material";
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
    };
    const success = (res) => {
      console.log("success");
      setcommentCNT((cur) => ++cur);
      const data = { reviewId: review.reviewId, loginMemberId: userId };
      searchReview(
        data,
        (res) => {
          setReview(res.data.data);
          const achievementUpdate = {
            achievementType: 2,
            genreId: res.data.data.genre.genreId,
            memberId: userId,
          };
          updateAchievementCount(
            achievementUpdate,
            (res) => {},
            (err) => {
              console.log(err);
            }
          );
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
    console.log(review);
    createReviewComment(data, success, fail);
  };
  // 리뷰 평가 받아오기
  const [ev, setEv] = useState<evInterface>({
    FUN: review.funCount,
    USEFUL: review.usefulCount,
    BAD: review.badCount,
  });
  console.log(userId, review.reviewId);
  useEffect(() => {
    setComments(review.comments);
  }, [review]);

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
      // 리뷰 평가 재미, 유용, 별로에요 동작
    } else {
      const data = {
        genreId: review.genre.genreId,
        memberId: review.memberId,
        reviewId: review.reviewId,
        type: event.target.id,
      };
      const success = (res) => {
        const idx = event.target.id;
        if (res.data.data.done) {
          setEv((prev) => {
            const newEv = { ...prev };
            newEv[idx] += 1;
            return newEv;
          });
        } else if (!res.data.data.done) {
          setEv((prev) => {
            const newEv = { ...prev };
            newEv[idx] -= 1;
            return newEv;
          });
        }
      };
      const fail = (err) => {
        alert("이미 다른 평가를 남겼습니다.");
      };
      evaluationsReview(data, success, fail);
    }
  };
  // 동일키워드 추천 리뷰 불러오기
  const [switchToggler, setSwitchToggler] = useState(true);
  const [recommendReview, setRecommendReview] = useState([]);
  const switchToggleHandler = () => {
    setSwitchToggler((prev) => !prev);
    const data = { myInterest: userId, orderBy: "" };
    if (!switchToggler) {
      data.orderBy = "FUN";
    } else {
      data.orderBy = "USEFUL";
    }
    console.log(data);

    searchReviews(
      data,
      (res) => {
        const data = res.data.data.data;
        const filterdReviewList = data.filter((rr) => {
          return (
            rr.reviewId !== review.reviewId && rr.memberId !== Number(userId)
          );
        });
        console.log(filterdReviewList);
        setRecommendReview(filterdReviewList);
      },
      (err) => {
        console.log("err:", err);
      }
    );
  };
  useEffect(() => {
    //memberId, reviewId
    const getRecommendReview = () => {
      const data = {
        myInterest: userId,
        orderBy: "POSITIVE",
      };
      console.log(data);
      searchReviews(
        data,
        (res) => {
          const data = res.data.data.data;
          const filterdReviewList = data.filter((rr) => {
            return (
              rr.reviewId !== review.reviewId && rr.memberId !== Number(userId)
            );
          });
          console.log(filterdReviewList);
          setRecommendReview(filterdReviewList);
        },
        (err) => {
          console.log("err:", err);
        }
      );
    };
    getRecommendReview();
  }, []);
  const editHandler = () => {};
  console.log(typeof userId, typeof review.memberId);
  console.log(recommendReview);
  return (
    <div className="detail">
      <div className="first-line">
        <h1>{review.reviewTitle}</h1>
        <div className="emotionbox">
          <img src="/GraduationCap.png" alt="재밌어요" />
          <span>{ev.USEFUL}</span>
          <img src="/smile.png" alt="유용해요" />
          <span>{ev.FUN}</span>
          <img src="/thumbdown.png" alt="싫어요" />
          <span>{ev.BAD}</span>
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
          onClick={onClick}
          disabled={userId === review.reviewId}
        ></button>
        <button
          id="FUN"
          onClick={onClick}
          disabled={userId === review.reviewId}
        ></button>
        <button
          id="BAD"
          onClick={onClick}
          disabled={userId === review.reviewId}
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
                No Coments 댓글좀 주세요 ㅋ
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
            <h5>관심장르 리뷰 추천</h5>
            <FormControlLabel
              className="useful-fun-switch"
              control={
                <Switch
                  checked={switchToggler}
                  onChange={switchToggleHandler}
                  color="warning"
                />
              }
              label={switchToggler ? "유용해요 기준" : "재밌어요 기준"}
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
                nickname={review.nickname}
                profileImageId={review.profileImage}
                backgroundImageId={
                  review.backgroundImageResponse
                    ? review.backgroundImageResponse.id
                    : null
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
                recommend={review.movieEvaluatedType === "YES" ? true : false}
              />
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Detail;
