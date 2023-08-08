import { DummyMov, DummyRev } from "../../pages/ReviewDetailPage";
import "../../styles/css/Detail.css";
import ReviewCard from "../ReviewCard";
import { ReviewCardInterface } from "../interface/ReviewCardInterface";
import { useSelector } from "react-redux";
import { Button } from "../common";
interface TODProps {
  review: DummyRev;
  movie: DummyMov;
}

const Detail = ({ review, movie }: TODProps) => {
  const userId = useSelector((state: any) => state.user.id);
  const onClick = (event: any) => {
    console.log(event.target);
  };
  const commentDummy = [
    {
      id: 1,
      writer: "작성자1",
      comment:
        "코멘트1코멘트1코멘트1코멘트1코멘트1코멘트1코멘트1코멘트1코멘트1",
    },
    {
      id: 2,
      writer: "작성자2",
      comment:
        "코멘트2코멘트2코멘트2코멘트2코멘트2코멘트2코멘트2코멘트2코멘트2코멘트2",
    },
    {
      id: 3,
      writer: "작성자3",
      comment:
        "코멘트3코멘트3코멘트3코멘트3코멘트3코멘트3코멘트3코멘트3코멘트3코멘트3코멘트3",
    },
    {
      id: 4,
      writer: "작성자4",
      comment:
        "코멘트4코멘트4코멘트4코멘트4코멘트4코멘트4코멘트4코멘트4코멘트4",
    },
    {
      id: 5,
      writer: "작성자5",
      comment: "코5코5코5코5코5코5코5코5코5코5코5코5코5코5코5",
    },
    {
      id: 6,
      writer: "작성자5",
      comment: "코5코5코5코5코5코5코5코5코5코5코5코5코5코5코5",
    },
    {
      id: 7,
      writer: "작성자5",
      comment: "코5코5코5코5코5코5코5코5코5코5코5코5코5코5코5",
    },
    {
      id: 8,
      writer: "작성자5",
      comment: "코5코5코5코5코5코5코5코5코5코5코5코5코5코5코5",
    },
    {
      id: 9,
      writer: "작성자5",
      comment: "코5코5코5코5코5코5코5코5코5코5코5코5코5코5코5",
    },
    {
      id: 10,
      writer: "작성자5",
      comment: "코5코5코5코5코5코5코5코5코5코5코5코5코5코5코5",
    },
    {
      id: 11,
      writer: "작성자5",
      comment: "코5코5코5코5코5코5코5코5코5코5코5코5코5코5코5",
    },
    {
      id: 12,
      writer: "작성자5",
      comment: "코5코5코5코5코5코5코5코5코5코5코5코5코5코5코5",
    },
  ];
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
  const ReviewDummy = {
    reviewId: 1141223,
    memberId: "us22er123",
    nickname: "JohnDoe",
    profileImagePath: "/ReviewCardDummy/dummyprofile2.jpg",
    backgroundImagePath: "/test.jpg",
    oneLineReview: "리뷰의 내용을 요약하는33 한줄평!",
    funnyCount: 10,
    usefulCount: 15,
    dislikeCount: 2,
    commentCount: 5,
    movieTitle: "Example Movie",
    releaseYear: 2023,
    movieGenre: ["액션", "모험"],
    createDate: Date.now(),
    recommend: false,
  };

  return (
    <div className="detail">
      <div className="first-line">
        <h1>{review.title}</h1>
        <div className="emotionbox">
          <img src="/smile.png" alt="유용해요" /> <span>20</span>
          <img src="/GraduationCap.png" alt="재밌어요" /> <span>20</span>
          <img src="/thumbdown.png" alt="싫어요" /> <span>20</span>
        </div>
      </div>
      <hr />
      <div className="content">
        <p>{review.content}</p>
      </div>
      <div className="ratingbuttons">
        <button id="useful"></button>
        <button id="funny"></button>
        <button id="dislike"></button>
      </div>
      {userId === review.memberId ? (
        <div className="edit">
          <Button text="수정" styles="btn-primary" onClick={onClick}></Button>
          <Button text="삭제" styles="btn-secondary" onClick={onClick}></Button>
        </div>
      ) : null}

      {/* 아랫단 디테일  */}
      <div className="comment">
        <div className="left" style={{ margin: "0px" }}>
          <div className="input">
            <form>
              <input type="text" />
              <button type="submit">댓글 등록</button>
            </form>
          </div>

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
            {commentDummy.map((comment) => (
              <div key={comment.id} style={{ width: "100%", height: "auto" }}>
                <p>{comment.comment}</p>
                <p>{comment.writer}</p>
                <hr style={{ border: "dashed 2px gray" }} />
              </div>
            ))}
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
            <div></div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Detail;
