export interface ReviewCardInterface {
  className?: string | null;
  reviewId: number;
  memberId: string;
  movieId: number;
  nickname: string;
  profileImageId?: number;
  backgroundImageId?: number;
  oneLineReview: string;
  funnyCount: number;
  usefulCount: number;
  dislikeCount: number;
  commentCount: number;
  hitsCount: number;
  movieTitle: string;
  releaseYear: number;
  movieGenre: string[];
  createDate: number | Date;
  recommend: boolean;
}
