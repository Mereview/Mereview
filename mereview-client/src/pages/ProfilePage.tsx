import { useState, useEffect, MouseEvent } from "react";
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
import Loading from "../components/common/Loading";
import {
  searchMemberInfo,
  searchMemberFollowInfo,
  searchMemberFollowerInfo,
  follow,
} from "../api/members";
import { searchReviews } from "../api/review";
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

const profileBorderColor = {
  NONE: "rgba(0, 0, 0, 1)",
  Bronze: "rgba(148, 97, 61, 1)", // bronze
  Silver: "rgba(143, 143, 143, 1)", // silver
  Gold: "rgba(242, 205, 92, 1)", // gold
  Platinum: "rgba(80, 200, 120, 1)", // platinum
  Diamond: "rgba(112, 209, 244, 1)", // diamond
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
/* 작성 리뷰 더미 데이터 끝 */

/* api test start */
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
      userExpData.sort((a, b) => {
        if (a.exp > b.exp) return -1;
        else if (a.exp < b.exp) return 1;
        else return 0;
      });
      userInfo.highestTier = userExpData[0].tier;

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
  await searchMemberFollowerInfo(
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
  await searchMemberFollowInfo(
    userId,
    ({ data }) => {
      followingCountRenewaler = data.data.length;
    },
    (error) => {
      console.log(error);
    }
  );
};
/* api test end */

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
  const [reviewListState, setReviewListState] = useState<ReviewCardInterface[]>(
    []
  );

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
      }, 관심사만: ${onlyInterest}, ID: ${userId}`
    );

    interface ProfilePageSortParamInterface {
      memberId: number;
      myInterest?: number;
      orderBy?: string;
      orderDir?: string;
      term?: string;
      pageNumber?: number;
    }

    const searchParam: ProfilePageSortParamInterface = {
      memberId: userId,
    };
    if (onlyInterest) searchParam.myInterest = loginId;
    if (sortBy === "recommend") {
      searchParam.orderBy = "POSITIVE";
      searchParam.orderDir = recommendDescend ? "DESC" : "ASC";
    } else if (sortBy === "date") {
      searchParam.orderDir = dateDescend ? "DESC" : "ASC";
    }
    if (searchTerm !== "all") searchParam.term = searchTerm;

    const getReviewList = async () => {
      await searchReviews(
        searchParam,
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
              onClickTitle: handleClickTitle,
            };
            if (review.profileImage?.id) {
              reviewData.profileImageId = review.profileImage?.id;
            }
            if (review.backgroundImageResponse?.id) {
              reviewData.backgroundImageId = review.backgroundImageResponse?.id;
            }

            reviewList.push(reviewData);
          }

          console.log(reviewList);
          setReviewListState(reviewList);
        },
        (error) => {
          console.log(error);
        }
      );
    };

    getReviewList();
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

  if (!isFetched) return <Loading />;
  return (
    <>
      <div className="profile-image-chart-container">
        <div
          className="profile-image-container"
          style={{ borderColor: profileBorderColor[userInfo.highestTier] }}
        >
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
      <ReviewList reviewList={reviewListState} />
    </>
  );
};

export default ProfilePage;
