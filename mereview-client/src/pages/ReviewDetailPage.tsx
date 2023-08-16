import "../styles/css/ReviewDetailPage.css";
import Top from "../components/reviewDetail/Top";
import Detail from "../components/reviewDetail/Detail";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { searchReview, toggleNotificationStatus } from "../api/review";
import Loading from "../components/common/Loading";
//디테일 페이지 렌더링
const ReviewDetail = () => {
  const [review, setReview] = useState({ backgroundImage: { id: null } });
  const [loading, setLoading] = useState(false);
  const { id } = useParams();
  const userId = localStorage.getItem("id");
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
  return (
    <div className="section" style={{ backgroundColor: "rgba(0, 0, 0, 0.7)" }}>
      {loading ? (
        <div>
          <div className="blurred" style={style}></div>
          <Top review={review} />
          <Detail review={review} setReview={setReview} />

          <button className="topbutton" onClick={scrolltoTop}></button>
        </div>
      ) : (
        <Loading />
      )}
    </div>
  );
};

export default ReviewDetail;
