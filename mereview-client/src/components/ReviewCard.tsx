import React from "react";
import "../styles/css/ReviewCard.css";

interface ReviewCardProps {
  reviewId: number;
  memberId: string;
  nickname: string;
  profileImagePath: string;
  backgroundImagePath: string;
  funnyCount: number;
  usefulCount: number;
  notLikeCount: number;
  commentCount: number;
  movieTitle: string;
  releaseYear: number;
  movieGenre: string;
  onClickProfile: (event: React.MouseEvent<HTMLParagraphElement>) => void;
  onClickTitle: (event: React.MouseEvent<HTMLParagraphElement>) => void;
}

const ReviewCard = (props: ReviewCardProps) => {
  const {
    reviewId,
    memberId,
    nickname,
    profileImagePath,
    backgroundImagePath,
    funnyCount,
    usefulCount,
    notLikeCount,
    commentCount,
    movieTitle,
    releaseYear,
    movieGenre,
    onClickProfile,
    onClickTitle,
  } = props;

  return (
    <div className="card">
      <p>리뷰아이디: {reviewId}</p>
      <p>멤버아이디: {memberId}</p>
      <p>별명: {nickname}</p>
      <p onClick={onClickProfile}>프로필이미지: {profileImagePath}</p>
      <p onClick={onClickTitle}>배경이미지: {backgroundImagePath}</p>
      <p>재밌어요: {funnyCount}</p>
      <p>유용해요: {usefulCount}</p>
      <p>별로에요: {notLikeCount}</p>
      <p>댓글수: {commentCount}</p>
      <p>영화제목: {movieTitle}</p>
      <p>개봉년도: {releaseYear}</p>
      <p>영화장르: {movieGenre}</p>
    </div>
  );
};

export default ReviewCard;
