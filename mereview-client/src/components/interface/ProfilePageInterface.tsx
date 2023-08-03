// 나중에 UserInterface에 합치기
export interface ProfileInfoInterface {
  memberId: string;
  nickname: string;
  profileImagePath: string | null;
  age: number | null;
  gender: string | null;
  joinDate: number | Date;
  // reviewNumber: number; // 리뷰 작성 수
  // commentNumber: number; // 댓글 작성 수
  // todayVisitor: number; // 오늘 방문자 수
  // totalVisitor: number; // 총 방문자 수
}

export interface Experience {
  genre: string;
  typeName: "재밌어요" | "유용해요";
  exp: number;
}
