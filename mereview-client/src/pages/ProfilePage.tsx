import { Row } from "react-bootstrap";
import ExperienceBar from "../components/ExperienceBar";
import AchievedBadge from "../components/AchievedBadge";
import {
  Experience,
  ProfileInfoInterface,
} from "../components/interface/ProfilePageInterface";
import "../styles/css/ProfilePage.css";

/* 유저 경험치 더미 데이터 생성 시작 */
const userExpData: Experience[] = [
  { genre: "28", typeName: "재밌어요", exp: 0 },
  { genre: "28", typeName: "유용해요", exp: 0 },
  { genre: "12", typeName: "재밌어요", exp: 0 },
  { genre: "12", typeName: "유용해요", exp: 0 },
  { genre: "16", typeName: "재밌어요", exp: 0 },
  { genre: "16", typeName: "유용해요", exp: 0 },
  { genre: "35", typeName: "재밌어요", exp: 0 },
  { genre: "35", typeName: "유용해요", exp: 0 },
  { genre: "80", typeName: "재밌어요", exp: 0 },
  { genre: "80", typeName: "유용해요", exp: 0 },
  { genre: "99", typeName: "재밌어요", exp: 0 },
  { genre: "99", typeName: "유용해요", exp: 0 },
  { genre: "18", typeName: "재밌어요", exp: 0 },
  { genre: "18", typeName: "유용해요", exp: 0 },
  { genre: "10751", typeName: "재밌어요", exp: 0 },
  { genre: "10751", typeName: "유용해요", exp: 0 },
  { genre: "14", typeName: "재밌어요", exp: 0 },
  { genre: "14", typeName: "유용해요", exp: 0 },
  { genre: "36", typeName: "재밌어요", exp: 0 },
  { genre: "36", typeName: "유용해요", exp: 0 },
  { genre: "27", typeName: "재밌어요", exp: 0 },
  { genre: "27", typeName: "유용해요", exp: 0 },
  { genre: "10402", typeName: "재밌어요", exp: 0 },
  { genre: "10402", typeName: "유용해요", exp: 0 },
  { genre: "9648", typeName: "재밌어요", exp: 0 },
  { genre: "9648", typeName: "유용해요", exp: 0 },
  { genre: "10749", typeName: "재밌어요", exp: 0 },
  { genre: "10749", typeName: "유용해요", exp: 0 },
  { genre: "878", typeName: "재밌어요", exp: 0 },
  { genre: "878", typeName: "유용해요", exp: 0 },
  { genre: "10770", typeName: "재밌어요", exp: 0 },
  { genre: "10770", typeName: "유용해요", exp: 0 },
  { genre: "53", typeName: "재밌어요", exp: 0 },
  { genre: "53", typeName: "유용해요", exp: 0 },
  { genre: "10752", typeName: "재밌어요", exp: 0 },
  { genre: "10752", typeName: "유용해요", exp: 0 },
  { genre: "37", typeName: "재밌어요", exp: 0 },
  { genre: "37", typeName: "유용해요", exp: 0 },
];

const ranNum = () => {
  const num = Math.random() * 600 - 300;
  return Math.floor(num > 0 ? num : 0);
};

const generateData = () => {
  for (const data of userExpData) {
    data.exp = ranNum();
  }
};
/* 유저 경험치 더미 데이터 생성 끝 */

const ProfilePage = (props: any) => {
  generateData();

  return (
    <>
      <Row>
        <div className="profile-image"></div>
        <ExperienceBar userExpData={userExpData} />
      </Row>
    </>
  );
};

export default ProfilePage;
