import "../../styles/css/Top.css";
import WordCloud from "react-d3-cloud";
import { Button } from "../common/index";
import { useState, useEffect } from "react";
import { searchMovieDetail } from "../../api/movie";
import { reverse } from "dns";

interface MovieInterface {
  id: number | null;
  overview: string | null;
  posterImg: string | null;
  releaseDate: string | null;
  title: string | null;
  voteAverage: number | null;
  genres: [
    {
      genreId: number | null;
      genreNumber: number | null;
      genreName: string | null;
    }
  ];
}

const Top = ({ review }: any) => {
  const [isOpen, setIsOpen] = useState<string>("none");
  const [movie, setMovie] = useState<MovieInterface>({
    id: null,
    overview: null,
    posterImg: null,
    releaseDate: null,
    title: null,
    voteAverage: null,
    genres: null,
  });
  const [loading, setLoading] = useState(false);
  const words = review.keywords.map((word) => ({
    text: word.keywordName,
    value: word.keywordWeight / 20,
  }));
  const profileImageURL = review.profileImage?.id
    ? `${process.env.REACT_APP_API_URL}/image/download/profiles/${review.profileImage.id}`
    : "/testProfile.gif";
  const modalHandler = (event: any) => {
    if (isOpen === "true") {
      setIsOpen("false");
    } else if (isOpen === "none") {
      setIsOpen("true");
    } else {
      setIsOpen("true");
    }
  };
  useEffect(() => {
    const movieId = review.movieId;
    const getMovieDetail = () => {
      searchMovieDetail(
        movieId,
        (res) => {
          setMovie(res.data.data);
          setLoading(true);
        },
        (err) => {
          console.log(err, ": 영화를 불러오지 못했습니다.");
        }
      );
    };
    getMovieDetail();
  }, []);
  return (
    <div className="total">
      <div className="leftInfo">
        <h1>"{review.reviewHighlight}"</h1>
        <div className="movieTitle">
          <h2>{review.movieTitle}</h2>{" "}
          <Button styles="btn-third" text="영화정보" onClick={modalHandler} />
        </div>
        <p>
          {review.movieReleaseDate} | {review.genre.genreName}
        </p>
        <div className="userInfo">
          <img src={profileImageURL} alt="작성자프로필이미지" />
          <p className="nickname">
            <a
              href={`/profile/${review.memberId}`}
              style={{ textDecoration: "none" }}
            >
              {review.nickname}
            </a>
          </p>
        </div>
      </div>
      <div className="rightInfo">
        <img
          src={`${
            review.movieEvaluatedType === "YES"
              ? "/ddabong.png"
              : "/reverseDdabong.png"
          }`}
          alt="따봉"
        />

        <WordCloud
          data={words}
          width={300}
          height={330}
          font="Times"
          fontStyle="italic"
          fontWeight="bold"
          fontSize={(word) => word.value * 10}
          rotate={(word) => word.value % 360}
          spiral="archimedean"
          padding={5}
          random={Math.random}
          fill={() => "white"}
          onWordClick={(event, d) => {}}
          onWordMouseOver={(event, d) => {}}
          onWordMouseOut={(event, d) => {}}
        />
      </div>
      {loading && (
        <div
          className={`openModal ${
            isOpen === "none" ? "" : isOpen === "true" ? "open" : "close"
          }`}
        >
          <div className="closebutton col-1">
            <button onClick={modalHandler}>
              {isOpen === "true" ? ">>" : "<<"}
            </button>
          </div>

          <div className="Modal">
            <div className="blank"></div>
            <div className="image">
              <img
                src={`https://image.tmdb.org/t/p/w300/${movie.posterImg}`}
                alt="포스터이미지"
              />
            </div>
            <div className="modalTitle">
              <h2>{movie.title}</h2>
              <h5>영화평점: {movie.voteAverage}</h5>
            </div>
            <div className="modalSubTitle">
              <div>
                {movie.genres.map((item) => (
                  <span key={item.genreId}>{item.genreName}, </span>
                ))}
              </div>
              <h5>개봉일: {movie.releaseDate}</h5>
            </div>
            <div className="overview">
              줄거리:
              <h5>{movie.overview}</h5>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Top;
