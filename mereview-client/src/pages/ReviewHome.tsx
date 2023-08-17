import React, { useState, useEffect, useRef } from "react";
import { useSelector } from "react-redux";
import axios from "axios";
import Loading from "../components/common/Loading";
import { searchReviews } from "../api/review";
import { getPopularMovies } from "../api/movie";
import { ReviewCardInterface } from "../components/interface/ReviewCardInterface";
import ReviewList from "../components/ReviewList";
import { MovieCardInterface } from "../components/interface/MovieCardInterface";
import MovieList from "../components/MovieList";
import { ReviewSearchInterface } from "../components/interface/ReviewSearchInterface";
import ReviewSearch from "../components/ReviewSearch";
import { ReviewSortInterface } from "../components/interface/ReviewSortInterface";
import ReviewSort from "../components/ReviewSort";
import { SearchConditionInterface } from "../components/interface/SearchConditionInterface";
import "../styles/css/ReviewHome.css";

const genres: Record<string, string> = {
  "28": "액션",
  "12": "모험",
  "16": "애니메이션",
  "35": "코미디",
  "80": "범죄",
  "99": "다큐멘터리",
  "18": "드라마",
  "10751": "가족",
  "14": "판타지",
  "36": "역사",
  "27": "공포",
  "10402": "음악",
  "9648": "미스터리",
  "10749": "로맨스",
  "878": "SF",
  "10770": "TV 영화",
  "53": "스릴러",
  "10752": "전쟁",
  "37": "서부",
};

const popularMovies: MovieCardInterface[] = [];
const getPopularMovieIds = async (page: number) => {
  popularMovies.length = 0;
  await getPopularMovies(
    page,
    ({ data }) => {
      const results = data.results;
      for (const movieData of results) {
        const genre: string[] = [];
        for (const id of movieData.genre_ids) {
          genre.push(genres[id]);
          if (genre.length === 3) break;
        }

        const movie: MovieCardInterface = {
          movieId: movieData.id,
          posterImagePath: `https://image.tmdb.org/t/p/w300/${movieData.poster_path}`,
          movieTitle: movieData.title,
          releaseYear: movieData.release_date.substring(0, 4),
          movieGenre: genre,
        };

        popularMovies.push(movie);
      }
    },
    (error) => {
      console.log(error);
    }
  );
};
/* 트렌딩 무비 데이터 생성 끝*/

const enterSearch = (e) => {
  console.log(e);
};

