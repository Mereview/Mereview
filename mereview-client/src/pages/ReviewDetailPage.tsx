import "../styles/css/ReviewDetailPage.css";
import Top from "../components/reviewDetail/Top";
import Detail from "../components/reviewDetail/Detail";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { searchReview, toggleNotificationStatus } from "../api/review";
import Loading from "../components/common/Loading";
import { searchMovieDetail } from "../api/movie";

interface MovieDetail {
  genres: [
    {
      genreId: number;
      genreNumber: number;
      genreName: string;
    }
  ];
  id: string;
  overview: string;
  posterImg: string;
  releaseDate: string;
  title: string;
  voteAverage: number;
}

//디테일 페이지 렌더링 + 버튼수정
const ReviewDetail = () => {
  const [review, setReview] = useState({ backgroundImage: { id: null } });
  const [loading, setLoading] = useState(false);
  const [loading2, setLoading2] = useState(false);
  const [movie, setMovie] = useState<MovieDetail>();
  const { id } = useParams();
  const userId = localStorage.getItem("id");
  const [isOpen, setIsOpen] = useState<string>("none");

  const modalHandler = (event: any) => {
    if (isOpen === "true") {
      setIsOpen("false");
    } else if (isOpen === "none") {
      setIsOpen("true");
    } else {
      setIsOpen("true");
    }
  };
  const navigate = useNavigate();
  // 스크롤 이벤트 핸들러
  const scrolltoTop = () => {
    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  };
  //리뷰불러오기
  useEffect(() => {
    const data = { reviewId: id, loginMemberId: userId };
    const getReviewHandler = () => {
      searchReview(
        data,
        (res) => {
          setReview(res.data.data);
          setLoading(true);
          toggleNotificationStatus(
            data,
            (toggleRes) => {},
            (toggleErr) => {}
          );
          searchMovieDetail(
            res.data.data.movieId,
            (res) => {
              console.log(res.data.data);
              setMovie(res.data.data);
              setLoading2(true);
            },
            (err) => {}
          );
        },
        (err) => {
          navigate("/404");
        }
      );
    };

    getReviewHandler();
  }, [id]);
  const backgroundImageURL =
    review.backgroundImage && review.backgroundImage.id
      ? `${process.env.REACT_APP_API_URL}/image/download/backgrounds/${review.backgroundImage.id}`
      : false;
  const style = backgroundImageURL
    ? {
        backgroundImage: `url(${backgroundImageURL})`,
        filter: "blur(5px)",
        zIndex: "-1",
      }
    : {
        backgroundColor: "rgba(0, 0, 0, 0.6)",
        zIndex: "-1",
      };

  console.log(movie);
  return (
    <div className="section" style={{ backgroundColor: "rgba(0, 0, 0, 0.7)" }}>
      {loading && loading2 ? (
        <div>
          <div className="blurred" style={style}></div>
          <Top review={review} isOpen={isOpen} setIsOpen={setIsOpen} />
          <Detail review={review} setReview={setReview} />
          <button className="topbutton" onClick={scrolltoTop}></button>

          {/* 모달시작 */}
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

              <div className="smallInfo">
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
              </div>

              <div className="overview">
                줄거리:
                <h5>{movie.overview}</h5>
              </div>
            </div>
          </div>
        </div>
      ) : (
        <Loading />
      )}
    </div>
  );
};

export default ReviewDetail;
