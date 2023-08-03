import { Row, Col, Container } from "react-bootstrap";
import { DummyRev, DummyMov } from "../../pages/ReviewDetailPage";
import "../../styles/css/Top.css";
interface TopProps {
  review: DummyRev;
  movie: DummyMov;
}
const Top = ({ review, movie }: TopProps) => {
  return (
    <Container className="total">
      <Row>
        <Col className="leftInfo">
          <h1>"{review.oneLine}"</h1>
          <h2>{movie.title}</h2>
          <p>
            {movie.released_date} | {movie.genres}
          </p>
          <div className="userInfo">
            <img src="/testProfile.gif" alt="" />
            <div>
              <p className="nickname">User.NickName</p>
              <p>작성글: 여기서</p>
            </div>
          </div>
        </Col>
        <Col className="rightInfo">
          <img src="/ddabong.png" alt="따봉" />
        </Col>
      </Row>
    </Container>
  );
};

export default Top;
