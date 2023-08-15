import React from "react";
import { Row, Col } from "react-bootstrap";
import { MovieCardInterface } from "./interface/MovieCardInterface";
import "../styles/css/MovieCard.css";

const MovieCard = (props: MovieCardInterface) => {
  const { movieId, posterImagePath, movieTitle, releaseYear, movieGenre } =
    props;

  const genres: string = movieGenre.join(". ");

  const handleMovieCardClick = (
    event: React.MouseEvent<HTMLParagraphElement>
  ) => {
    console.log("Movie Card Clicked", movieId);
  };

  return (
   
      <div className="movie-card" onClick={handleMovieCardClick}>
        <div className="poster-image">
          <img src={posterImagePath} alt={movieTitle} />
        </div>
        <div className="movie-info">
          <div className="movie-title">{movieTitle}</div>
          <div>
            {releaseYear} {genres && `| ${genres}`}
          </div>
        </div>
      </div>

  );
};

export default MovieCard;
