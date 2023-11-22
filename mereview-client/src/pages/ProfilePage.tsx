import { useState, useEffect, ChangeEvent, useRef } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { userActions } from "../store/user-slice";
import { AxiosError } from "axios";
import { Col, Row } from "react-bootstrap";
import Modal from "react-modal";
import { TextField, List, ListItemButton } from "@mui/material";
import { BsHeart, BsHeartFill, BsPencilSquare, BsPersonFillGear } from "react-icons/bs";
import ExperienceBar from "../components/ExperienceBar";
import BadgeList from "../components/BadgeList";
import ReviewList from "../components/ReviewList";
import {
  Experience,
  ProfileInfoInterface,
  AchievedBadge,
  FollowUserInfo,
} from "../components/interface/ProfilePageInterface";
import { ReviewCardInterface } from "../components/interface/ReviewCardInterface";
import ReviewSort from "../components/ReviewSort";
import { ReviewSortInterface } from "../components/interface/ReviewSortInterface";
import { SearchConditionInterface } from "../components/interface/SearchConditionInterface";
import FollowUser from "../components/FollowUser";
import Loading from "../components/common/Loading";
import {
  searchMemberInfo,
  searchMemberInfoSimple,
  updateMemberIntroduce,
  updateMemberNickname,
  updateMemberInterest,
  searchMemberFollowInfo,
  searchMemberFollowerInfo,
  updateProfilePic,
  follow,
  verify,
  deleteMember,
} from "../api/members";
import { searchReviews } from "../api/review";
import "../styles/css/ProfilePage.css";

interface GenreInfo {
  [id: string]: [string, string];
}

interface InterestInterface {
  genreId: string;
  genreName: string;
}

/* 유저 데이터 생성 시작 */
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
  interests: [],
  introduction: null,
  reviewCount: 0,
  commentCount: 0,
  followerCount: 0,
  followingCount: 0,
  followed: false,
  highestTier: null,
  achievements: [],
  joinDate: new Date(""),
  todayVisitor: 0,
  totalVisitor: 0,
};

const profileBorderColor = {
  NONE: "rgba(0, 0, 0, 1)",
  BRONZE: "rgba(148, 97, 61, 1)",
  SILVER: "rgba(143, 143, 143, 1)",
  GOLD: "rgba(242, 205, 92, 1)",
  PLATINUM: "rgba(80, 200, 120, 1)",
  DIAMOND: "rgba(112, 209, 244, 1)",
};

const profileTier = {
  NONE: 0,
  BRONZE: 1,
  SILVER: 2,
  GOLD: 3,
  PALTINUM: 4,
  DIAMOND: 5,
};

const followListStyle = {
  width: "100%",
  maxWidth: 360,
  maxHeight: 720,
  overflowY: "scroll",
  borderRadius: "15px",
};

const genre: GenreInfo = {
  "1": ["액션", "/interest/action"],
  "2": ["모험", "/interest/adventure"],
  "3": ["애니메이션", "/interest/animation"],
  "4": ["코미디", "/interest/comedy"],
  "5": ["범죄", "/interest/crime"],
  "6": ["다큐멘터리", "/interest/documentary"],
  "7": ["드라마", "/interest/drama"],
  "8": ["가족", "/interest/familly"],
  "9": ["판타지", "/interest/fantasy"],
  "10": ["역사", "/interest/history"],
  "11": ["공포", "/interest/horror"],
  "12": ["음악", "/interest/music"],
  "13": ["미스터리", "/interest/mistery"],
  "14": ["로맨스", "/interest/romance"],
  "15": ["SF", "/interest/SF"],
  "16": ["TV 영화", "/interest/TVmovie"],
  "17": ["스릴러", "/interest/thriller"],
  "18": ["전쟁", "/interest/war"],
  "19": ["서부", "/interest/western"],
};

const userExpData: Experience[] = [];
const userInterestData: InterestInterface[] = [];
const userAchievementData: AchievedBadge[] = [];
/* 유저 데이터 생성 끝 */

