import "../../styles/css/Comments.css";
import { useState } from "react";
import { deleteReviewComment, updateCommentLike } from "../../api/review";
import { searchReview } from "../../api/review";
import { useParams } from "react-router-dom";
const Comments = ({ comment, setComments, setcommentCNT }) => {
  const profileImageURL = comment.profileImage
    ? `${process.env.REACT_APP_API_URL}/image/download/profiles/${comment.profileImage.id}`
    : "/testProfile.gif";
  const userId = localStorage.getItem("id");
  const { id } = useParams();
  const [evCnt, setEvCnt] = useState({
    likeCount: comment.likeCount,
    dislikeCount: comment.disLikeCount,
  });

  // 댓글작성 시간 계산
  const now = new Date();
  const createdTime = new Date(comment.createdTime);
  createdTime.setHours(createdTime.getHours() + 9);
  const diffTime = Math.abs(now.getTime() - createdTime.getTime());
  let presentTime = null;

  // 하루지났을때
  if (Math.floor(diffTime / (1000 * 60 * 60 * 24))) {
    presentTime = Math.floor(diffTime / (1000 * 60 * 60 * 24)) + "일";
  } // 시간 지났을때
  else if (Math.floor((diffTime % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60))) {
    presentTime =
      Math.floor((diffTime % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)) +
      "시간";
  } // 1분이상 지났을 때
  else if (Math.floor((diffTime % (1000 * 60 * 60)) / (1000 * 60))) {
    presentTime =
      Math.floor((diffTime % (1000 * 60 * 60)) / (1000 * 60)) + "분";
  } // 1초이상 지났을 때
  else {
    if (Math.floor((diffTime % (1000 * 60)) / 1000) <= 0) {
      presentTime = "방금";
    } else {
      presentTime = Math.floor((diffTime % (1000 * 60)) / 1000) + "초";
    }
  }

  const onClick = () => {
    deleteReviewComment(
      comment.commentId,
      (res) => {
        alert("삭제되었습니다.");
        const data = {
          reviewId: id,
          loginMemberId: Number(userId),
        };
        searchReview(
          data,
          (res) => {
            setComments(res.data.data.comments);
            setcommentCNT((cur) => --cur);
          },
          (err) => {
            console.log(err);
          }
        );
      },
      (err) => {
        console.log("댓글삭제실패");
      }
    );
  };
  const likeHandler = () => {
    const data = {
      commentId: comment.commentId,
      memberId: Number(userId),
      type: "LIKE",
    };
    updateCommentLike(
      data,
      (res) => {
        const data = {
          reviewId: id,
          loginMemberId: Number(userId),
        };
        searchReview(
          data,
          (res) => {
            setComments(res.data.data.comments);
          },
          (err) => {
            console.log(err);
          }
        );
      },
      (err) => {
        alert("이미 추천/비추천을 눌렀습니다.");
      }
    );
  };
  const dislikeHandler = () => {
    const data = {
      commentId: comment.commentId,
      memberId: Number(userId),
      type: "DISLIKE",
    };
    updateCommentLike(
      data,
      (res) => {
        const data = {
          reviewId: id,
          loginMemberId: Number(userId),
        };
        searchReview(
          data,
          (res) => {
            setComments(res.data.data.comments);
          },
          (err) => {
            console.log(err);
          }
        );
      },
      (err) => {
        alert("이미 추천/비추천을 눌렀습니다.");
      }
    );
  };
  return (
    <div className="comment">
      <div className="writer">
        <div>
          <img src={profileImageURL} alt="댓글주인" />
        </div>
        <a href={`/profile/${comment.memberId}`}>{comment.nickname}</a>
        <button
          className="delete"
          onClick={onClick}
          disabled={comment.memberId !== Number(userId)}
        ></button>
      </div>
      <div className="all">
        <p className="content">{comment.content}</p>
      </div>
      <div className="buttons">
        <p className="createdDate">{presentTime} 전</p>
        <div className="buttonsWrapper">
          <div
            className="likeWrapper"
            onClick={likeHandler}
            aria-disabled={comment.memberId === Number(userId)}
          >
            <button
              id="LIKE"
              disabled={comment.memberId === Number(userId)}
            ></button>
            {comment.likeCount}
          </div>
          <div
            className="dislikeWrapper"
            onClick={dislikeHandler}
            aria-disabled={comment.memberId === Number(userId)}
          >
            <button
              id="DISLIKE"
              disabled={comment.memberId === Number(userId)}
            ></button>
            {comment.dislikeCount}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Comments;
