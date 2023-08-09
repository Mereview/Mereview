import "../../styles/css/Comments.css";

const Comments = ({ comment }) => {
  const profileImageURL = comment;

  return (
    <div className="comment">
      <div className="writer">
        <img src="/testProfile.gif" alt="댓글주인" />
        <p>{comment.nickname}</p>
      </div>
      <div className="content">{comment.content}</div>
      <div className="buttons">
        <div className="lndl">
          <div>
            <button>조아용</button>
            {comment.likeCount}
          </div>
          <div>
            <button>안조아용</button>
            {comment.dislikeCount}
          </div>
        </div>
        <div className="delete">
          <button>X</button>
        </div>
      </div>
    </div>
  );
};

export default Comments;
