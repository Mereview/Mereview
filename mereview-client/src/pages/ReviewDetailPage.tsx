import "../styles/css/ReviewDetailPage.css";
import Top from "../components/reviewDetail/Top";
import Detail from "../components/reviewDetail/Detail";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { searchReview, toggleNotificationStatus } from "../api/review";
import Loading from "../components/common/Loading";

const ReviewDetail = () => {
  const [review, setReview] = useState({ backgroundImage: { id: null } });
  const [loading, setLoading] = useState(false);
  const { id } = useParams();
  const userId = localStorage.getItem("id");
  const navigate = useNavigate();
  //리뷰불러오기

  useEffect(() => {
    const data = { reviewId: id, loginMemberId: userId };
    const getReviewHandler = () => {
      searchReview(
        data,
        (res) => {
          setReview(res.data.data);
          setLoading(true);
          console.log("최상위 렌더링");
          console.log("putMapping에 보낼 데이터 ", data);

          toggleNotificationStatus(
            data,
            (toggleRes) => {
              console.log("토글 완료", toggleRes);
            },
            (toggleErr) => {
              console.log("토글 불가능", toggleErr);
            }
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
        filter: "blur(15px)",
        zIndex: "-1",
      }
    : {
        backgroundColor: "black",
        opacity: "82%",
        filter: "blur(5px)",
        zIndex: "-1",
      };

  return (
    <div className="section">
      {loading ? (
        <div>
          <div className="blurred" style={style}></div>
          <Top review={review} />
          <Detail review={review} setReview={setReview} />
          <div className="topbutton"></div>
        </div>
      ) : (
        <Loading />
      )}
    </div>
  );
};

export default ReviewDetail;
