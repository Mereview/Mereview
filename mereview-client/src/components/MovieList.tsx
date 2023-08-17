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
    if (screenWidth >= 1860) setNumberOfMoviesToShow(6);
    else if (screenWidth >= 1470) setNumberOfMoviesToShow(5);
    else if (screenWidth >= 1140) setNumberOfMoviesToShow(4);
    else if (screenWidth >= 780) setNumberOfMoviesToShow(3);
    else if (screenWidth >= 576) setNumberOfMoviesToShow(2);
    else setNumberOfMoviesToShow(1);
  };

  useEffect(() => {
    calculateVisibleMovies();
    if (movieList.length != 0 && startIndex + numberOfMoviesToShow > movieList.length) {
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
      startIndex - numberOfMoviesToShow > 0 ? startIndex - numberOfMoviesToShow : 0;

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
      <div className="movie-list-sub-title">유행하는 영화</div>
      <div className="movie-card-list-wrapper">
        {visibleMovies.map((movie: MovieCardInterface) => (
          <MovieCard
            key={movie.movieId}
            movieId={movie.movieId}
            posterImagePath={movie.posterImagePath}
            movieTitle={movie.movieTitle}
            releaseYear={movie.releaseYear}
            movieGenre={movie.movieGenre}
          />
        ))}
      </div>
      <div className="controls">
        <button className="slide-button" onClick={handleSlideLeft}>
          이전
        </button>
        <button className="slide-button" onClick={handleSlideRight}>
          다음
        </button>
      </div>
    </>
  );
};

export default MovieList;
