import React, { useState, useEffect, useRef } from "react";
import { useSelector } from "react-redux";
import axios from "axios";
import Loading from "../components/common/Loading";
import { searchReviews } from "../api/review";
import { ReviewCardInterface } from "../components/interface/ReviewCardInterface";
import ReviewList from "../components/ReviewList";
import { MovieCardInterface } from "../components/interface/MovieCardInterface";
import MovieList from "../components/MovieList";
import { ReviewSearchInterface } from "../components/interface/ReviewSearchInterface";
import ReviewSearch from "../components/ReviewSearch";
import { ReviewSortInterface } from "../components/interface/ReviewSortInterface";
import ReviewSort from "../components/ReviewSort";
import { SearchConditionInterface } from "../components/interface/SearchConditionInterface";

//import { IconName } from "react-icons/bs"; // 나중에 install 해서 사용할것

/* BoxOffice 데이터 생성 시작 */

const koficKey: string = "f22f6d4bc63521504a75ef52103c4101"; // 나중에 따로 빼기?
const koficDailyBoxofficeURL: string =
  "https://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
const today: Date = new Date(Date.now());
const yesterday: string =
  today.getFullYear() +
  String(today.getMonth() + 1).padStart(2, "0") +
  String(today.getDate() - 1).padStart(2, "0");

const tmdbSearchMovieURL: string = "https://api.themoviedb.org/3/search/movie";
// 토큰 나중에 따로 빼기?
const tmdbAuthToken: string =
  "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjOGI5YmMyNTI5YTg1OTQ2ZTIyYTI4YTE4ZTYxYjc0YyIsInN1YiI6IjY0YWU1ZDA5M2UyZWM4MDBjYmQwMzI0ZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.GFs7ms-36oYPWK7JmBviACJHmAoSyRO0txStPCAtMZM";

type koficMovie = {
  [key: string]: string;
};

const getBoxOfficeMovieNames = async () => {
  const boxOfficeMovieNames: string[] = [];

  try {
    const koficResponse = await axios.get(koficDailyBoxofficeURL, {
      params: {
        key: koficKey,
        targetDt: yesterday,
      },
    });

    const data: koficMovie[] =
      koficResponse.data.boxOfficeResult.dailyBoxOfficeList;
    for (let i = 0; i < 10; i++) {
      boxOfficeMovieNames.push(data[i].movieNm);
    }
    return boxOfficeMovieNames;
  } catch (error) {
    console.log(`Failed to fetch from KOFIC: ${error}`);
    return [];
  }
};

// Use API later
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

const searchMovieOnTmdb = async (movieNames: string[]) => {
  const boxOffices: MovieCardInterface[] = [];

  try {
    for (const movieName of movieNames) {
      const tmdbResponse = await axios.get(tmdbSearchMovieURL, {
        headers: { Authorization: `Bearer ${tmdbAuthToken}` },
        params: { query: movieName, language: "ko", page: 1 },
      });

      const data = tmdbResponse.data.results[0];
      if (typeof data === "undefined") {
        const movie: MovieCardInterface = {
          movieId: null,
          posterImagePath:
            "https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/495px-No-Image-Placeholder.svg.png?20200912122019",
          movieTitle: movieName,
          releaseYear: null,
          movieGenre: [],
        };
        boxOffices.push(movie);
        continue;
      }
      const genre: string[] = [];
      for (const id of data.genre_ids) {
        genre.push(genres[id]);
        if (genre.length === 3) break;
      }

      const movie: MovieCardInterface = {
        movieId: data.id,
        posterImagePath: `https://image.tmdb.org/t/p/w300/${data.poster_path}`,
        movieTitle: data.title,
        releaseYear: data.release_date.substring(0, 4),
        movieGenre: genre,
      };

      boxOffices.push(movie);
    }

    return boxOffices;
  } catch (error) {
    console.log(`Faild to fetch from TMDB: ${error}`);
    return [];
  }
};
/* 박스오피스 데이터 생성 끝*/

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
  const [reviewListState, setReviewListState] = useState<ReviewCardInterface[]>(
    []
  );
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
      const movieNames = await getBoxOfficeMovieNames();
      const movies = await searchMovieOnTmdb(movieNames);
      setMovieList(movies);
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
    else if (searchCriteria === "작성자")
      searchCondition.nickname = searchKeyword;
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
            nickname: review.nickname,
            oneLineReview: review.highlight,
            funnyCount: review.funCount,
            usefulCount: review.usefulCount,
            dislikeCount: review.badCount,
            commentCount: review.commentCount,
            movieTitle: review.movieTitle,
            releaseYear: Number(
              String(review.movieReleaseDate).substring(0, 4)
            ),
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
        setReviewListState((prevReviewList) => [
          ...prevReviewList,
          ...newReviewList,
        ]);
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

    const infScrollObserver = new IntersectionObserver(
      infScrollReloadCallback,
      observerOptions
    );

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
    else if (searchCriteria === "작성자")
      searchCondition.nickname = searchKeyword;
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
              nickname: review.nickname,
              oneLineReview: review.highlight,
              funnyCount: review.funCount,
              usefulCount: review.usefulCount,
              dislikeCount: review.badCount,
              commentCount: review.commentCount,
              movieTitle: review.movieTitle,
              releaseYear: Number(
                String(review.movieReleaseDate).substring(0, 4)
              ),
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
    else if (searchCriteria === "작성자")
      searchCondition.nickname = searchKeyword;
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
              nickname: review.nickname,
              oneLineReview: review.highlight,
              funnyCount: review.funCount,
              usefulCount: review.usefulCount,
              dislikeCount: review.badCount,
              commentCount: review.commentCount,
              movieTitle: review.movieTitle,
              releaseYear: Number(
                String(review.movieReleaseDate).substring(0, 4)
              ),
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
  console.log(reviewListState);
  if (!isFetched) return <Loading />;
  return (
    <>
      <MovieList movieList={movieList} />
      <hr />
      <ReviewSearch searchProps={searchProps} searchSubmit={searchSubmit} />
      <ReviewSort sortProps={sortProps} />
      <ReviewList reviewList={reviewListState} />
      {!infScrollDone ? (
        <div
          style={{ height: "100px", backgroundColor: "white" }}
          ref={infScrollTargetRef}
        ></div>
      ) : (
        <div className="empty-review-list-info">리뷰가 없습니다.</div>
      )}
    </>
  );
};

export default ReviewHome;
