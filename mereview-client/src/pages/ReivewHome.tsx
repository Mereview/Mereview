import { Container } from "react-bootstrap";
import ReviewCard from "../components/ReviewCard";
import { ReviewCardInterface } from "../components/interface/ReviewCardInterface";
import ReviewList from "../components/ReviewList";

const handleClickProfile = (event: React.MouseEvent<HTMLParagraphElement>) => {
  console.log("Profile Clicked");
};

const handleClickTitle = (event: React.MouseEvent<HTMLParagraphElement>) => {
  console.log("Title Clicked");
};

const someReview = {
  reviewId: 123,
  memberId: "user123",
  nickname: "JohnDoe",
  profileImagePath: "profile.jpg",
  backgroundImagePath: "/CardBack2.jpg",
  funnyCount: 10,
  usefulCount: 15,
  dislikeCount: 2,
  commentCount: 5,
  movieTitle: "Example Movie",
  releaseYear: 2023,
  movieGenre: "Action",
  createDate: new Date("2022-06-03 07:23:53"),
  recommend: true,
  onClickProfile: handleClickProfile,
  onClickTitle: handleClickTitle,
};

const otherReview = {
  reviewId: 123,
  memberId: "user123",
  nickname: "JohnDoe",
  profileImagePath: "profile.jpg",
  backgroundImagePath: "/test.jpg",
  funnyCount: 10,
  usefulCount: 15,
  dislikeCount: 2,
  commentCount: 5,
  movieTitle: "Example Movie",
  releaseYear: 2023,
  movieGenre: "Action",
  createDate: Date.now(),
  recommend: false,
  onClickProfile: handleClickProfile,
  onClickTitle: handleClickTitle,
};

const reviewList: ReviewCardInterface[] = [
  someReview,
  otherReview,
  someReview,
  otherReview,
];

const ReviewHome = () => {
  return (
    <>
      <Container>
        <ReviewList reviewList={reviewList} />
      </Container>
    </>
  );
};

export default ReviewHome;
