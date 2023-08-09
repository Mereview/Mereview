import "../../styles/css/Detail.css";
import ReviewCard from "../ReviewCard";
import { ReviewCardInterface } from "../interface/ReviewCardInterface";
import { useSelector } from "react-redux";
import { Button } from "../common";
import { useState } from "react";
import { createReviewComment } from "../../api/review";
import Comments from "./Comments";
const Detail = ({ review }: any) => {
  const userId = useSelector((state: any) => state.user.user.id);
  const [comments, setComments] = useState(review.comments);
  const [inputComment, setInputComment] = useState("");
  const onClick = (event: any) => {
    console.log(event.target);
  };
  const inputCommentHandler = (event: any) => {
    setInputComment(event.target.value);
    console.log(inputComment);
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
  console.log(review);
  return (
    <div className="detail">
      <div className="first-line">
        <h1>{review.reviewTitle}</h1>
        <div className="emotionbox">
          <img src="/smile.png" alt="유용해요" />{" "}
          <span>{review.usefulCount}</span>
          <img src="/GraduationCap.png" alt="재밌어요" />{" "}
          <span>{review.funCount}</span>
          <img src="/thumbdown.png" alt="싫어요" />{" "}
          <span>{review.badCount}</span>
        </div>
      </div>
      <hr />
      <div className="content">
        <p>{review.reviewContent.replace(/<.*?>/g, "")}</p>
      </div>
      <div className="ratingbuttons">
        <button id="useful"></button>
        <button id="funny"></button>
        <button id="dislike"></button>
      </div>
      {userId === review.memberId ? (
        <div className="edit">
          <Button text="수정" styles="btn-primary" onClick={onClick} />
          <Button text="삭제" styles="btn-secondary" onClick={onClick} />
        </div>
      ) : null}

      {/* 아랫단 디테일  */}
      <div className="comment">
        <div className="left" style={{ margin: "0px" }}>
          <form className="input" onSubmit={submitComment}>
            <textarea
              name=""
              id="input"
              placeholder="의견을 입력해주세요."
              value={inputComment}
              onChange={inputCommentHandler}
            ></textarea>
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
            {comments.length ? (
              comments.map((comment) => <Comments comment={comment} />)
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
