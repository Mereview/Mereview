import { useEffect, useState } from "react";
import { Row, Col } from "react-bootstrap";
import MovieCard from "./MovieCard";
import { MovieCardInterface } from "./interface/MovieCardInterface";
import "../styles/css/MovieList.css";

interface MovieListProps {
  movieList: MovieCardInterface[];
}

/* 정렬 방식(일별로)에 따라 받는 movieList가 달라질 것 */
const MovieList = ({ movieList }: MovieListProps) => {
  const [startIndex, setStartIndex] = useState<number>(0);
  const [numberOfMoviesToShow, setNumberOfMoviesToShow] = useState<number>(5);
  const [visibleMovies, setVisibleMovies] = useState<MovieCardInterface[]>([]);

  const calculateVisibleMovies = () => {
    const screenWidth = window.innerWidth;
    // 반응형, 나중에 카드 크기에 맞춰 좀 더 상세하게 고치기
    if (screenWidth >= 1370) setNumberOfMoviesToShow(5);
    else if (screenWidth >= 1120) setNumberOfMoviesToShow(4);
    else if (screenWidth >= 860) setNumberOfMoviesToShow(3);
    else setNumberOfMoviesToShow(2);
  };

  useEffect(() => {
    calculateVisibleMovies();
    if (
      movieList.length != 0 &&
      startIndex + numberOfMoviesToShow > movieList.length
    ) {
      setStartIndex(movieList.length - numberOfMoviesToShow);
    }
    const endIndex = startIndex + numberOfMoviesToShow;
    setVisibleMovies(movieList.slice(startIndex, endIndex));

    window.addEventListener("resize", calculateVisibleMovies);
    return () => {
      window.removeEventListener("resize", calculateVisibleMovies);
    };
  }, [numberOfMoviesToShow, movieList, startIndex]);

  const handleSlideLeft = () => {
    const leftStartIndex =
      startIndex - numberOfMoviesToShow > 0
        ? startIndex - numberOfMoviesToShow
        : 0;

    setStartIndex(leftStartIndex);
  };

  const handleSlideRight = () => {
    const rightStartIndex =
      startIndex + numberOfMoviesToShow < movieList.length
        ? startIndex + numberOfMoviesToShow
        : startIndex;

    setStartIndex(rightStartIndex);
  };

  return (
    <>
      <div>
        <Col className="sub-title">박스 오피스 순위</Col>
      </div>
      <div className="card-list-wrapper">
        {visibleMovies.map((movie: MovieCardInterface) => (
          <MovieCard
            key={movie.movieId}
            movieId={movie.movieId}
            posterImagePath={movie.posterImagePath}
            movieTitle={movie.movieTitle}
            releaseYear={movie.releaseYear}
            movieGenre={movie.movieGenre}
            movieCardClickHandler={movie.movieCardClickHandler}
          />
        ))}
      </div>
      <div>
        <Col className="controls">
          <button className="slide-button left" onClick={handleSlideLeft}>
            &lt;
          </button>
          <button className="slide-button right" onClick={handleSlideRight}>
            &gt;
          </button>
        </Col>
      </div>
    </>
  );
};

export default MovieList;
