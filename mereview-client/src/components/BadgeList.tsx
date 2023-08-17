import { AchievedBadge } from "./interface/ProfilePageInterface";
import "../styles/css/BadgeList.css";

const rankMapper: Object = {
  NONE: null,
  BRONZE: "b",
  SILVER: "s",
  GOLD: "g",
};

const genreMapper: Object = {
  액션: "action",
  모험: "adventure",
  애니메이션: "animation",
  코미디: "comedy",
  범죄: "crime",
  다큐멘터리: "documentary",
  드라마: "drama",
  가족: "familly",
  판타지: "fantasy",
  역사: "history",
  공포: "horror",
  음악: "music",
  미스터리: "mistery",
  로맨스: "romance",
  SF: "sf",
  "TV 영화": "TVmovie",
  스릴러: "thriller",
  전쟁: "war",
  서부: "western",
  기타: "etc",
};

const genreColor: Object = {
  NONE: null,
  BRONZE: "rgb(138, 110, 37)",
  SILVER: "rgb(170, 169, 173)",
  GOLD: "rgb(212, 175, 55)",
};

interface BadgeListInterface {
  badgeListProps: AchievedBadge[];
}

interface BadgeInfo {
  badgeInfo: AchievedBadge;
}

const BadgeComponent = ({ badgeInfo }: BadgeInfo) => {
  return (
    <div className="achieved-badge">
      <img
        src={`/Badges/${rankMapper[badgeInfo.rank]}-${
          genreMapper[badgeInfo.genreName]
        }.png`}
        alt="Badge"
      />
      <div className={`badge-info ${rankMapper[badgeInfo.rank]}`}>
        <div
          className="badge-genre"
          style={{ color: genreColor[badgeInfo.rank] }}
        >
          {badgeInfo.genreName}
        </div>
        <div className="badge-type">{badgeInfo.type}</div>
      </div>
    </div>
  );
};

const genreValues = Object.values(genreMapper);
const randomIndex = Math.floor(Math.random() * genreValues.length);
const randomGenreValue = genreValues[randomIndex];
const BadgeList = ({ badgeListProps }: BadgeListInterface) => {
  if (badgeListProps.length === 0) {
    return (
      <>
        <div
          className="badge-background"
          style={{ backgroundImage: `url(/Badges/g-${randomGenreValue}.png)` }}
        >
          <div className="badge-background-overlay">
            <div className="empty-badge-info">
              <div>획득한 뱃지가 없습니다.</div>
              <div>리뷰 혹은 댓글을 작성해서 뱃지를 수집해보세요!!</div>
            </div>
          </div>
        </div>
      </>
    );
  }
  return (
    <>
      <div>
        {badgeListProps.map((badge, index: number) => (
          <BadgeComponent key={index} badgeInfo={badge} />
        ))}
      </div>
    </>
  );
};

export default BadgeList;
