import React from "react";
import { Row, Col } from "react-bootstrap";
import { MovieCardInterface } from "./interface/MovieCardInterface";

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
      <div>
        <img src={posterImagePath} alt={movieTitle} />
        <Row>
          <Col>{movieTitle}</Col>
        </Row>
        <Row>
          <Col>
            <span className="year-genres">
              {releaseYear} | {genres}
            </span>
          </Col>
        </Row>
      </div>
    </>
  );
};

export default MovieCard;
