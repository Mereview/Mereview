export interface MovieCardInterface {
  movieId: string;
  posterImagePath: string;
  movieTitle: string;
  releaseYear: number;
  movieGenre: string[];
  onClickPoster: (event: React.MouseEvent<HTMLParagraphElement>) => void;
}
