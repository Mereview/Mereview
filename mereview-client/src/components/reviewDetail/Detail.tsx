import "../../styles/css/Detail.css";
import ReviewCard from "../ReviewCard";
import { ReviewCardInterface } from "../interface/ReviewCardInterface";
import { useSelector } from "react-redux";
import { Button } from "../common";
import { useState, useEffect } from "react";
import { createReviewComment, searchReview } from "../../api/review";
import Comments from "./Comments";
import { useNavigate } from "react-router-dom";
import { deleteReview, evaluationsReview } from "../../api/review";

interface evInterface {
  FUN: number;
  USEFUL: number;
  BAD: number;
}

const Detail = ({ review, setReview }: any) => {
  const userId = useSelector((state: any) => state.user.user.id);
  const [comments, setComments] = useState(review.comments);
  const [inputComment, setInputComment] = useState("");
  const [commentCNT, setcommentCNT] = useState(comments.length);
  const [ev, setEv] = useState<evInterface>({
    FUN: review.funCount,
    USEFUL: review.usefulCount,
    BAD: review.badCount,
  });
  useEffect(() => {
    setComments(review.comments);
  }, [review]);

  const onClick = (event: any) => {
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
  const inputCommentHandler = (event: any) => {
    setInputComment(event.target.value);
  };
  const navigate = useNavigate();
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
  const 추천해줄리뷰 = [
    {
      id: 1,
    },
    {
      id: 2,
    },
    {
      id: 3,
    },
    {
      id: 4,
    },
    {
      id: 5,
    },
    {
      id: 6,
    },
  ];
  const editHandler = () => {};
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
        <button id="USEFUL" onClick={onClick}></button>
        <button id="FUN" onClick={onClick}></button>
        <button id="BAD" onClick={onClick}></button>
      </div>
      {userId === review.memberId ? (
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
                {inputComment.length}자 / 300bytes
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
          {추천해줄리뷰.map((review) => (
            <div key={review.id}></div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Detail;
