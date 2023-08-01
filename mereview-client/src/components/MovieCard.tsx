import React from "react";
import { Row, Col } from "react-bootstrap";
import { MovieCardInterface } from "./interface/MovieCardInterface";
import "../styles/css/MovieCard.css";

const MovieCard = (props: MovieCardInterface) => {
  const {
    movieId,
    posterImagePath,
    movieTitle,
    releaseYear,
    movieGenre,
    onClickPoster,
  } = props;

  const genres: string = movieGenre.join(". ");

  return (
    <>
      <div className="movie-card">
        <div className="poster-image">
          <img src={posterImagePath} alt={movieTitle} />
        </div>
        <Row>
          <Col md={12} className="movie-title">
            {movieTitle}
          </Col>
        </Row>
        <Row>
          <Col>
            <span className="year-genres">
              {releaseYear} {genres && `| ${genres}`}
            </span>
          </Col>
        </Row>
      </div>
    </>
  );
};

export default MovieCard;
