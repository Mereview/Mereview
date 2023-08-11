export interface ReviewCardInterface {
  reviewId: number;
  memberId: string;
  nickname: string;
  profileImageId?: number;
  backgroundImageId?: number;
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
}
