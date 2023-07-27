import { Container } from "react-bootstrap";
import ReviewCard from "../components/ReviewCard";

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
};

const handleClickProfile = (event: React.MouseEvent<HTMLParagraphElement>) => {
  console.log("Profile Clicked");
};

const handleClickTitle = (event: React.MouseEvent<HTMLParagraphElement>) => {
  console.log("Title Clicked");
};

const reviewList = [someReview, otherReview];
const a = 1;

const ReviewHome = () => {
  return (
    <>
      <Container>
        <ReviewCard
          reviewId={otherReview.reviewId}
          memberId={otherReview.memberId}
          nickname={otherReview.nickname}
          profileImagePath={otherReview.profileImagePath}
          backgroundImagePath={otherReview.backgroundImagePath}
          funnyCount={otherReview.funnyCount}
          usefulCount={otherReview.usefulCount}
          dislikeCount={otherReview.dislikeCount}
          commentCount={otherReview.commentCount}
          movieTitle={otherReview.movieTitle}
          releaseYear={otherReview.releaseYear}
          movieGenre={otherReview.movieGenre}
          createDate={otherReview.createDate}
          recommend={otherReview.recommend}
          onClickProfile={handleClickProfile}
          onClickTitle={handleClickTitle}
        />

        <ReviewCard
          reviewId={someReview.reviewId}
          memberId={someReview.memberId}
          nickname={someReview.nickname}
          profileImagePath={someReview.profileImagePath}
          backgroundImagePath={someReview.backgroundImagePath}
          funnyCount={someReview.funnyCount}
          usefulCount={someReview.usefulCount}
          dislikeCount={someReview.dislikeCount}
          commentCount={someReview.commentCount}
          movieTitle={someReview.movieTitle}
          releaseYear={someReview.releaseYear}
          movieGenre={someReview.movieGenre}
          createDate={someReview.createDate}
          recommend={someReview.recommend}
          onClickProfile={handleClickProfile}
          onClickTitle={handleClickTitle}
        />
      </Container>
    </>
  );
};

export default ReviewHome;
