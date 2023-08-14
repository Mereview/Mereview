export interface ReviewDataInterface {
  title: string | null;
  content: string | null;
  highlight: string | null;
  type: string | null;
  memberId?: number | null;
  movieId?: number | null;
  genreId?: number | null;
  keywordRequests: any[];
}
