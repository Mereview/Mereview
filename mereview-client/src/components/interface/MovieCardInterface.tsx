export interface MovieCardInterface {
  movieId: string | null;
  posterImagePath: string;
  movieTitle: string;
  releaseYear: number | null;
  movieGenre: string[];
  movieCardClickHandler: (
    event: React.MouseEvent<HTMLParagraphElement>
  ) => void;
}
