import { Container } from "react-bootstrap";
import ReviewCard from "../components/ReviewCard";
import { ReviewCardInterface } from "../components/interface/ReviewCardInterface";

interface ReviewListProps {
  reviewList: ReviewCardInterface[];
}

const ReviewList = ({ reviewList }: ReviewListProps) => {
  return (
    <>
      <Container>
        {reviewList.map((review: ReviewCardInterface, index: number) =>
          ReviewCard(review)
        )}
      </Container>
    </>
  );
};

export default ReviewList;
