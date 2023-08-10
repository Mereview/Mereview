import "../styles/css/ReviewDetailPage.css";
import Top from "../components/reviewDetail/Top";
import Detail from "../components/reviewDetail/Detail";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { searchReview } from "../api/review";
import Loading from "../components/common/Loading";
// export interface DummyRev {
//   title: string;
//   oneLine: string;
//   content: string;
//   keyword: { [key: string]: string }; // 키와 값의 타입을 모두 문자열로 정의
//   memberId: number;
// }
// export interface DummyMov {
//   title: string;
//   released_date: string;
//   genres: string[];
// }
const ReviewDetail = () => {
  const [review, setReview] = useState({ backgroundImage: { id: null } });
  const [loading, setLoading] = useState(false);
  const { id } = useParams();
  console.log(id);
  const userId = localStorage.getItem("id");
  console.log(userId);
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
        },
        (err) => {
          console.log("fail");
        }
      );
    };
    getReviewHandler();
  }, []);

  const backgroundImageURL =
    review.backgroundImage && review.backgroundImage.id
      ? `http://localhost:8080/api/image/download/backgrounds/${review.backgroundImage.id}`
      : false;
  const style = backgroundImageURL
    ? {
        backgroundImage: `url(${backgroundImageURL})`,
        filter: "blur(15px)",
        zIndex: "-1",
      }
    : {
        backgroundColor: "rgba(0, 0, 0, 0.7)",
        filter: "blur(5px)",
        zIndex: "-1",
      };

  console.log(review);
  return (
    <div className="section">
      {loading ? (
        <div>
          <div className="blurred" style={style}></div>
          <Top review={review} />
          <Detail review={review} />
          <div className="topbutton"></div>
        </div>
      ) : (
        <Loading />
      )}
    </div>
  );
};

export default ReviewDetail;
