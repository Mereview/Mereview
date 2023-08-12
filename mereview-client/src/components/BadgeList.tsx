import { AchievedBadge } from "./interface/ProfilePageInterface";
import "../styles/css/BadgeList.css";

const goldBadge =
  "https://www.badgesplus.co.uk/uploads/images/products/D31_Achievement_Gold.png";
const silverBadge =
  "https://www.badgesplus.co.uk/uploads/images/products/D31_Achievement_Silver.png";
const bronzeBadge =
  "https://www.badgesplus.co.uk/uploads/images/products/D31_Achievement_Bronze.png";

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
        src={
          badgeInfo.rank === "GOLD"
            ? goldBadge
            : badgeInfo.rank === "SILVER"
            ? silverBadge
            : bronzeBadge
        }
        alt="Badge"
      />
    </div>
  );
};

const BadgeList = ({ badgeListProps }: BadgeListInterface) => {
  return (
    <>
      <div>
        {badgeListProps.map((badge) => (
          <BadgeComponent key={badge.achievementId} badgeInfo={badge} />
        ))}
      </div>
    </>
  );
};

export default BadgeList;
