import React, { useState } from "react";
import { Container, Row, Col } from "react-bootstrap";

import { ReviewCardInterface } from "./interface/ReviewCardInterface";
import "../styles/css/ReviewCard.css";
import { useNavigate } from "react-router-dom";

import { Button } from "./common";
import { NotificationReviewCardInterface } from "./interface/NotificationReviewCardInterface";
import { deleteNotification, toggleNotificationStatus } from "../api/review";

type Style = {
  [key: string]: string | number;
};

const NotificationReviewCard = (props: NotificationReviewCardInterface) => {
  const {
    notificationId,
    className,
    reviewId,
    movieId,
    memberId,
    nickname,
    profileImageId,
    backgroundImageId,
    oneLineReview,
    funnyCount,
    usefulCount,
    dislikeCount,
    hitsCount,
    commentCount,
    movieTitle,
    releaseYear,
    movieGenre,
    createDate,
    recommend,
    confirmed,
    confirmedReviewList,
    unconfirmedReviewList,
    setConfirmedReviewList,
    setUnconfirmedReviewList,
  } = props;
  const navigate = useNavigate();
  const [pressStartTime, setPressStartTime] = useState(0);
  const [isPressing, setIsPressing] = useState(false);
  const pressThreshold = 100;
  const handlePressStart = (event: React.MouseEvent<HTMLDivElement>) => {
    setPressStartTime(Date.now());
    setIsPressing(true);
  };
  console.log(confirmedReviewList);
  console.log(unconfirmedReviewList);
  const handlePressEnd = (event: React.MouseEvent<HTMLDivElement>) => {
    setIsPressing(false);

    // 길게 누르기가 1초 이상인 경우에만 이벤트 처리
    if (Date.now() - pressStartTime < pressThreshold) {
      navigate(`/review/${reviewId}`);
    }
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

  const handlerDeleteNotification = () => {
    if (confirmed) {
      deleteNotification(
        notificationId,
        () => {},
        () => {}
      );
      setConfirmedReviewList((prev) =>
        prev.filter((e) => e.notificationId != notificationId)
      );
    } else {
      deleteNotification(
        notificationId,
        () => {},
        () => {}
      );
      setUnconfirmedReviewList((prev) =>
        prev.filter((e) => e.notificationId != notificationId)
      );
    }
  };

  const handlerToggleStatus = () => {
    toggleNotificationStatus(
      notificationId,
      () => {},
      () => {}
    );

    if (confirmed) {
      const notificationCard = confirmedReviewList.filter(
        (e) => e.notificationId === notificationId
      );
      console.log(notificationCard);

      setUnconfirmedReviewList((prev) => prev.concat(notificationCard));
      setConfirmedReviewList((prev) =>
        prev.filter((e) => e.notificationId != notificationId)
      );
    } else {
      const notificationCard = unconfirmedReviewList.filter(
        (e) => e.notificationId === notificationId
      );
      console.log(notificationCard);
      setConfirmedReviewList((prev) => prev.concat(notificationCard));
      setUnconfirmedReviewList((prev) =>
        prev.filter((e) => e.notificationId != notificationId)
      );
    }
  };

  const cardStyle: Style = {};
  if (backgroundImageId) {
    cardStyle.backgroundImage = `url(${process.env.REACT_APP_API_URL}/image/download/backgrounds/${backgroundImageId})`;
    cardStyle.backgroundPosition = "center center";
  }

  const recommendStyle: Style = {};
  if (funnyCount + usefulCount + dislikeCount > 0) {
    recommendStyle.opacity =
      (funnyCount + usefulCount) / (funnyCount + usefulCount + dislikeCount);
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
    <div className="me-3">
      <div
        className={`notification-review-card ${className}`}
        style={cardStyle}
        onMouseDown={handlePressStart}
        onMouseUp={handlePressEnd}
      >
        <div className="card-overlay">
          <Row>
            <Col className="date">
              {year}-{month}-{day} {hour}:{minute}
            </Col>
            <Col className="evaluation-counts">
              <div>
                <img src="/ReviewCard/laughing.png" alt="재밌어요" />
                <p>{funnyCount}</p>
              </div>
              <div>
                <img src="/ReviewCard/scholar.png" alt="유용해요" />
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
              <div>
                <img src="/ReviewCard/hitcount.png" alt="댓글수" />
                <p>{hitsCount > 1000 ? "999+" : hitsCount}</p>
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
              {recommend ? (
                <img src="/ReviewCard/thumbsup.png" alt="추천!!" />
              ) : (
                <img src="/ReviewCard/thumbsdown.png" alt="비추!!" />
              )}
            </div>
          </div>
        </div>
      </div>
      <div className="btn-justify">
        <Button
          onClick={handlerToggleStatus}
          styles="btn-primary w-50"
          text="알림 확인"
        ></Button>
        <Button
          onClick={handlerDeleteNotification}
          styles="btn-primary w-50"
          text="알림 제거"
        ></Button>
      </div>
    </div>
  );
};

export default NotificationReviewCard;
