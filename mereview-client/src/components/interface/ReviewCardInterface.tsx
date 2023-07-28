export interface ReviewCardInterface {
  reviewId: number;
  memberId: string;
  nickname: string;
  profileImagePath: string;
  backgroundImagePath: string;
  oneLineReview: string;
  funnyCount: number;
  usefulCount: number;
  dislikeCount: number;
  commentCount: number;
  movieTitle: string;
  releaseYear: number;
  movieGenre: string[];
  createDate: number | Date;
  recommend: boolean;
  onClickProfile: (event: React.MouseEvent<HTMLParagraphElement>) => void;
  onClickTitle: (event: React.MouseEvent<HTMLParagraphElement>) => void;
}
