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
  console.log(comment.memberId, userId);
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
            console.log("comment:", res.data.data.comments);
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
  const likeDislike = (event: any) => {
    const data = {
      commentId: comment.commentId,
      memberId: userId,
      type: event.target.id,
    };
    updateCommentLike(
      data,
      (res) => {
        console.log(res);
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
        <p>{comment.nickname}</p>
      </div>

      <div className="all">{comment.content}</div>

      <div className="buttons">
        <div className="lndl">
          <div>
            <button
              id="LIKE"
              disabled={comment.memberId === Number(userId)}
              onClick={likeDislike}
            ></button>
            {comment.likeCount}
          </div>
          <div>
            <button
              id="DISLIKE"
              disabled={comment.memberId === Number(userId)}
              onClick={likeDislike}
            ></button>
            {comment.dislikeCount}
          </div>
          <button
            className="delete"
            onClick={onClick}
            disabled={comment.memberId !== Number(userId)}
          >
            X
          </button>
        </div>
      </div>
    </div>
  );
};

export default Comments;
