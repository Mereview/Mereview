import { Container } from "react-bootstrap";
import ReviewCard from "../components/ReviewCard";
import { ReviewCardInterface } from "../components/interface/ReviewCardInterface";
import Loading from "./common/Loading";
import "../styles/css/ReviewList.css";

interface ReviewListProps {
  reviewList: ReviewCardInterface[];
}

/* 정렬 방식에 따라 받는 reviewList가 달라질 것 */
const ReviewList = ({ reviewList }: ReviewListProps) => {
  if (reviewList === null || reviewList === undefined) return <Loading />;
  return (
    <>
      <div className="review-card-list-wrapper">
        {reviewList.map((review: ReviewCardInterface, index: number) => (
          <ReviewCard
            key={review.reviewId}
            reviewId={review.reviewId}
            memberId={review.memberId}
            movieId={review.movieId}
            nickname={review.nickname}
            profileImageId={review.profileImageId}
            backgroundImageId={review.backgroundImageId}
            oneLineReview={review.oneLineReview}
            funnyCount={review.funnyCount}
            usefulCount={review.usefulCount}
            dislikeCount={review.dislikeCount}
            commentCount={review.commentCount}
            hitsCount={review.hitsCount}
            movieTitle={review.movieTitle}
            releaseYear={review.releaseYear}
            movieGenre={review.movieGenre}
            createDate={review.createDate}
            recommend={review.recommend}
          />
        ))}
      </div>
    </>
  );
};

export default ReviewList;
