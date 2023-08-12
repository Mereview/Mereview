import "../../styles/css/Top.css";
import WordCloud from "react-d3-cloud";

const Top = ({ review }: any) => {
  const words = review.keywords.map((word) => ({
    text: word.keywordName,
    value: word.keywordWeight / 20,
  }));
  const profileImageURL = review.profileImage?.id
    ? `${process.env.REACT_APP_API_URL}/image/download/profiles/${review.profileImage.id}`
    : "/testProfile.gif";

  return (
    <div className="total">
      <div className="leftInfo">
        <h1>"{review.reviewHighlight}"</h1>
        <h2>{review.movieTitle}</h2>
        <p>
          {review.movieReleaseDate} | {review.genre.genreName}
        </p>
        <div className="userInfo">
          <img src={profileImageURL} alt="작성자프로필이미지" />
          <div>
            <p className="nickname">{review.nickname}</p>
            <p>작성글: 여기서</p>
          </div>
        </div>
      </div>
      <div className="rightInfo">
        <img src="/ddabong.png" alt="따봉" />

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
      </div>
    </div>
  );
};

export default Top;
