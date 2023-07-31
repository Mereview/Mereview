import SlickSlider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import MovieCard from "./MovieCard";
import { MovieCardInterface } from "./interface/MovieCardInterface";

interface MovieListProps {
  movieList: MovieCardInterface[];
}

/* 정렬 방식(일별로)에 따라 받는 movieList가 달라질 것 */
const MovieList = ({ movieList }: MovieListProps) => {
  const sliderItems = movieList.map((movie: MovieCardInterface) => (
    <MovieCard
      key={movie.movieId}
      movieId={movie.movieId}
      posterImagePath={movie.posterImagePath}
      movieTitle={movie.movieTitle}
      releaseYear={movie.releaseYear}
      movieGenre={movie.movieGenre}
      onClickPoster={movie.onClickPoster}
    />
  ));

  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 5,
    slidesToScroll: 5,
    responsive: [
      {
        breakpoint: 1024,
        settings: {
          slidesToShow: 4,
          slidesToScroll: 4,
        },
      },
      {
        breakpoint: 768,
        settings: {
          slidesToShow: 3,
          slidesToScroll: 3,
        },
      },
      {
        breakpoint: 480,
        settings: {
          slidesToShow: 2,
          slidesToScroll: 2,
        },
      },
    ],
  };

  return (
    <>
      <div className="card-list-wrapper">
        <SlickSlider {...settings}>{sliderItems}</SlickSlider>
      </div>
    </>
  );
};

export default MovieList;