/* api start */
let error: AxiosError | null = null;
const getMemberInfo = async (userId: number) => {
  await searchMemberInfo(
    userId,
    ({ data }) => {
      const response = data.data;
      const birthDate = new Date(response.birthDate);
      const ageDiff = Date.now() - birthDate.getTime();
      const ageDate = new Date(ageDiff);
      userExpData.length = 0;
      userInterestData.length = 0;
      userAchievementData.length = 0;

      userInfo.memberId = userId;
      userInfo.nickname = response.nickname;
      userInfo.profileImageId = response.profileImage?.id;
      userInfo.age = Math.abs(ageDate.getUTCFullYear() - 1970);
      userInfo.gender = response.gender;
      for (const interest of response.interests) {
        const interestTemp: InterestInterface = {
          genreId: String(interest.genreId),
          genreName: interest.genreName,
        };
        userInterestData.push(interestTemp);
      }
      for (const achievement of response.achievements) {
        if (achievement.achievementRank === "NONE") continue;
        const achievementTemp: AchievedBadge = {
          genreName: achievement.genreName,
          rank: achievement.achievementRank,
          type: achievement.achievementType,
          count: achievement.achievementCount,
        };
        userAchievementData.push(achievementTemp);
      }
      userAchievementData.sort((a, b) => {
        if (a.rank === b.rank) {
          return b.count - a.count;
        }
        return profileTier[b.rank] - profileTier[a.rank];
      });
      userInfo.interests = userInterestData;
      userInfo.achievements = userAchievementData;
      userInfo.introduction = response.introduce ? response.introduce : "";
      userInfo.followerCount = response.follower;
      userInfo.followingCount = response.following;
      userInfo.todayVisitor = response.todayVisitCount;
      userInfo.totalVisitor = response.totalVisitCount;
      userInfo.joinDate = response.createdTime;
      userInfo.reviewCount = response.reviews;
      userInfo.commentCount = response.commentCount;
      for (const expData of response.tiers) {
        const usefulExp: Experience = {
          genre: expData.genreName,
          typeName: "유용해요",
          exp: expData.usefulExperience,
          expPercent: expData.usefulExperiencePercent,
          tier: expData.usefulTier,
        };
        const funExp: Experience = {
          genre: expData.genreName,
          typeName: "재밌어요",
          exp: expData.funExperience,
          expPercent: expData.funExperiencePercent,
          tier: expData.funTier,
        };
        userExpData.push(usefulExp);
        userExpData.push(funExp);
      }

      userExpData.sort((a, b) => {
        if (a.tier === b.tier) {
          if (a.expPercent === b.expPercent) return b.exp - a.exp;
          return b.expPercent - a.expPercent;
        }
        return profileTier[b.tier] - profileTier[a.tier];
      });

      userInfo.highestTier = userExpData[0].tier;
    },
    (e) => {
      error = e;
      console.log(e);
    }
  );
};

let followFlag: boolean = false;
let followerCountUpdater: number = 0;
const followerListUpdater: FollowUserInfo[] = [];
const getFollowerCount = async (userId: number, loginId: number) => {
  if (userId === loginId) followFlag = true;
  await searchMemberFollowerInfo(
    userId,
    async ({ data }) => {
      followerCountUpdater = data.data.length;
      followerListUpdater.length = 0;
      for (const follower of data.data) {
        if (follower["id"] === loginId) followFlag = true;
        await searchMemberInfoSimple(
          follower["id"],
          ({ data }) => {
            const response = data.data;
            followerListUpdater.push({
              memberId: response.id,
              profileImageId: response.profileImage ? response.profileImage.id : undefined,
              nickname: response.nickname,
            });
          },
          (error) => {
            console.log(error);
          }
        );
      }
    },
    (error) => {
      console.log(error);
    }
  );
};

let followingCountUpdater: number = 0;
const followingListUpdater: FollowUserInfo[] = [];
const getFollowingCount = async (userId: number) => {
  await searchMemberFollowInfo(
    userId,
    async ({ data }) => {
      followingCountUpdater = data.data.length;
      followingListUpdater.length = 0;
      for (const following of data.data) {
        await searchMemberInfoSimple(
          following["id"],
          ({ data }) => {
            const response = data.data;
            followingListUpdater.push({
              memberId: response.id,
              profileImageId: response.profileImage ? response.profileImage.id : undefined,
              nickname: response.nickname,
            });
          },
          (error) => {
            console.log(error);
          }
        );
      }
    },
    (error) => {
      console.log(error);
    }
  );
};
/* api end */

