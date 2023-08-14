import { Container } from "react-bootstrap";
import ReviewCard from "../components/ReviewCard";
import { ReviewCardInterface } from "../components/interface/ReviewCardInterface";
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css'
import Loading from "./common/Loading";

import "../styles/css/ReviewList.css";

interface ReviewListProps {
  reviewList: ReviewCardInterface[];
}

/* 정렬 방식에 따라 받는 reviewList가 달라질 것 */
const NotificationList = ({ reviewList }: ReviewListProps) => {
  if (reviewList === null || reviewList === undefined) return <Loading />;
  return (
    <>
     <Swiper
      spaceBetween={50}
      slidesPerView={3}
      onSlideChange={() => console.log('slide change')}
      onSwiper={(swiper) => console.log(swiper)}
    ></Swiper>

      <div className="review-card-list-wrapper">
        {reviewList.map((review: ReviewCardInterface, index: number) => (
          <SwiperSlide>
          <ReviewCard
            key={review.reviewId}
            reviewId={review.reviewId}
            memberId={review.memberId}
            nickname={review.nickname}
            profileImageId={review.profileImageId}
            backgroundImageId={review.backgroundImageId}
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
          />
          </SwiperSlide>
        ))}
      </div>
    </>
  );
};

export default NotificationList;
