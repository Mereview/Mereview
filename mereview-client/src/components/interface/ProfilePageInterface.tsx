export interface ProfileInfoInterface {
  memberId: string;
  nickname: string;
  profileImagePath: string | null;
  age: number | null;
  gender: 1 | 2 | null; // 1: male, 2: female
  introduction: string | null;
  reviewCount: number; // 리뷰 작성 수
  commentCount: number; // 댓글 작성 수
  followerCount: number;
  followingCount: number;
  joinDate: number | Date;
  // todayVisitor: number; // 오늘 방문자 수
  // totalVisitor: number; // 총 방문자 수
}

export interface Experience {
  genre: string;
  typeName: "재밌어요" | "유용해요";
  exp: number;
}
