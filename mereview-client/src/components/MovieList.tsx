import MovieCard from "./MovieCard";
import { MovieCardInterface } from "./interface/MovieCardInterface";

interface MovieListProps {
  movieList: MovieCardInterface[];
}

/* 정렬 방식에 따라 받는 reviewList가 달라질 것 */
const MovieList = ({ movieList }: MovieListProps) => {
  return (
    <>
      <div className="card-list-wrapper">
        {movieList.map((movie: MovieCardInterface, index: number) => (
          <MovieCard
            key={movie.movieId}
            movieId={movie.movieId}
            posterImagePath={movie.posterImagePath}
            movieTitle={movie.movieTitle}
            releaseYear={movie.releaseYear}
            movieGenre={movie.movieGenre}
            onClickPoster={movie.onClickPoster}
          />
        ))}
      </div>
    </>
  );
};

export default MovieList;
