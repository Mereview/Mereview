import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { AxiosError } from "axios";
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
import {
  searchMemberInfo,
  searchMemberFollowInfo,
  searchMemberFollowerInfo,
  follow,
} from "../api/members";
import "../styles/css/ProfilePage.css";

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

const defaultProfileImage = "/testProfile.gif";

const genderMapping = {
  MALE: "남",
  FEMALE: "여",
};

const userInfo: ProfileInfoInterface = {
  memberId: null,
  nickname: null,
  profileImageId: null,
  age: null,
  gender: null,
  introduction: null,
  reviewCount: 0,
  commentCount: 0,
  followerCount: 0,
  followingCount: 0,
  followed: false,
  highestTier: null,
  badges: dummyBadges,
  joinDate: new Date(""),
  todayVisitor: 0,
  totalVisitor: 0,
};
/* 유저 더미 데이터 생성 끝 */

const userExpData: Experience[] = [];

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
  profileImageId: null,
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
  profileImageId: null,
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
  profileImageId: null,
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
  profileImageId: null,
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
let error: AxiosError | null = null;
const getMemberInfo = async (userId: number) => {
  await searchMemberInfo(
    userId,
    ({ data }) => {
      const response = data.data;
      const birthDate = new Date(response.birthDate);
      const ageDiff = Date.now() - birthDate.getTime();
      const ageDate = new Date(ageDiff);

      userInfo.memberId = userId;
      userInfo.nickname = response.nickname;
      userInfo.profileImageId = response.profileImage?.id;
      userInfo.age = Math.abs(ageDate.getUTCFullYear() - 1970);
      userInfo.gender = response.gender;
      userInfo.introduction = response.introduce;
      userInfo.followerCount = response.follower;
      userInfo.followingCount = response.following;
      userInfo.todayVisitor = response.todayVisitCount;
      userInfo.totalVisitor = response.totalVisitCount;
      userInfo.joinDate = response.createdTime;
      userInfo.reviewCount = response.reviews.length;
      for (const expData of response.tiers) {
        const usefulExp: Experience = {
          genre: expData.genreName,
          typeName: "유용해요",
          exp: expData.usefulExperience,
          // expPercent: expData.usefulExpPercent,
          tier: expData.usefulTier,
        };
        const funExp: Experience = {
          genre: expData.genreName,
          typeName: "재밌어요",
          exp: expData.funExperience,
          // expPercent: expData.funExpPercent,
          tier: expData.funTier,
        };
        userExpData.push(usefulExp);
        userExpData.push(funExp);
      }

      // userInfo.commentCount = response.commentCount;
    },
    (e) => {
      error = e;
      alert(e.response.data.message);
    }
  );
};

let loginNickname: string | null = null;
let followFlag: boolean = false;
const isFollower = async (userId: number, loginId: number) => {
  await searchMemberFollowInfo(
    userId,
    ({ data }) => {
      for (const follower of data.data) {
        if (follower["nickname"] === loginNickname) {
          followFlag = true;
          break;
        }
      }
    },
    (error) => {
      console.log(error);
    }
  );
};

let followerCountRenewaler: number = 0;
const getFollowerCount = async (userId: number) => {
  await searchMemberFollowInfo(
    userId,
    ({ data }) => {
      followerCountRenewaler = data.data.length;
    },
    (error) => {
      console.log(error);
    }
  );
};

let followingCountRenewaler: number = 0;
const getFollowingCount = async (userId: number) => {
  await searchMemberFollowerInfo(
    userId,
    ({ data }) => {
      followingCountRenewaler = data.data.length;
    },
    (error) => {
      console.log(error);
    }
  );
};
/* api test */

const ProfilePage = () => {
  const loginId: number = useSelector((state: any) => state.user.user.id);
  loginNickname = useSelector((state: any) => state.user.user.nickname);
  const { id } = useParams();
  const userId: number = id ? Number(id) : loginId;

  const [isFetched, setIsFetched] = useState<boolean>(false);
  const [sortBy, setSortBy] = useState<string>("date");
  const [dateDescend, setDateDescend] = useState<boolean>(true);
  const [recommendDescend, setRecommendDescend] = useState<boolean>(true);
  const [onlyInterest, setOnlyInterest] = useState<boolean>(false);
  const [searchTerm, setSearchTerm] = useState<string>("all");
  const [followed, setFollowed] = useState<boolean>(false);
  const [followerCount, setFollowerCount] = useState<number>(0);
  const [followingCount, setFollowingCount] = useState<number>(0);
  const navigate = useNavigate();

  const isSelf = userId !== loginId;
  const followIcon =
    followed || userId === loginId ? <BsHeartFill /> : <BsHeart />;

  useEffect(() => {
    if (userId === null || loginId === null || userId === undefined) return;
    const followCheck = async () => {
      await isFollower(userId, loginId);
      await getFollowerCount(userId);
      await getFollowingCount(userId);
    };

    followCheck();
  }, [userId]);

  useEffect(() => {
    // 유저 정보 저장
    if (userId === null || loginId === null || userId === undefined) return;
    const fetchData = async () => {
      await getMemberInfo(userId);
      if (error !== null) navigate(-1);
      else {
        if (userId !== loginId) {
          await isFollower(userId, loginId);
        }
        setIsFetched(true);
      }
    };

    fetchData();
  }, [userId]);

  useEffect(() => {
    setFollowed(followFlag);
  }, [followFlag]);

  useEffect(() => {
    setFollowingCount(followingCountRenewaler);
  }, [followingCountRenewaler]);

  useEffect(() => {
    setFollowerCount(followerCountRenewaler);
  }, [followerCountRenewaler]);

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

  const followClicked = async () => {
    if (loginId === userId) return;
    const data: Object = {
      memberId: loginId,
      targetId: userId,
    };

    await follow(
      data,
      ({ data }) => {
        if (data.status === "unfollow") {
          setFollowed(false);
        } else if (data.status === "follow") {
          setFollowed(true);
        }
      },
      (error) => {
        console.log(error);
      }
    );

    await getFollowerCount(userId);
    await getFollowingCount(userId);

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
            src={
              userInfo.profileImageId
                ? `${process.env.REACT_APP_API_URL}/image/download/profiles/${userInfo.profileImageId}`
                : defaultProfileImage
            }
            alt="프로필 이미지"
            style={{ width: "450px" }}
          />
          <div className="follow-info">
            <span>팔로잉: {followingCount}</span>
            <span>팔로워: {followerCount}</span>
            <span
              className="follow"
              onClick={isSelf ? followClicked : null}
              style={isSelf ? { cursor: "pointer" } : null}
            >
              팔로우 {followIcon}
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
