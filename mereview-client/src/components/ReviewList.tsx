import { Container } from "react-bootstrap";
import ReviewCard from "../components/ReviewCard";
import { ReviewCardInterface } from "../components/interface/ReviewCardInterface";
import "../styles/css/ReviewList.css";

interface ReviewListProps {
  reviewList: ReviewCardInterface[];
}

/* 정렬 방식에 따라 받는 reviewList가 달라질 것 */
const ReviewList = ({ reviewList }: ReviewListProps) => {
  return (
    <>
      <div className="card-list-wrapper">
        {reviewList.map((review: ReviewCardInterface, index: number) => (
          <ReviewCard
            key={review.reviewId}
            reviewId={review.reviewId}
            memberId={review.memberId}
            nickname={review.nickname}
            profileImagePath={review.profileImagePath}
            backgroundImagePath={review.backgroundImagePath}
            oneLineReview={review.oneLineReview}
            funnyCount={review.funnyCount}
            usefulCount={review.usefulCount}
            dislikeCount={review.dislikeCount}
            commentCount={review.commentCount}
            movieTitle={review.movieTitle}
            releaseYear={review.releaseYear}
            movieGenre={review.movieGenre}
            createDate={review.createDate}
            recommend={review.recommend}
            onClickProfile={review.onClickProfile}
            onClickTitle={review.onClickTitle}
          />
        ))}
      </div>
    </>
  );
};

export default ReviewList;
