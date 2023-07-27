import React from "react";
import { Container, Row, Col } from "react-bootstrap";
import { ReviewCardInterface } from "./interface/ReviewCardInterface";
import "../styles/css/ReviewCard.css";

type Style = {
  [key: string]: string | number;
};

const ReviewCard = (props: ReviewCardInterface) => {
  const {
    reviewId,
    memberId,
    nickname,
    profileImagePath,
    backgroundImagePath,
    funnyCount,
    usefulCount,
    dislikeCount,
    commentCount,
    movieTitle,
    releaseYear,
    movieGenre,
    createDate,
    recommend,
    onClickProfile,
    onClickTitle,
  } = props;

  const cardStyle: Style = {
    backgroundImage: `url(${backgroundImagePath})`,
  };

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

  return (
    <>
      <div className="card" style={cardStyle}>
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
          <Row>한줄평</Row>
          <Row></Row>
        </div>
      </div>
    </>
  );
};

export default ReviewCard;
