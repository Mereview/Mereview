import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import NotificationReviewList from "../components/NotificationReviewList";
import axios from "axios";
import Loading from "../components/common/Loading";
import { NotificationReviewCardInterface } from "../components/interface/NotificationReviewCardInterface";

import {
  getConfirmedNotifications,
  searchReviews,
  getUnConfirmedNotifications,
} from "../api/review";
import { ReviewCardInterface } from "../components/interface/ReviewCardInterface";
import ReviewList from "../components/ReviewList";
import { MovieCardInterface } from "../components/interface/MovieCardInterface";
import MovieList from "../components/MovieList";
import { ReviewSearchInterface } from "../components/interface/ReviewSearchInterface";
import ReviewSearch from "../components/ReviewSearch";
import { ReviewSortInterface } from "../components/interface/ReviewSortInterface";
import ReviewSort from "../components/ReviewSort";
import { SearchConditionInterface } from "../components/interface/SearchConditionInterface";
import "../styles/css/NotificationHome.css";
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

const enterSearch = (e) => {
  console.log(e);
};

const NotificationHome = () => {
  const [isFetched, setIsFetched] = useState<boolean>(false);
  const [movieList, setMovieList] = useState<MovieCardInterface[]>([]);
  // 검색조건, 검색어, 정렬조건 통합하기
  // 정렬
  const [sortBy, setSortBy] = useState<string>("date");
  const [dateDescend, setDateDescend] = useState<boolean>(true);
  const [recommendDescend, setRecommendDescend] = useState<boolean>(true);
  const [onlyInterest, setOnlyInterest] = useState<boolean>(false);
  const [searchTerm, setSearchTerm] = useState<string>("all");
  const [confirmedReviewList, setConfirmedReviewList] = useState<
    Array<ReviewCardInterface>
  >([]);
  const [unconfirmedReviewList, setUnconfirmedReviewList] = useState<
    Array<ReviewCardInterface>
  >([]);

  // 검색
  const [searchKeyword, setSearchKeyword] = useState<string>("");
  const [searchCriteria, setSearchCriteria] = useState<string>("제목");
  const [emptySearchKeyword, setEmptySearchKeyword] = useState<boolean>(false);
  const [reviewListState, setReviewListState] = useState<ReviewCardInterface[]>(
    []
  );
  const [loginId, setLoginId] = useState<number | null>(null);

  // useSelector를 통해 loginId 값을 가져오기 위한 코드
  const reduxLoginId: number = useSelector((state: any) => state.user.user.id);
  const user = useSelector((state: any) => state.user.user);
  // 로그인 상태에 따라 loginId 값을 설정
  useEffect(() => {
    setLoginId(reduxLoginId);
  }, [reduxLoginId]);

  useEffect(() => {
    const getReviewList = async () => {
      if (loginId != null) {
        await getConfirmedNotifications(
          loginId,
          ({ data }) => {
            const response = data.data.data;
            const confirmedReviewList: NotificationReviewCardInterface[] = [];
            for (const review of response) {
              const reviewData: NotificationReviewCardInterface = {
                notificationId: review.notificationId,
                reviewId: review.reviewId,
                memberId: review.memberId,
                movieId: review.movieId,
                nickname: review.nickname,
                oneLineReview: review.highlight,
                funnyCount: review.funCount,
                usefulCount: review.usefulCount,
                dislikeCount: review.badCount,
                hitsCount: review.hits,
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
                reviewData.backgroundImageId =
                  review.backgroundImageResponse?.id;
              }

              confirmedReviewList.push(reviewData);
            }

            console.log(confirmedReviewList);
            setConfirmedReviewList(confirmedReviewList);
          },
          (error) => {
            console.log(error);
          }
        );

        await getUnConfirmedNotifications(
          loginId,
          ({ data }) => {
            const response = data.data.data;
            const unconfirmedReviewList: NotificationReviewCardInterface[] = [];
            for (const review of response) {
              const reviewData: NotificationReviewCardInterface = {
                notificationId: review.notificationId,
                reviewId: review.reviewId,
                memberId: review.memberId,
                movieId: review.movieId,
                nickname: review.nickname,
                oneLineReview: review.highlight,
                funnyCount: review.funCount,
                usefulCount: review.usefulCount,
                dislikeCount: review.badCount,
                hitsCount: review.hits,
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
                reviewData.backgroundImageId =
                  review.backgroundImageResponse?.id;
              }

              unconfirmedReviewList.push(reviewData);
            }

            console.log(unconfirmedReviewList.length);
            setUnconfirmedReviewList(unconfirmedReviewList);
          },
          (error) => {
            console.log(error);
          }
        );
      }
    };

    getReviewList();
  }, [
    loginId,
    sortBy,
    dateDescend,
    recommendDescend,
    onlyInterest,
    searchTerm,
  ]);

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
  console.log(confirmedReviewList);
  console.log(unconfirmedReviewList);

  return (
    <>
      <div className="mt-3">
        <span className="notification-title ms-5">새로 온 알림</span>
      </div>
      <hr />

      {unconfirmedReviewList.length === 0 ? (
        <div className="notification-title ms-5 mb-5 mt-5 text-center text-secondary">
          새로 온 알림이 없습니다
        </div>
      ) : (
        <NotificationReviewList
          confirmed={false}
          confirmedReviewList={confirmedReviewList}
          unconfirmedReviewList={unconfirmedReviewList}
          setConfirmedReviewList={setConfirmedReviewList}
          setUnconfirmedReviewList={setUnconfirmedReviewList}
        />
      )}
      <span className="notification-title ms-5">확인 된 알림</span>
      <hr />
      {confirmedReviewList.length === 0 ? (
        <div className="notification-title ms-5 mb-5 mt-5 text-center text-secondary">
          알림이 없습니다
        </div>
      ) : (
        <NotificationReviewList
          confirmed={true}
          confirmedReviewList={confirmedReviewList}
          unconfirmedReviewList={unconfirmedReviewList}
          setConfirmedReviewList={setConfirmedReviewList}
          setUnconfirmedReviewList={setUnconfirmedReviewList}
        />
      )}
    </>
  );
};

export default NotificationHome;
