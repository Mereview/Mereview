export interface AchievedBadge {
  genre: string;
  rank: "BRONZE" | "SILVER" | "GOLD" | null;
  achievementId: string;
}

export interface ProfileInfoInterface {
  memberId: number | null;
  nickname: string;
  profileImageId: number | null;
  age: number | null;
  gender: "MALE" | "FEMALE" | null; // 1: male, 2: female
  introduction: string | null;
  reviewCount: number; // 리뷰 작성 수
  commentCount: number; // 댓글 작성 수
  followerCount: number;
  followingCount: number;
  followed: boolean;
  highestTier:
    | "BRONZE"
    | "SILVER"
    | "GOLD"
    | "PLATINUM"
    | "DIAMOND"
    | "NONE"
    | null;
  badges: AchievedBadge[];
  joinDate: number | Date;
  todayVisitor: number; // 오늘 방문자 수
  totalVisitor: number; // 총 방문자 수
}

export interface Experience {
  genre: string;
  typeName: "재밌어요" | "유용해요";
  exp: number;
  // expPercent: number;
  tier: "BRONZE" | "SILVER" | "GOLD" | "PLATINUM" | "DIAMOND" | "NONE";
}
