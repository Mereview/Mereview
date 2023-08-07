import { useState, useEffect } from "react";
import { Col, Row } from "react-bootstrap";
import { BsHeart, BsHeartFill } from "react-icons/bs";
import ExperienceBar from "../components/ExperienceBar";
import BadgeList from "../components/BadgeList";
import ReviewList from "../components/ReviewList";
import {
  Experience,
  ProfileInfoInterface,
  AchievedBadge,
} from "../components/interface/ProfilePageInterface";
import { ReviewCardInterface } from "../components/interface/ReviewCardInterface";
import ReviewSort from "../components/ReviewSort";
import { ReviewSortInterface } from "../components/interface/ReviewSortInterface";
import "../styles/css/ProfilePage.css";
import { getInfo } from "../api/members";

/* 유저 더미 데이터 생성 시작 */
const dummyBadges: AchievedBadge[] = [
  {
    genre: "액션",
    rank: "gold",
    achievementId: "0001",
  },
  {
    genre: "SF",
    rank: "bronze",
    achievementId: "0002",
  },
  {
    genre: "SF",
    rank: "bronze",
    achievementId: "0012",
  },
  {
    genre: "범죄",
    rank: "silver",
    achievementId: "0043",
  },
  {
    genre: "액션",
    rank: "gold",
    achievementId: "0041",
  },
  {
    genre: "액션",
    rank: "gold",
    achievementId: "0231",
  },
  {
    genre: "SF",
    rank: "bronze",
    achievementId: "0072",
  },
  {
    genre: "범죄",
    rank: "silver",
    achievementId: "0053",
  },
  {
    genre: "액션",
    rank: "gold",
    achievementId: "0081",
  },
  {
    genre: "SF",
    rank: "bronze",
    achievementId: "0542",
  },
  {
    genre: "범죄",
    rank: "silver",
    achievementId: "0063",
  },
  {
    genre: "SF",
    rank: "bronze",
    achievementId: "0992",
  },
  {
    genre: "범죄",
    rank: "silver",
    achievementId: "0113",
  },
  {
    genre: "액션",
    rank: "gold",
    achievementId: "0211",
  },
  {
    genre: "SF",
    rank: "bronze",
    achievementId: "0312",
  },
  {
    genre: "범죄",
    rank: "silver",
    achievementId: "0653",
  },
];

const userInfo: ProfileInfoInterface = {
  memberId: "id123123",
  nickname: "닉네임123",
  profileImagePath: "/ReviewCardDummy/dummyprofile.jpg",
  age: 29,
  gender: 1,
  introduction: "자기소개입니다. ^^",
  reviewCount: 4,
  commentCount: 5,
  followerCount: 3,
  followingCount: 5,
  followed: false,
  highestTier: "gold",
  badges: dummyBadges,
  joinDate: new Date("2022-06-03 07:23:53"),
  todayVisitor: 2,
  totalVisitor: 34,
};

const defaultProfileImage = "/defaultProfile.png";

const genderMapping = {
  1: "남",
  2: "여",
};
/* 유저 더미 데이터 생성 끝 */

/* 유저 경험치 더미 데이터 생성 시작 */
const userExpData: Experience[] = [
  { genre: "28", typeName: "재밌어요", exp: 0 },
  { genre: "28", typeName: "유용해요", exp: 0 },
  { genre: "12", typeName: "재밌어요", exp: 0 },
  { genre: "12", typeName: "유용해요", exp: 0 },
  { genre: "16", typeName: "재밌어요", exp: 0 },
  { genre: "16", typeName: "유용해요", exp: 0 },
  { genre: "35", typeName: "재밌어요", exp: 0 },
  { genre: "35", typeName: "유용해요", exp: 0 },
  { genre: "80", typeName: "재밌어요", exp: 0 },
  { genre: "80", typeName: "유용해요", exp: 0 },
  { genre: "99", typeName: "재밌어요", exp: 0 },
  { genre: "99", typeName: "유용해요", exp: 0 },
  { genre: "18", typeName: "재밌어요", exp: 0 },
  { genre: "18", typeName: "유용해요", exp: 0 },
  { genre: "10751", typeName: "재밌어요", exp: 0 },
  { genre: "10751", typeName: "유용해요", exp: 0 },
  { genre: "14", typeName: "재밌어요", exp: 0 },
  { genre: "14", typeName: "유용해요", exp: 0 },
  { genre: "36", typeName: "재밌어요", exp: 0 },
  { genre: "36", typeName: "유용해요", exp: 0 },
  { genre: "27", typeName: "재밌어요", exp: 0 },
  { genre: "27", typeName: "유용해요", exp: 0 },
  { genre: "10402", typeName: "재밌어요", exp: 0 },
  { genre: "10402", typeName: "유용해요", exp: 0 },
  { genre: "9648", typeName: "재밌어요", exp: 0 },
  { genre: "9648", typeName: "유용해요", exp: 0 },
  { genre: "10749", typeName: "재밌어요", exp: 0 },
  { genre: "10749", typeName: "유용해요", exp: 0 },
  { genre: "878", typeName: "재밌어요", exp: 0 },
  { genre: "878", typeName: "유용해요", exp: 0 },
  { genre: "10770", typeName: "재밌어요", exp: 0 },
  { genre: "10770", typeName: "유용해요", exp: 0 },
  { genre: "53", typeName: "재밌어요", exp: 0 },
  { genre: "53", typeName: "유용해요", exp: 0 },
  { genre: "10752", typeName: "재밌어요", exp: 0 },
  { genre: "10752", typeName: "유용해요", exp: 0 },
  { genre: "37", typeName: "재밌어요", exp: 0 },
  { genre: "37", typeName: "유용해요", exp: 0 },
];

