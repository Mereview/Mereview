import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { Col } from "react-bootstrap";
import { searchMovieDetailReview } from "../api/movie";
import { ReviewCardInterface } from "../components/interface/ReviewCardInterface";
import Loading from "../components/common/Loading";
import "../styles/css/MovieDetail.css";
import ReviewList from "../components/ReviewList";

type MovieKeywordType = {
  keyword: string;
  score: number;
};

interface MovieDetailInterface {
  evaluation: string;
  genres: string;
  movieKeyword: MovieKeywordType[];
  overview: string;
  posterImagePath: string | null;
  releaseDate: number | string;
  title: string;
  voteAverage: number;
}

const movieDetailInfo: MovieDetailInterface = {
  evaluation: "",
  genres: "",
  movieKeyword: [],
  overview: "",
  posterImagePath: null,
  releaseDate: 1970,
  title: "",
  voteAverage: 0,
};

const MovieDetail = () => {
  const { id } = useParams();
  const movieId: number = Number(id);

  const [isFetched, setFetched] = useState<boolean>(false);
  const [movieDetail, setMovieDetail] = useState<MovieDetailInterface | null>(null);

  const [sortBy, setSortBy] = useState<string>("date");
  const [recentReviews, setRecentReviews] = useState<ReviewCardInterface[]>([]);
  const [topReviews, setTopReviews] = useState<ReviewCardInterface[]>([]);

  useEffect(() => {
    if (movieId === null || movieId === undefined || Number.isNaN(movieId)) return;
    const featchData = async () => {
      await searchMovieDetailReview(
        movieId,
        ({ data }) => {
          const response = data.data;
          const recentReviewList: ReviewCardInterface[] = [];
          const topReviewList: ReviewCardInterface[] = [];
          const genreNames: string[] = [];

          movieDetailInfo.evaluation = response.evaluation;
          for (const genre of response.genres) {
            genreNames.push(genre.genreName);
          }
          movieDetailInfo.genres = genreNames.join(". ");
          movieDetailInfo.movieKeyword = response.movieKeyword;
          movieDetailInfo.overview = response.overview;
          if (response.posterImg != "null") {
            movieDetailInfo.posterImagePath = `https://image.tmdb.org/t/p/w300/${response.posterImg}`;
          } else {
            const randomIndex = Math.ceil(Math.random() * 16);
            movieDetailInfo.posterImagePath = `/RandomBackground/random-${randomIndex}.jpg`;
          }
          console.log(movieDetailInfo.posterImagePath);
          for (const review of response.recentReviews) {
            const reviewData: ReviewCardInterface = {
              reviewId: review.reviewId,
              memberId: review.memberId,
              movieId: review.movieId,
              nickname: review.nickname,
              oneLineReview: review.highlight,
              funnyCount: review.funCount,
              usefulCount: review.usefulCount,
              dislikeCount: review.badCount,
              commentCount: review.commentCount,
              movieTitle: review.movieTitle,
              releaseYear: Number(String(review.movieReleaseDate).substring(0, 4)),
              movieGenre: [review.genreResponse.genreName],
              createDate: new Date(review.createdTime),
              recommend: review.movieRecommendType === "YES",
            };
            if (review.profileImage?.id) {
              reviewData.profileImageId = review.profileImage?.id;
            }
            if (review.backgroundImageResponse?.id) {
              reviewData.backgroundImageId = review.backgroundImageResponse?.id;
            }
            recentReviewList.push(reviewData);
          }
          movieDetailInfo.releaseDate = response.releaseDate;
          movieDetailInfo.title = response.title;
          for (const review of response.topReviews) {
            const reviewData: ReviewCardInterface = {
              reviewId: review.reviewId,
              memberId: review.memberId,
              movieId: review.movieId,
              nickname: review.nickname,
              oneLineReview: review.highlight,
              funnyCount: review.funCount,
              usefulCount: review.usefulCount,
              dislikeCount: review.badCount,
              commentCount: review.commentCount,
              movieTitle: review.movieTitle,
              releaseYear: Number(String(review.movieReleaseDate).substring(0, 4)),
              movieGenre: [review.genreResponse.genreName],
              createDate: new Date(review.createdTime),
              recommend: review.movieRecommendType === "YES",
            };
            if (review.profileImage?.id) {
              reviewData.profileImageId = review.profileImage?.id;
            }
            if (review.backgroundImageResponse?.id) {
              reviewData.backgroundImageId = review.backgroundImageResponse?.id;
            } else {
            }
            topReviewList.push(reviewData);
          }
          movieDetailInfo.voteAverage = response.voteAverage;

          setMovieDetail(movieDetailInfo);
          setRecentReviews(recentReviewList);
          setTopReviews(topReviewList);
          setFetched(true);
        },
        (error) => {
          console.log(error);
        }
      );
    };

    featchData();
  }, [movieId]);

  const handleSortByDate = () => {
    if (sortBy === "date") return;
    else setSortBy("date");
  };

  const handleSortByRecommend = () => {
    if (sortBy === "recommend") return;
    else setSortBy("recommend");
  };

  if (!isFetched) return <Loading />;
  return (
    <>
      <div
        className="movie-info-container"
        style={{ backgroundImage: `url(${movieDetail.posterImagePath})` }}
      >
        <div className="info-container">
          <div className="movie-poster">
            <img src={movieDetail.posterImagePath} alt={movieDetail.title} />
          </div>
          <div className="movie-info">
            <div className="movie-title">{movieDetail.title}</div>
            <div className="movie-release-date-genre">
              {movieDetail.releaseDate} | {movieDetail.genres}
            </div>
            <div className="movie-overview">{movieDetail.overview}</div>
          </div>
        </div>
      </div>
      <hr style={{ marginTop: "0px" }} />
      <div>
        <Col className="movie-detail-sub-title">이 영화의 리뷰 (총 {recentReviews.length}개)</Col>
      </div>
      <div className="sort-container">
        <button className={sortBy === "date" ? "selected" : ""} onClick={() => handleSortByDate()}>
          최신순
        </button>
        <button
          className={sortBy === "recommend" ? "selected" : ""}
          onClick={() => handleSortByRecommend()}
        >
          추천순
        </button>
      </div>
      {recentReviews.length === 0 ? (
        <div className="empty-review-list-info">리뷰가 없습니다.</div>
      ) : sortBy === "date" ? (
        <ReviewList reviewList={recentReviews} />
      ) : (
        <ReviewList reviewList={topReviews} />
      )}
    </>
  );
};

export default MovieDetail;
