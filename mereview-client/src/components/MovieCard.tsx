import React from "react";
import { useNavigate } from "react-router-dom";
import { MovieCardInterface } from "./interface/MovieCardInterface";
import { searchIdByContentId } from "../api/movie";
import "../styles/css/MovieCard.css";

const MovieCard = (props: MovieCardInterface) => {
  const { movieId, posterImagePath, movieTitle, releaseYear, movieGenre } = props;
  const navigate = useNavigate();

  const genres: string = movieGenre.join(". ");

  const handleMovieCardClick = async (event: React.MouseEvent<HTMLParagraphElement>) => {
    await searchIdByContentId(
      Number(movieId),
      ({ data }) => {
        navigate(`/movie/${data.data}`);
      },
      (error) => {
        console.log(error);
      }
    );
  };

  return (
    <div className="movie-card" onClick={handleMovieCardClick}>
      <div className="poster-image">
        <img src={posterImagePath} alt={movieTitle} />
      </div>
      <div className="movie-info">
        <div className="movie-title">{movieTitle}</div>
        <div className="movie-rel-genre">
          {releaseYear} {genres && `| ${genres}`}
        </div>
      </div>
    </div>
  );
};

export default MovieCard;