const ranNum = () => {
  const num = Math.random() * 600 - 300;
  return Math.floor(num > 0 ? num : 0);
};

const generateData = () => {
  for (const data of userExpData) {
    data.exp = ranNum();
  }
};
/* 유저 경험치 더미 데이터 생성 끝 */

/* 작성 리뷰 더미 데이터 */
const handleClickProfile = (event: React.MouseEvent<HTMLParagraphElement>) => {
  console.log("Profile Clicked");
};

const handleClickTitle = (event: React.MouseEvent<HTMLParagraphElement>) => {
  console.log("Title Clicked");
};

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
/* 작성 리뷰 더미 데이터 끝 */

/* api test */
const id: string = localStorage.getItem("id");

const test = async (userId: number) => {
  await getInfo(
    userId,
    ({ data }) => {
      console.log(data);
    },
    (error) => {
      console.log(error);
    }
  );
};
/* api test */

const ProfilePage = () => {
  generateData();

  const [isFetched, setIsFetched] = useState<boolean>(false);
  const [sortBy, setSortBy] = useState<string>("date");
  const [dateDescend, setDateDescend] = useState<boolean>(true);
  const [recommendDescend, setRecommendDescend] = useState<boolean>(true);
  const [onlyInterest, setOnlyInterest] = useState<boolean>(false);
  const [searchTerm, setSearchTerm] = useState<string>("all");
  const [followed, setFollowed] = useState<boolean>(false);

  useEffect(() => {
    const fetchData = async () => {
      for (var i = 0; i < 100; i++) {
        await test(Number(id));
      }
      setIsFetched(true);
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
  }, [sortBy, dateDescend, recommendDescend, onlyInterest, searchTerm]);

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

  const follow = () => {
    if (followed) {
      console.log("unfollow!");
    } else {
      console.log("follow!");
    }

    setFollowed(!followed);
  };

  const formattedCreateDate: Date = new Date(userInfo.joinDate);
  const year: number = formattedCreateDate.getFullYear();
  const month: string = String(formattedCreateDate.getMonth() + 1).padStart(
    2,
    "0"
  );
  const day: string = String(formattedCreateDate.getDate()).padStart(2, "0");

  const joinDateText = `${year}-${month}-${day}`;

  if (!isFetched) return <>Loading...</>;
  return (
    <>
      <div className="profile-image-chart-container">
        <div className="profile-image-container">
          <img
            src={userInfo.profileImagePath || defaultProfileImage}
            alt="프로필 이미지"
            style={{ width: "450px" }}
          />
          <div className="follow-info">
            <span>팔로잉: {userInfo.followerCount}</span>
            <span>팔로워: {userInfo.followingCount}</span>
            <span className="follow" onClick={follow}>
              팔로우 {followed ? <BsHeartFill /> : <BsHeart />}
            </span>
          </div>
        </div>
        <div className="profile-chart-scroll-div">
          <div className="profile-chart-container">
            <ExperienceBar userExpData={userExpData} />
          </div>
        </div>
      </div>
      <div className="profile-info-badge-container">
        <div className="user-info">
          <Row>
            <Col className="nickname">{userInfo.nickname}</Col>
            <Col className="age-gender">
              {userInfo.age && userInfo.age + " "}
              {genderMapping[userInfo.gender] || null}
            </Col>
          </Row>
          <hr style={{ marginTop: "1px", marginBottom: "10px" }} />
          <Row>
            <Col className="introduction">{userInfo.introduction}</Col>
          </Row>
          <div className="post-counter-join-date-container">
            <Row>
              <Col className="post-counter">
                작성 리뷰: {userInfo.reviewCount} / 작성 댓글:{" "}
                {userInfo.commentCount}
              </Col>
            </Row>
            <Row>
              <Col className="visitors">
                방문자) 오늘: {userInfo.todayVisitor} / 전체:{" "}
                {userInfo.totalVisitor}
              </Col>
              <Col className="join-date">가입일: {joinDateText}</Col>
            </Row>
          </div>
        </div>
        <div className="profile-badge-container">
          <BadgeList badgeListProps={userInfo.badges} />
        </div>
      </div>
      <hr />
      <div>
        <Col className="sub-title">작성한 리뷰</Col>
      </div>
      <ReviewSort sortProps={sortProps} />
      <ReviewList reviewList={reviewList} />
    </>
  );
};

export default ProfilePage;
