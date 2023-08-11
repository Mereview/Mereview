import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { AxiosError } from "axios";
import { Col, Row } from "react-bootstrap";
import {
  BsHeart,
  BsHeartFill,
  BsPencilSquare,
  BsPersonFillGear,
} from "react-icons/bs";
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
import { SearchConditionInterface } from "../components/interface/SearchConditionInterface";
import Loading from "../components/common/Loading";
import {
  searchMemberInfo,
  updateMemberIntroduce,
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
  BRONZE: "rgba(148, 97, 61, 1)", // bronze
  SILVER: "rgba(143, 143, 143, 1)", // silver
  GOLD: "rgba(242, 205, 92, 1)", // gold
  PLATINUM: "rgba(80, 200, 120, 1)", // platinum
  DIAMOND: "rgba(112, 209, 244, 1)", // diamond
};
/* 유저 더미 데이터 생성 끝 */

const userExpData: Experience[] = [];

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
          tier: expData.usefulTier,
        };
        const funExp: Experience = {
          genre: expData.genreName,
          typeName: "재밌어요",
          exp: expData.funExperience,
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
      console.log(response);
      // userInfo.commentCount = response.commentCount;
    },
    (e) => {
      error = e;
      alert(e.response.data.message);
    }
  );
};

let followFlag: boolean = false;
let followerCountUpdater: number = 0;
const getFollowerCount = async (userId: number, loginId: number) => {
  await searchMemberFollowerInfo(
    userId,
    ({ data }) => {
      followerCountUpdater = data.data.length;
      for (const follower of data.data) {
        if (follower["id"] === loginId) {
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

let followingCountUpdater: number = 0;
const getFollowingCount = async (userId: number) => {
  await searchMemberFollowInfo(
    userId,
    ({ data }) => {
      followingCountUpdater = data.data.length;
    },
    (error) => {
      console.log(error);
    }
  );
};
/* api test end */

let reviewListUpdater: ReviewCardInterface[] | null = null;
const ProfilePage = () => {
  const loginId: number = useSelector((state: any) => state.user.user.id);
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
  const [introductionEditing, setIntroductionEditing] =
    useState<boolean>(false);
  const [editedIntroduction, setEditedIntroduction] = useState<string>("");

  const navigate = useNavigate();

  const isSelf = userId !== loginId;
  const followIcon =
    followed || userId === loginId ? <BsHeartFill /> : <BsHeart />;

  useEffect(() => {
    if (
      userId === null ||
      loginId === null ||
      userId === undefined ||
      loginId === undefined
    )
      return;
    const followCheck = async () => {
      await getFollowerCount(userId, loginId);
      await getFollowingCount(userId);
    };

    const fetchData = async () => {
      await getMemberInfo(userId);
      if (error !== null) navigate(-1);
      else setIsFetched(true);
    };

    fetchData();
    followCheck();
  }, [userId, loginId]);

  useEffect(() => {
    setFollowed(followFlag);
  }, [followFlag]);

  useEffect(() => {
    setFollowingCount(followingCountUpdater);
  }, [followingCountUpdater]);

  useEffect(() => {
    setFollowerCount(followerCountUpdater);
  }, [followerCountUpdater]);

  useEffect(() => {
    if (!isFetched) return;
    const searchCondition: SearchConditionInterface = {
      memberId: userId,
    };
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
        },
        (error) => {
          console.log(error);
        }
      );
    };

    getReviewList();
  }, [
    isFetched,
    sortBy,
    dateDescend,
    recommendDescend,
    onlyInterest,
    searchTerm,
  ]);

  useEffect(() => {
    setReviewListState(reviewListUpdater);
  }, [reviewListUpdater]);

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

    await getFollowerCount(userId, loginId);
    await getFollowingCount(userId);

    setFollowed(!followed);
  };

  const openModify = () => {
    console.log(userInfo.memberId);
  };

  const handleEditClick = () => {
    setEditedIntroduction(userInfo.introduction);
    setIntroductionEditing(true);
  };

  const handleEditSaveClick = async () => {
    const introduceData: Object = {
      id: loginId,
      introduce: editedIntroduction,
    };
    await updateMemberIntroduce(
      introduceData,
      ({ data }) => {
        console.log(data);
      },
      (error) => {
        console.log(error);
      }
    );
    setIntroductionEditing(false);
  };

  const handleEditCancelClick = () => {
    setIntroductionEditing(false);
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
          {userId === loginId ? (
            <div className="profile-modify-icon-container" onClick={openModify}>
              <BsPersonFillGear className="modify-icon" />
            </div>
          ) : (
            <></>
          )}
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
            {introductionEditing ? (
              <>
                <div className="introduction">
                  <textarea
                    className="edit-area"
                    value={editedIntroduction}
                    rows={10}
                    onChange={(e) => setEditedIntroduction(e.target.value)}
                  />
                  <div className="edit-button">
                    <button onClick={handleEditSaveClick}>수정</button>
                    <button onClick={handleEditCancelClick}>취소</button>
                  </div>
                </div>
              </>
            ) : (
              <>
                <Col className="introduction">
                  {userInfo.introduction
                    ? userInfo.introduction
                    : "자기소개가 없습니다."}
                  {userId === loginId ? (
                    <BsPencilSquare
                      className="edit-icon"
                      onClick={handleEditClick}
                    />
                  ) : null}
                </Col>
              </>
            )}
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
