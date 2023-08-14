import { Container } from "react-bootstrap";
import ReviewCard from "../components/ReviewCard";
import { ReviewCardInterface } from "../components/interface/ReviewCardInterface";
import Loading from "./common/Loading";
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import "../styles/css/ReviewList.css";

interface ReviewListProps {
  reviewList: ReviewCardInterface[];
}

/* 정렬 방식에 따라 받는 reviewList가 달라질 것 */
const NotificationReviewList = ({ reviewList }: ReviewListProps) => {
  if (reviewList === null || reviewList === undefined) return <Loading />;
  
  const settings = {
    dots: true,
    infinite: false,
    speed: 500,
    slidesToShow: 3,
    slidesToScroll: 1,
    initialSlide: 0,
    responsive: [
      {
        breakpoint: 1024,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1,
          infinite: true,
          dots: true
        }
      },
      {
        breakpoint: 600,
        settings: {
          slidesToShow: 2,
          slidesToScroll: 2,
          initialSlide: 2
        }
      },
      {
        breakpoint: 480,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1
        }
      }
    ]
  };

  
  
  return (
    <>
    <Slider {...settings}>
        {reviewList.map((review: ReviewCardInterface) => (
            
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

        ))}
        

    </Slider>

    </>
  );
};

export default NotificationReviewList;
