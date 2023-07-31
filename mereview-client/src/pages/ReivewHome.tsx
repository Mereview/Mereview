import { Container } from "react-bootstrap";
import ReviewCard from "../components/ReviewCard";
import { ReviewCardInterface } from "../components/interface/ReviewCardInterface";
import ReviewList from "../components/ReviewList";
import MovieList from "../components/MovieList";

//import { IconName } from "react-icons/bs"; // 나중에 install 해서 사용할것

const handleClickProfile = (event: React.MouseEvent<HTMLParagraphElement>) => {
  console.log("Profile Clicked");
};

const handleClickTitle = (event: React.MouseEvent<HTMLParagraphElement>) => {
  console.log("Title Clicked");
};

const handleClickPoster = (event: React.MouseEvent<HTMLParagraphElement>) => {
  console.log("Poster Clicked");
};



//https://react-icons.github.io/react-icons/icons?name=bs
const someReview = {
  reviewId: 12113,
  memberId: "user123",
  nickname: "JohnDoe",
  profileImagePath: "/ReviewCardDummy/dummyprofile.jpg",
  backgroundImagePath: "/ReviewCardDummy/CardBack2.jpg",
  oneLineReview:
    "이것은 한줄평 한줄평 영화 리뷰를 요약하는 한줄평 하지만 두줄이상이 될수도 있는...",
  funnyCount: 10,
  usefulCount: 15,
  dislikeCount: 2,
  commentCount: 5,
  movieTitle: "Example Movie",
  releaseYear: 2023,
  movieGenre: ["애니메이션", "가족", "코미디"],
  createDate: new Date("2022-06-03 07:23:53"),
  recommend: true,
  onClickProfile: handleClickProfile,
  onClickTitle: handleClickTitle,
};

const otherReview = {
  reviewId: 12333,
  memberId: "user123",
  nickname: "JohnDoe",
  profileImagePath: "/ReviewCardDummy/dummyprofile2.jpg",
  backgroundImagePath: "/test.jpg",
  oneLineReview: "리뷰의 내용을 요약하는 한줄평! 얘는 dislike가 99임",
  funnyCount: 10,
  usefulCount: 15,
  dislikeCount: 99,
  commentCount: 5,
  movieTitle: "Example Movie",
  releaseYear: 2023,
  movieGenre: ["액션", "모험", "스릴러"],
  createDate: Date.now(),
  recommend: false,
  onClickProfile: handleClickProfile,
  onClickTitle: handleClickTitle,
};
const dummy = {
  reviewId: 12223,
  memberId: "user123",
  nickname: "JohnD124124oe",
  profileImagePath: "/ReviewCardDummy/dummyprofile2.jpg",
  backgroundImagePath: "/test.jpg",
  oneLineReview: "리뷰의 14내용을 요약하는 한줄평!",
  funnyCount: 10,
  usefulCount: 15,
  dislikeCount: 2,
  commentCount: 5,
  movieTitle: "Example Movie",
  releaseYear: 2023,
  movieGenre: ["액션"],
  createDate: Date.now(),
  recommend: false,
  onClickProfile: handleClickProfile,
  onClickTitle: handleClickTitle,
};
const a = {
  reviewId: 1141223,
  memberId: "us22er123",
  nickname: "JohnDoe",
  profileImagePath: "/ReviewCardDummy/dummyprofile2.jpg",
  backgroundImagePath: "/test.jpg",
  oneLineReview: "리뷰의 내용을 요약하는33 한줄평!",
  funnyCount: 10,
  usefulCount: 15,
  dislikeCount: 2,
  commentCount: 5,
  movieTitle: "Example Movie",
  releaseYear: 2023,
  movieGenre: ["액션", "모험"],
  createDate: Date.now(),
  recommend: false,
  onClickProfile: handleClickProfile,
  onClickTitle: handleClickTitle,
};
const reviewList: ReviewCardInterface[] = [someReview, otherReview, dummy, a];

const ReviewHome = () => {
  return (
    <>
      <ReviewList reviewList={reviewList} />
    </>
  );
};

export default ReviewHome;
