import { Row, Col, Container } from "react-bootstrap";
import { DummyRev, DummyMov } from "../../pages/ReviewDetailPage";
import "../../styles/css/Top.css";
import WordCloud from "react-d3-cloud";
import { scaleOrdinal } from "d3-scale";
import { schemeCategory10 } from "d3-scale-chromatic";
interface TopProps {
  review: DummyRev;
  movie: DummyMov;
}
const Top = ({ review, movie }: TopProps) => {
  const words = Object.keys(review.keyword).map((word) => ({
    text: word,
    value: Number(review.keyword[word]),
  }));
  const schemeCategory10ScaleOrdinal = scaleOrdinal(schemeCategory10);
  console.log(words);
  return (
    <div className="total">
      <div className="leftInfo">
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
      </div>
      <div className="rightInfo">
        <div className="img">
          <img src="/ddabong.png" alt="따봉" />
        </div>
        <div
          className="wordcloud"
          style={{
            display: "block", // 레이아웃 속성을 변경
            width: "100%",
            height: "auto",
            overflow: "auto",
          }}
        >
          <WordCloud
            data={words}
            width={330}
            height={250}
            font="Times"
            fontStyle="italic"
            fontWeight="bold"
            fontSize={(word) => word.value * 10}
            rotate={(word) => word.value % 360}
            spiral="archimedean"
            padding={5}
            random={Math.random}
            fill={(d, i) => schemeCategory10ScaleOrdinal(i)}
            onWordClick={(event, d) => {
              console.log(`onWordClick: ${d.text}`);
            }}
            onWordMouseOver={(event, d) => {
              console.log(`onWordMouseOver: ${d.text}`);
            }}
            onWordMouseOut={(event, d) => {
              console.log(`onWordMouseOut: ${d.text}`);
            }}
          />
          ,
        </div>
      </div>
    </div>
  );
};

export default Top;