const ReviewHome = () => {
  const loginId: number = useSelector((state: any) => state.user.user.id);

  const [isFetched, setIsFetched] = useState<boolean>(false);
  const [movieList, setMovieList] = useState<MovieCardInterface[]>([]);
  // 검색조건, 검색어, 정렬조건 통합하기
  // 정렬
  const [sortBy, setSortBy] = useState<string>("date");
  const [dateDescend, setDateDescend] = useState<boolean>(true);
  const [recommendDescend, setRecommendDescend] = useState<boolean>(true);
  const [onlyInterest, setOnlyInterest] = useState<boolean>(false);
  const [searchTerm, setSearchTerm] = useState<string>("all");
  // 검색
  const [searchKeyword, setSearchKeyword] = useState<string>("");
  const [searchCriteria, setSearchCriteria] = useState<string>("제목");
  const [emptySearchKeyword, setEmptySearchKeyword] = useState<boolean>(false);
  const [reviewListState, setReviewListState] = useState<ReviewCardInterface[]>([]);
  // 무한 스크롤
  const [infScrollPage, setInfScrollPage] = useState<number>(2);
  const [infScrollLoading, setInfScrollLoading] = useState<boolean>(false);
  const [infScrollDone, setInfScrollDone] = useState<boolean>(false);

  const infScrollTargetRef = useRef(null);

  const observerOptions = {   
    root: null,
    rootMargin: "5px",
    threshold: 0.8,
  };

  useEffect(() => {
    const fetchData = async () => {
      await getPopularMovieIds(1);
      setMovieList(popularMovies);
      setIsFetched(true);
    };
    fetchData();
  }, []);

  const infScrollLoadMore = async () => {
    if (infScrollLoading) return;

    setInfScrollPage((prevPage) => ++prevPage);

    const searchCondition: SearchConditionInterface = {
      pageNumber: infScrollPage,
    };

    if (searchCriteria === "제목") searchCondition.title = searchKeyword;
    else if (searchCriteria === "작성자") searchCondition.nickname = searchKeyword;
    else {
      console.log("검색 기준 에러!!");
      return;
    }

    if (onlyInterest) searchCondition.myInterest = loginId;
    if (sortBy === "recommend") {
      searchCondition.orderBy = "POSITIVE";
      searchCondition.orderDir = recommendDescend ? "DESC" : "ASC";
    } else if (sortBy === "date") {
      searchCondition.orderDir = dateDescend ? "DESC" : "ASC";
    }
    if (searchTerm !== "all") searchCondition.term = searchTerm;

    await searchReviews(
      searchCondition,
      ({ data }) => {
        const response = data.data.data;
        if (response.length === 0) {
          setInfScrollDone(true);
          return;
        }
        const newReviewList = [];
        for (const review of response) {
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
          newReviewList.push(reviewData);
        }
        setReviewListState((prevReviewList) => [...prevReviewList, ...newReviewList]);
        setInfScrollLoading(false);
      },
      (error) => {
        setInfScrollLoading(false);
        console.log(error);
      }
    );
  };

  useEffect(() => {
    if (!isFetched || infScrollDone) return;

    const infScrollReloadCallback = async (entries) => {
      if (!infScrollLoading && entries[0].isIntersecting) {
        setInfScrollLoading(true);
        await infScrollLoadMore();
      }
    };

    const infScrollObserver = new IntersectionObserver(infScrollReloadCallback, observerOptions);

    infScrollObserver.observe(infScrollTargetRef.current);

    return () => {
      infScrollObserver.disconnect();
    };
  }, [isFetched, infScrollLoading]);

  const searchSubmit = () => {
    if (searchKeyword === "") {
      setEmptySearchKeyword(true);
      return;
    }

    interface ReviewHomePageSearchParamInterface {
      nickname?: string;
      title?: string;
    }

    const searchCondition: ReviewHomePageSearchParamInterface = {};

    if (searchCriteria === "제목") searchCondition.title = searchKeyword;
    else if (searchCriteria === "작성자") searchCondition.nickname = searchKeyword;
    else {
      console.log("검색 기준 에러!!");
      return;
    }

    setSortBy("date");
    setDateDescend(true);
    setRecommendDescend(true);
    setOnlyInterest(false);
    setSearchTerm("all");

    const getReviewList = async () => {
      await searchReviews(
        searchCondition,
        ({ data }) => {
          const response = data.data.data;
          const reviewList: ReviewCardInterface[] = [];
          for (const review of response) {
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

            reviewList.push(reviewData);
          }

          setInfScrollDone(false);
          setReviewListState(reviewList);
          setInfScrollPage(2);
          setInfScrollLoading(false);
        },
        (error) => {
          console.log(error);
        }
      );
    };

    getReviewList();
  };

  useEffect(() => {
    const searchCondition: SearchConditionInterface = {};

    if (searchCriteria === "제목") searchCondition.title = searchKeyword;
    else if (searchCriteria === "작성자") searchCondition.nickname = searchKeyword;
    else {
      console.log("검색 기준 에러!!");
      return;
    }

    if (onlyInterest) searchCondition.myInterest = loginId;
    if (sortBy === "recommend") {
      searchCondition.orderBy = "POSITIVE";
      searchCondition.orderDir = recommendDescend ? "DESC" : "ASC";
    } else if (sortBy === "date") {
      searchCondition.orderDir = dateDescend ? "DESC" : "ASC";
    }
    if (searchTerm !== "all") searchCondition.term = searchTerm;

    const getReviewList = async () => {
      await searchReviews(
        searchCondition,
        ({ data }) => {
          const response = data.data.data;
          const reviewList: ReviewCardInterface[] = [];
          for (const review of response) {
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

            reviewList.push(reviewData);
          }

          setReviewListState(reviewList);
          setInfScrollDone(false);
          setInfScrollLoading(false);
          setInfScrollPage(2);
        },
        (error) => {
          console.log(error);
        }
      );
    };

    getReviewList();
  }, [sortBy, dateDescend, recommendDescend, onlyInterest, searchTerm]);

  const searchProps: ReviewSearchInterface = {
    searchKeyword: searchKeyword,
    setSearchKeyword: setSearchKeyword,
    searchCriteria: searchCriteria,
    setSearchCriteria: setSearchCriteria,
    emptySearchKeyword: emptySearchKeyword,
    setEmptySearchKeyword: setEmptySearchKeyword,
  };

  const sortProps: ReviewSortInterface = {
    sortBy: sortBy,
    setSortBy: setSortBy,
    dateDescend: dateDescend,
    setDateDescend: setDateDescend,
    recommendDescend: recommendDescend,
    setRecommendDescend: setRecommendDescend,
    searchTerm: searchTerm,
    setSearchTerm: setSearchTerm,
    onlyInterest: onlyInterest,
    setOnlyInterest: setOnlyInterest,
  };

  if (!isFetched) return <Loading />;
  return (
    <>
      <div className="movieList-section">
        <MovieList movieList={movieList} />
      </div>

      <ReviewSearch searchProps={searchProps} searchSubmit={searchSubmit} />
      <ReviewSort sortProps={sortProps} />
      <ReviewList reviewList={reviewListState} />
      {!infScrollDone ? (
        <div style={{ height: "200px", backgroundColor: "white" }} ref={infScrollTargetRef}></div>
      ) : (
        <div className="empty-review-list-info">리뷰가 없습니다.</div>
      )}
    </>
  );
};

export default ReviewHome;
