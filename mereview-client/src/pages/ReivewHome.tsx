import React, { useState, useEffect } from "react";
import axios from "axios";
import { ReviewCardInterface } from "../components/interface/ReviewCardInterface";
import ReviewList from "../components/ReviewList";
import { MovieCardInterface } from "../components/interface/MovieCardInterface";
import MovieList from "../components/MovieList";
import { ReviewSearchInterface } from "../components/interface/ReviewSearchInterface";
import ReviewSearch from "../components/ReviewSearch";
import { ReviewSortInterface } from "../components/interface/ReviewSortInterface";
import ReviewSort from "../components/ReviewSort";

//import { IconName } from "react-icons/bs"; // 나중에 install 해서 사용할것

const handleClickProfile = (event: React.MouseEvent<HTMLParagraphElement>) => {
  console.log("Profile Clicked");
};

const handleClickTitle = (event: React.MouseEvent<HTMLParagraphElement>) => {
  console.log("Title Clicked");
};

/* BoxOffice 데이터 생성 시작 */
const handleMovieCardClick = (
  event: React.MouseEvent<HTMLParagraphElement>
) => {
  console.log("Movie Card Clicked");
};

const koficKey: string = "f22f6d4bc63521504a75ef52103c4101"; // 나중에 따로 빼기?
const koficDailyBoxofficeURL: string =
  "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
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
          movieCardClickHandler: handleMovieCardClick,
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
        movieCardClickHandler: handleMovieCardClick,
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

/* 리뷰 더미 데이터 */
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
/* 리뷰 더미 데이터 끝 */

const ReviewHome = () => {
  const [movieList, setMovieList] = useState<MovieCardInterface[]>([]);
  // 검색조건, 검색어, 정렬조건 통합하기
  // 정렬
  const [sortBy, setSortBy] = useState<string>("date");
  const [dateDescend, setDateDescend] = useState<boolean>(true);
  const [recommendDescend, setRecommendDescend] = useState<boolean>(true);
  const [onlyInterest, setOnlyInterest] = useState<boolean>(false);
  const [searchTerm, setSearchTerm] = useState<string>("all");
  // 검색
  const [searchParam, setSearchParam] = useState<string>("");
  const [searchCriteria, setSearchCriteria] = useState<string>("제목");
  const [emptySearchParam, setEmptySearchParam] = useState<boolean>(false);

  useEffect(() => {
    const fetchData = async () => {
      const movieNames = await getBoxOfficeMovieNames();
      const movies = await searchMovieOnTmdb(movieNames);
      setMovieList(movies);
    };
    fetchData();
  }, []);

  useEffect(() => {
    // reload review list
    // 검색조건이 있다면 조건 유지
    // 검색어 공백일땐 reload X
    console.log(
      `Reload!! ${sortBy} ${
        sortBy === "date"
          ? dateDescend
            ? "DESC"
            : "ASC"
          : recommendDescend
          ? "DESC"
          : "ASC"
      }, 조회기간: ${
        searchTerm === "all" ? "전체기간" : searchTerm + "개월"
      }, 관심사만: ${onlyInterest}`
    );
  }, [sortBy, dateDescend, recommendDescend, searchTerm, onlyInterest]);

  const searchProps: ReviewSearchInterface = {
    searchParam: searchParam,
    setSearchParam: setSearchParam,
    searchCriteria: searchCriteria,
    setSearchCriteria: setSearchCriteria,
    emptySearchParam: emptySearchParam,
    setEmptySearchParam: setEmptySearchParam,
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

  const searchSubmit = () => {
    // Review 검색, reviewList 초기화
    // 정렬 기준 초기화 (최신순 내림차순 관심장르 false)
    if (searchParam === "") {
      setEmptySearchParam(true);
    } else {
      console.log(
        `search!! criteria: ${searchCriteria}, param: ${searchParam}`
      );
    }
  };

  // ReviewSearch => Enter키 연결, 아이콘 추가
  // ReviewSearch,
  return (
    <>
      <MovieList movieList={movieList} />
      <hr />
      <ReviewSearch searchProps={searchProps} searchSubmit={searchSubmit} />
      <ReviewSort sortProps={sortProps} />
      <ReviewList reviewList={reviewList} />
    </>
  );
};

export default ReviewHome;
