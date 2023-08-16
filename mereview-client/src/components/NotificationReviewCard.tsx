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
    memberId,
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
    confirmed,
    confirmedReviewList,
    unconfirmedReviewList,
    setConfirmedReviewList,
    setUnconfirmedReviewList
  } = props;
  const navigate = useNavigate();
  const [pressStartTime, setPressStartTime] = useState(0);
  const [isPressing, setIsPressing] = useState(false);
  const pressThreshold = 100;
  const handlePressStart = (event: React.MouseEvent<HTMLDivElement>) => {
    setPressStartTime(Date.now());
    setIsPressing(true);
  };

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
    console.log("Movie Name Clicked", movieTitle);
  };

  const handlerDeleteNotification = () => {
    if(confirmed){
      deleteNotification(notificationId, ()=>{}, ()=>{})
      setConfirmedReviewList(prev => prev.filter((e)=> e.notificationId != notificationId))
    }else{
      deleteNotification(notificationId, ()=>{}, ()=>{})
      setUnconfirmedReviewList(prev => prev.filter((e)=> e.notificationId != notificationId))
    }
  }

  const handlerToggleStatus = () => {
    
    toggleNotificationStatus({loginMemberId : memberId, reviewId : reviewId}, ()=>{}, ()=>{})
    if(confirmed){
      const notificationCard = confirmedReviewList.filter((e)=>e.notificationId == notificationId)
      setConfirmedReviewList(prev => prev.filter((e)=> e.notificationId != notificationId))
      setUnconfirmedReviewList(prev => prev.concat(notificationCard));
      }else{
      const notificationCard = confirmedReviewList.filter((e)=>e.notificationId == notificationId)
      setUnconfirmedReviewList(prev => prev.filter((e)=> e.notificationId != notificationId))
      setConfirmedReviewList(prev => prev.concat(notificationCard));
    }
  }

  const cardStyle: Style = {};
  if (backgroundImageId) {
    cardStyle.backgroundImage = `url(${process.env.REACT_APP_API_URL}/image/download/backgrounds/${backgroundImageId})`;
  }

  const recommendStyle: Style = {};
  if (funnyCount + usefulCount + dislikeCount > 0) {
    recommendStyle.opacity =
      (funnyCount + usefulCount) / (funnyCount + usefulCount + dislikeCount);
  }

  const formattedCreateDate: Date = new Date(createDate);
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
            <Col md={"auto"} className="date">
              {year}-{month}-{day} {hour}:{minute}
            </Col>
            <Col className="evaluation">
              <span>재밌어요: {funnyCount}</span> |{" "}
              <span>유용해요: {usefulCount}</span> |{" "}
              <span>별로에요: {dislikeCount}</span> |{" "}
              <span>Comment: {commentCount}</span>
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
                <img src="/ReviewCardDummy/thumbsup.png" alt="추천!!" />
              ) : (
                <img src="/ReviewCardDummy/thumbsdown.png" alt="비추!!" />
              )}
            </div>
          </div>
        </div>
      </div>
      <div className="btn-justify">
        <Button
          onClick={handlerToggleStatus}
          styles="btn-primary"
          text="확인"
        ></Button>
        <Button
          onClick={
            handlerDeleteNotification
          }
          styles="btn-primary"
          text="삭제"
        ></Button>
      </div>
    </div>
  );
};

export default NotificationReviewCard;
