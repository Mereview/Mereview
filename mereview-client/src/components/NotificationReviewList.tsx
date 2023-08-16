import { Container } from "react-bootstrap";
import ReviewCard from "../components/ReviewCard";
import { ReviewCardInterface } from "../components/interface/ReviewCardInterface";
import NotificationReviewCard from "./NotificationReviewCard";
import { NotificationReviewListProps } from "./interface/NotificationReviewListProps";
import Loading from "./common/Loading";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import "../styles/css/ReviewList.css";
import { NotificationReviewCardInterface } from "./interface/NotificationReviewCardInterface";



/* 정렬 방식에 따라 받는 reviewList가 달라질 것 */
const NotificationReviewList = ({ unconfirmedReviewList, confirmedReviewList, setConfirmedReviewList, setUnconfirmedReviewList, confirmed }: NotificationReviewListProps) => {
  if (unconfirmedReviewList === null || confirmedReviewList === undefined) return <Loading />;

  const settings = {
    dots: false,
    infinite: false,
    speed: 500,
    slidesToShow: 3,
    slidesToScroll: 1,
    initialSlide: 0,
    responsive: [
      {
        breakpoint: 1600,
        settings: {
          slidesToShow: 2,
          slidesToScroll: 1,
          infinite: true,
          dots: true,
        },
      },
      {
        breakpoint: 1200,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1,
          initialSlide: 1,
        },
      },
      {
        breakpoint: 480,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1,
        },
      },
    ],
  };

  return (
    <div className="mx-5">
      <Slider className="notification-slider" {...settings}>
        {confirmed ? confirmedReviewList.map((review: NotificationReviewCardInterface) => (
          <NotificationReviewCard
          notificationId={review.notificationId}  
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
            movieTitle={review.movieTitle}
            releaseYear={review.releaseYear}
            movieGenre={review.movieGenre}
            createDate={review.createDate}
            recommend={review.recommend}
            confirmed={true} 
            confirmedReviewList={confirmedReviewList} 
            unconfirmedReviewList={unconfirmedReviewList} 
            setConfirmedReviewList={setConfirmedReviewList} 
            setUnconfirmedReviewList={setUnconfirmedReviewList}
          />
        )) : unconfirmedReviewList.map((review: NotificationReviewCardInterface) => (
          <NotificationReviewCard
          notificationId={review.notificationId}  
  
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
            movieTitle={review.movieTitle}
            releaseYear={review.releaseYear}
            movieGenre={review.movieGenre}
            createDate={review.createDate}
            recommend={review.recommend}
            confirmed={false} 
            confirmedReviewList={confirmedReviewList} 
            unconfirmedReviewList={unconfirmedReviewList} 
            setConfirmedReviewList={setConfirmedReviewList} 
            setUnconfirmedReviewList={setUnconfirmedReviewList}
          />
        ))}
      </Slider>
    </div>
  );
};

export default NotificationReviewList;