const ProfilePage = () => {
  const loginId: number = useSelector((state: any) => state.user.user.id);
  const { id } = useParams();
  const userId: number = id ? Number(id) : loginId;

  const [isFetched, setIsFetched] = useState<boolean>(false);
  // 검색 정렬
  const [sortBy, setSortBy] = useState<string>("date");
  const [dateDescend, setDateDescend] = useState<boolean>(true);
  const [recommendDescend, setRecommendDescend] = useState<boolean>(true);
  const [onlyInterest, setOnlyInterest] = useState<boolean>(false);
  const [searchTerm, setSearchTerm] = useState<string>("all");
  const [isSearchConditionChanging, setSearchConditionChanging] = useState<boolean>(false);
  // 팔로우 팔로잉
  const [followed, setFollowed] = useState<boolean>(false);
  const [followerCount, setFollowerCount] = useState<number>(0);
  const [followingCount, setFollowingCount] = useState<number>(0);
  const [isFollowerDropdown, setFollowerDropdown] = useState<boolean>(false);
  const [isFollowingDropdown, setFollowingDropdown] = useState<boolean>(false);
  const [followerList, setFollowerList] = useState<FollowUserInfo[]>([]);
  const [followingList, setFollowingList] = useState<FollowUserInfo[]>([]);
  const [followIcon, setFollowIcon] = useState<any>("");
  // 리뷰리스트
  const [reviewListState, setReviewListState] = useState<ReviewCardInterface[]>([]);
  // 자기소개, 프로필 이미지
  const [profileImageHovered, setProfileImageHovered] = useState<boolean>(false);
  const [selectedImage, setSelectedImage] = useState<string | null>(null);
  const [selectedFileData, setSelectedFileData] = useState<File>(null);
  const [nicknameEditing, setNicknameEditing] = useState<boolean>(false);
  const [editedNickname, setEditedNickname] = useState<string>("");
  const [introductionEditing, setIntroductionEditing] = useState<boolean>(false);
  const [editedIntroduction, setEditedIntroduction] = useState<string>("");
  // 무한 스크롤
  const [infScrollPage, setInfScrollPage] = useState<number>(2);
  const [infScrollLoading, setInfScrollLoading] = useState<boolean>(false);
  const [infScrollDone, setInfScrollDone] = useState<boolean>(false);
  // 회원 정보 수정, 탈퇴 모달
  const [isProfileImageModalOpen, setProfileImageModalOpen] = useState<boolean>(false);
  const [isVerifyModalOpen, setVerifyModalOpen] = useState<boolean>(false);
  const [isModifyModalOpen, setModifyModalOpen] = useState<boolean>(false);
  const [isInterestModifyModalLoading, setInterestModifyModalLoading] = useState<boolean>(false);
  const [isInterestModifyModalOpen, setInterestModifyModalOpen] = useState<boolean>(false);
  const [verifyPasswordInput, setVerifyPasswordInput] = useState<string>("");
  const [emptyInput, setEmptyInput] = useState<boolean>(false);
  const [wrongPassword, setWrongPassword] = useState<boolean>(false);
  const [interestModify, setInterestModify] = useState<InterestInterface[]>([]);

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const isSelf = userId !== loginId;
  const verifyRef = useRef(null);
  const infScrollTargetRef = useRef(null);

  const observerOptions = {
    root: null,
    rootMargin: "5px",
    threshold: 0.8,
  };

  const infScrollLoadMore = async () => {
    if (infScrollLoading) return;

    setInfScrollPage((prevPage) => ++prevPage);

    const searchCondition: SearchConditionInterface = {
      memberId: userId,
      pageNumber: infScrollPage,
    };
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
            hitsCount: review.hits,
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
  }, [isFetched, infScrollLoading, isSearchConditionChanging]);

  // useEffect
  useEffect(() => {
    if (userId === null || loginId === null || userId === undefined || loginId === undefined)
      return;
    setIsFetched(false);
    const followCheck = async () => {
      setFollowingDropdown(false);
      setFollowerDropdown(false);
      await getFollowerCount(userId, loginId);
      await getFollowingCount(userId);
    };

    const fetchData = async () => {
      await getMemberInfo(userId);
      if (error !== null) navigate(-1);
      else setIsFetched(true);
    };
    setFollowed(false);
    followCheck();
    fetchData();
  }, [userId, loginId]);

  useEffect(() => {
    setFollowed(followFlag);
    if (followFlag) setFollowIcon(<BsHeartFill color="lightpink" />);
    else setFollowIcon(<BsHeart color="lightpink" />);
  }, [followFlag]);

  useEffect(() => {
    setFollowingCount(followingCountUpdater);
  }, [followingCountUpdater]);

  useEffect(() => {
    setFollowerCount(followerCountUpdater);
  }, [followerCountUpdater]);

  useEffect(() => {
    setFollowerList(followerListUpdater);
  }, [followerListUpdater]);

  useEffect(() => {
    setFollowingList(followingListUpdater);
  }, [followingListUpdater]);

  useEffect(() => {
    if (!isFetched || isSearchConditionChanging) return;
    setSearchConditionChanging(true);
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

    const reviewList: ReviewCardInterface[] = [];
    const getReviewList = async () => {
      await searchReviews(
        searchCondition,
        ({ data }) => {
          const response = data.data.data;
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
              hitsCount: review.hits,
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
          setInfScrollPage(2);
          setSearchConditionChanging(false);
        },
        (error) => {
          console.log(error);
          setSearchConditionChanging(false);
        }
      );
    };

    getReviewList();
  }, [isFetched, sortBy, dateDescend, recommendDescend, onlyInterest, searchTerm]);

  useEffect(() => {
    if (emptyInput || wrongPassword) {
      verifyRef.current.focus();
    }
  }, [emptyInput, wrongPassword]);

  // 인터페이스
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

  // 함수
  const followClicked = async () => {
    if (loginId === userId) return;
    const data: Object = {
      memberId: loginId,
      targetId: userId,
    };

    await follow(
      data,
      ({ data }) => {
        if (data.data.status === "unfollow") {
          followFlag = false;
        } else if (data.data.status === "follow") {
          followFlag = true;
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

  const followingListClicked = async () => {
    if (isFollowingDropdown) setFollowingDropdown(false);
    else {
      setFollowerDropdown(false);
      setFollowingDropdown(true);
    }
  };

  const followerListClicked = async () => {
    if (isFollowerDropdown) setFollowerDropdown(false);
    else {
      setFollowingDropdown(false);
      setFollowerDropdown(true);
    }
  };

  const handelProfileImageSelect = (e) => {
    const file = e.target.files[0];
    const objectURL = URL.createObjectURL(file);
    setSelectedImage(objectURL);
    setSelectedFileData(file);
  };

  const handleEditNicknameClick = () => {
    setEditedNickname(userInfo.nickname);
    setNicknameEditing(true);
  };

  const handleEditNicknameSaveClick = async () => {
    if (editedNickname === "") {
      alert("닉네임을 입력해주세요");
      return;
    }
    const nicknameData: Object = {
      id: loginId,
      nickname: editedNickname,
    };

    await updateMemberNickname(
      nicknameData,
      ({ data }) => {
        if (data.code === 200) {
          userInfo.nickname = editedNickname;
          handleEditNicknameCancelClick();
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };

  const handleEditNicknameCancelClick = () => {
    setNicknameEditing(false);
  };

  const handleEditIntroductionClick = () => {
    setEditedIntroduction(userInfo.introduction);
    setIntroductionEditing(true);
  };

  const handleEditIntroductionSaveClick = async () => {
    const introduceData: Object = {
      id: loginId,
      introduce: editedIntroduction,
    };

    await updateMemberIntroduce(
      introduceData,
      ({ data }) => {
        if (data.code === 200) userInfo.introduction = editedIntroduction;
      },
      (error) => {
        console.log(error);
      }
    );
    setIntroductionEditing(false);
  };

  const handleEditIntroductionCancelClick = () => {
    setIntroductionEditing(false);
  };

  const openProfileImageModal = () => {
    setProfileImageModalOpen(true);
  };

  const closeProfileImageModal = () => {
    setProfileImageModalOpen(false);
  };

  const updateProfileImage = async () => {
    const formData = new FormData();
    formData.append("file", selectedFileData);
    formData.append("memberId", String(loginId));

    await updateProfilePic(
      formData,
      ({ data }) => {
        if (data.code === 200) {
          navigate(0);
          closeProfileImageModal();
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };

  const openVerifyModal = () => {
    setVerifyPasswordInput("");
    setVerifyModalOpen(true);
  };

  const onChangeVerfiyPasswordInput = (event: ChangeEvent<HTMLInputElement>) => {
    setEmptyInput(false);
    setWrongPassword(false);
    setVerifyPasswordInput(event.target.value);
  };

  const closeVerifyModal = () => {
    setVerifyModalOpen(false);
    setEmptyInput(false);
    setWrongPassword(false);
  };

  const verifyPassword = async () => {
    setEmptyInput(false);
    setWrongPassword(false);
    if (verifyPasswordInput === "") {
      setEmptyInput(true);
      return;
    }

    const verifyData: Object = {
      id: loginId,
      password: verifyPasswordInput,
    };

    await verify(
      verifyData,
      ({ data }) => {
        if (data.data) {
          closeVerifyModal();
          openModifyModal();
        } else {
          setWrongPassword(true);
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };

  const verifyOnKeyUp = (e) => {
    if (e.key === "Enter") {
      verifyPassword();
    }
  };

  const openModifyModal = () => {
    setInterestModify([]);
    setInterestModifyModalLoading(true);
    const interestInit = async () => {
      for (const interest of userInfo.interests) {
        setInterestModify((prevInterest: InterestInterface[]) => {
          const genreId: string = interest.genreId;
          const genreName: string = interest.genreName;
          return [...prevInterest, { genreId, genreName }];
        });
      }

      setInterestModifyModalLoading(false);
    };
    interestInit();
    setModifyModalOpen(true);
  };

  const closeModifyModal = () => {
    setModifyModalOpen(false);
  };

  const updateMemberInfo = () => {
    console.log("수정완료");
    setModifyModalOpen(false);
  };

  const withdrawal = async () => {
    if (window.confirm("정말 탈퇴하시게요???? (재가입 안됨)")) {
      await deleteMember(
        loginId,
        ({ data }) => {
          dispatch(userActions.logout());
          alert("Bye..");
          navigate(`/`);
        },
        (error) => {
          console.log(error);
          alert("못도망가 히히");
          closeModifyModal();
        }
      );
    }
  };

  const openInterestModifyModal = () => {
    setInterestModifyModalOpen(true);
  };

  const closeInterestModifyModal = () => {
    setInterestModifyModalOpen(false);
  };

  const onClickInterest = (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    const genreId = event.currentTarget.id;
    const genreName = event.currentTarget.textContent || "";

    setInterestModify((prevInterest: InterestInterface[]) => {
      const isGenreExist = prevInterest.some((item) => item.genreId === genreId);
      if (isGenreExist) {
        return prevInterest.filter((item) => item.genreId !== genreId);
      } else {
        return [...prevInterest, { genreId, genreName }];
      }
    });
  };

  const handelInterestModify = async () => {
    const interestModifyData = {
      id: loginId,
      interests: interestModify,
    };
    await updateMemberInterest(
      interestModifyData,
      ({ data }) => {
        if (data.code === 200) {
          alert("관심장르 변경 성공!");
          closeInterestModifyModal();
          closeModifyModal();
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };

  const formattedCreateDate: Date = new Date(userInfo.joinDate);
  const year: number = formattedCreateDate.getFullYear();
  const month: string = String(formattedCreateDate.getMonth() + 1).padStart(2, "0");
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
            style={{
              width: "450px",
              opacity: userId === loginId && profileImageHovered ? 0.45 : 1,
            }}
            onMouseEnter={() => setProfileImageHovered(true)}
            onMouseLeave={() => setProfileImageHovered(false)}
          />
          {userId === loginId && profileImageHovered && (
            <BsPencilSquare
              className="profile-image-edit-icon"
              onClick={openProfileImageModal}
              onMouseEnter={() => setProfileImageHovered(true)}
              onMouseLeave={() => setProfileImageHovered(false)}
            />
          )}
          {userId === loginId ? (
            <div className="profile-modify-icon-container" onClick={openVerifyModal}>
              <BsPersonFillGear className="modify-icon" />
            </div>
          ) : (
            <></>
          )}
          <div className="follow-info">
            <span className="f-user-span" onClick={followingListClicked}>
              팔로잉: {followingCount}
            </span>
            <span className="f-user-span" onClick={followerListClicked}>
              팔로워: {followerCount}
            </span>
            <span
              className="follow"
              onClick={isSelf ? followClicked : null}
              style={isSelf ? { cursor: "pointer" } : null}
            >
              팔로우 {followIcon}
            </span>
          </div>

          {isFollowerDropdown ? (
            <List sx={followListStyle} aria-label="mailbox folders">
              {followerList.map((user: FollowUserInfo, index: number) => (
                <ListItemButton className="follow-list-button" key={`follower-${index}`}>
                  <FollowUser
                    memberId={user.memberId}
                    profileImageId={user.profileImageId}
                    nickname={user.nickname}
                  />
                </ListItemButton>
              ))}
            </List>
          ) : isFollowingDropdown ? (
            <List sx={followListStyle} aria-label="mailbox folders">
              {followingList.map((user: FollowUserInfo, index: number) => (
                <ListItemButton className="follow-list-button" key={`following-${index}`}>
                  <FollowUser
                    memberId={user.memberId}
                    profileImageId={user.profileImageId}
                    nickname={user.nickname}
                  />
                </ListItemButton>
              ))}
            </List>
          ) : (
            <></>
          )}
        </div>
        <div className="profile-chart-scroll-div">
          <div className="profile-chart-container">
            <ExperienceBar userExpData={userExpData} />
          </div>
        </div>
      </div>
      <div className="profile-info-badge-container">
        <div className="user-info">
          <Row className="nickname-row">
            {nicknameEditing ? (
              <div className="nickname-edit-container">
                <input
                  className="edit-input"
                  value={editedNickname}
                  onChange={(e) => setEditedNickname(e.target.value)}
                  type="text"
                />
                <div className="edit-button">
                  <button onClick={handleEditNicknameSaveClick}>수정</button>
                  <button onClick={handleEditNicknameCancelClick}>취소</button>
                </div>
              </div>
            ) : (
              <>
                <Col className="nickname">
                  <span className="nickname-text">{userInfo.nickname}</span>
                  {userId === loginId ? (
                    <BsPencilSquare className="edit-icon" onClick={handleEditNicknameClick} />
                  ) : null}
                </Col>
              </>
            )}
            <div className="age-gender">
              {userInfo.age && userInfo.age + " "}
              {genderMapping[userInfo.gender] || null}
            </div>
          </Row>
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
                    <button onClick={handleEditIntroductionSaveClick}>수정</button>
                    <button onClick={handleEditIntroductionCancelClick}>취소</button>
                  </div>
                </div>
              </>
            ) : (
              <>
                <Col className="introduction">
                  {userInfo.introduction ? userInfo.introduction : "자기소개가 없습니다."}
                  {userId === loginId ? (
                    <BsPencilSquare className="edit-icon" onClick={handleEditIntroductionClick} />
                  ) : null}
                </Col>
              </>
            )}
          </Row>
          <div className="post-counter-join-date-container">
            <Row>
              <Col className="visitors">
                Visitors: {userInfo.todayVisitor} / {userInfo.totalVisitor}
              </Col>
              <Col className="join-date">Join Date: {joinDateText}</Col>
            </Row>
          </div>
        </div>
        <div className="profile-badge-container">
          <BadgeList badgeListProps={userInfo.achievements} />
        </div>
      </div>
      <div>
        <Col className="profile-review-sub-title">
          {userInfo.nickname}님이 작성한 리뷰 (총 {userInfo.reviewCount}개){" "}
        </Col>
      </div>
      <hr className="review-hr" />
      <ReviewSort sortProps={sortProps} />
      <ReviewList reviewList={reviewListState} />
      {!infScrollDone ? (
        <div style={{ height: "100px", backgroundColor: "white" }} ref={infScrollTargetRef}></div>
      ) : (
        <div className="empty-review-list-info">리뷰가 없습니다.</div>
      )}

      <Modal
        isOpen={isProfileImageModalOpen}
        onRequestClose={closeProfileImageModal}
        contentLabel="Profile Modal"
        className="profile-modal"
      >
        <div
          className="profile-image-edit-container"
          style={{ borderColor: profileBorderColor[userInfo.highestTier] }}
        >
          <img
            src={
              selectedImage
                ? selectedImage
                : userInfo.profileImageId
                ? `${process.env.REACT_APP_API_URL}/image/download/profiles/${userInfo.profileImageId}`
                : defaultProfileImage
            }
            alt="프로필 이미지"
            style={{ width: "250px" }}
          />
        </div>
        <div>
          <input
            type="file"
            accept="image/*"
            className="profile-image-input"
            onChange={handelProfileImageSelect}
          />
        </div>
        <div className="modal-button-box">
          <button onClick={updateProfileImage}>변경</button>
          <button onClick={closeProfileImageModal}>취소</button>
        </div>
      </Modal>

      <Modal
        isOpen={isVerifyModalOpen}
        onRequestClose={closeVerifyModal}
        contentLabel="Verify Modal"
        className="verify-modal"
      >
        <div>비밀번호를 입력해주세요</div>
        <TextField
          inputRef={verifyRef}
          id="verify-password"
          className="verify-password"
          placeholder="Password"
          onChange={onChangeVerfiyPasswordInput}
          value={verifyPasswordInput}
          type="password"
          error={emptyInput || wrongPassword}
          onKeyUp={verifyOnKeyUp}
        />
        <span className="verify-info">
          {emptyInput ? "비밀번호를 입력하세요" : wrongPassword ? "비밀번호가 틀렸습니다." : ""}
        </span>
        <div className="modal-button-box">
          <button onClick={verifyPassword}>확인</button>
          <button onClick={closeVerifyModal}>취소</button>
        </div>
      </Modal>

      <Modal
        isOpen={isModifyModalOpen}
        onRequestClose={closeModifyModal}
        contentLabel="Modify Modal"
        className="modify-modal"
      >
        <div className="modify-button-box">
          <button onClick={openInterestModifyModal}>관심장르수정</button>
          <button onClick={withdrawal}>탈퇴</button>
        </div>
        <div className="modal-button-box">
          <button onClick={closeModifyModal}>취소</button>
        </div>
      </Modal>

      <Modal
        isOpen={isInterestModifyModalOpen}
        onRequestClose={closeInterestModifyModal}
        contentLabel="Interest Modify Modal"
        className="interest-modify-modal"
      >
        <div>어떤 장르에 관심 있으세요??</div>
        {isInterestModifyModalLoading ? (
          <div className="loading-overlay">
            <div className="loading-spinner"></div>
          </div>
        ) : (
          <div className="middle-box">
            {Object.keys(genre).map((id) => (
              <div
                id={id}
                className={`small-box ${
                  interestModify.some((item) => item.genreId === id) ? "selected" : ""
                }`}
                key={id}
                style={{ backgroundImage: `url(${genre[id][1]}.png)` }}
                onClick={onClickInterest}
              >
                <span id="title">{genre[id][0]}</span>
              </div>
            ))}
          </div>
        )}
        <div className="interest-modify-finish">
          <button onClick={handelInterestModify}>
            {`${interestModify.length} 개 선택, 완료하기!`}
          </button>
          <button onClick={closeInterestModifyModal}>취소</button>
        </div>
      </Modal>
    </>
  );
};

export default ProfilePage;
