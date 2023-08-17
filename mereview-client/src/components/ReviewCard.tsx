import React from "react";
import { Container, Row, Col } from "react-bootstrap";
import { ReviewCardInterface } from "./interface/ReviewCardInterface";
import "../styles/css/ReviewCard.css";
import { useNavigate } from "react-router-dom";

type Style = {
  [key: string]: string | number;
};

const ReviewCard = (props: ReviewCardInterface) => {
  const {
    className,
    reviewId,
    memberId,
    movieId,
    nickname,
    profileImageId,
    backgroundImageId,
    oneLineReview,
    funnyCount,
    usefulCount,
    dislikeCount,
    commentCount,
    movieTitle,
    releaseYear,
    movieGenre,
    createDate,
    recommend,
  } = props;
  const navigate = useNavigate();

  const handleClickReviewCard = (
    event: React.MouseEvent<HTMLParagraphElement>
  ) => {
    navigate(`/review/${reviewId}`);
  };

  const handleClickProfile = (
    event: React.MouseEvent<HTMLParagraphElement>
  ) => {
    event.stopPropagation();
    navigate(`/profile/${memberId}`);
  };

  const handleClickMovie = (event: React.MouseEvent<HTMLParagraphElement>) => {
    event.stopPropagation();
    navigate(`/movie/${movieId}`);
  };

  const cardStyle: Style = {};
  if (backgroundImageId) {
    cardStyle.backgroundImage = `url(${process.env.REACT_APP_API_URL}/image/download/backgrounds/${backgroundImageId})`;
  }

  const recommendStyle: Style = {};
  if (funnyCount + usefulCount + dislikeCount > 0) {
    recommendStyle.opacity =
      (funnyCount + usefulCount) / (funnyCount + usefulCount + dislikeCount);
    if (recommendStyle.opacity < 0.5) recommendStyle.opacity = 0.5;
  }

  const formattedCreateDate: Date = new Date(createDate);
  formattedCreateDate.setHours(formattedCreateDate.getHours() + 9);
  const year: number = formattedCreateDate.getFullYear();
  const month: string = String(formattedCreateDate.getMonth() + 1).padStart(
    2,
    "0"
  );
  const day: string = String(formattedCreateDate.getDate()).padStart(2, "0");
  const hour: string = String(formattedCreateDate.getHours()).padStart(2, "0");
  const minute: string = String(formattedCreateDate.getMinutes()).padStart(
    2,
    "0"
  );
  const genres: string = movieGenre.join(". ");
  const defaultProfileImage = "/testProfile.gif";

  return (
    <>
      <div
        className={`review-card ${className}`}
        style={cardStyle}
        onClick={handleClickReviewCard}
      >
        <div className="card-overlay">
          <Row>
            <Col className="date">
              {year}-{month}-{day} {hour}:{minute}
            </Col>
            <Col className="evaluation-counts">
              <div>
                <img src="/ReviewCard/laugh.png" alt="재밌어요" />
                <p>{funnyCount}</p>
              </div>
              <div>
                <img src="/ReviewCard/book.png" alt="유용해요" />
                <p>{usefulCount}</p>
              </div>
              <div>
                <img src="/ReviewCard/dislike.png" alt="별로에요" />
                <p>{dislikeCount}</p>
              </div>
              <div>
                <img src="/ReviewCard/comment.png" alt="댓글수" />
                <p>{commentCount}</p>
              </div>
            </Col>
          </Row>
          <Row>
            <Col>
              <span className="one-line-review">{oneLineReview}</span>
            </Col>
          </Row>
          <div className="additional-info">
            <Row>
              <Col className="profile-container">
                <div className="profile-img">
                  <img
                    src={
                      profileImageId
                        ? `${process.env.REACT_APP_API_URL}/image/download/profiles/${profileImageId}`
                        : defaultProfileImage
                    }
                    alt="Profile"
                  />
                </div>
                <span className="nickname" onClick={handleClickProfile}>
                  {nickname}
                </span>
              </Col>
            </Row>
            <Row>
              <Col>
                <span className="movie-title" onClick={handleClickMovie}>
                  {movieTitle}
                </span>
              </Col>
            </Row>
            <Row>
              <Col>
                <span className="year-genres">
                  {releaseYear} | {genres}
                </span>
              </Col>
            </Row>
            <div className="recommend" style={recommendStyle}>
              <div className="recommend-text">
                {recommend
                  ? "이 영화를 추천합니다."
                  : "이 영화를 추천하지 않습니다."}
              </div>
              <div className="recommend-icon-container">
                {recommend ? (
                  <img src="/ReviewCard/thumbsup.png" alt="추천!!" />
                ) : (
                  <img src="/ReviewCard/thumbsdown.png" alt="비추!!" />
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default ReviewCard;
