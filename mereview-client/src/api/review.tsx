import axios from "axios";

const writeReview = (data) => {
  const reviewData = new FormData();
  reviewData.append("file", data.get("file"));
  console.log(reviewData.get("file"));
};

export default writeReview;
