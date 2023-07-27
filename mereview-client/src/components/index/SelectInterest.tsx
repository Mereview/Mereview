import { Container, Row, Col, Modal } from "react-bootstrap";
import { Input, Button, FloatLabelInput } from "../common/index";
import "../../styles/css/SelectInterest.css";
const SelectInterest = () => {
  const genre: { [id: number]: string } = {
    12: "모험",
    14: "판타지",
    16: "애니메이션",
    18: "드라마",
    27: "공포",
    28: "액션",
    35: "코미디",
    36: "역사",
    37: "서부",
    53: "스릴러",
    80: "범죄",
    99: "다큐멘터리",
    878: "과학소설",
    9648: "미스터리",
    10402: "음악",
    10749: "로맨스",
    10571: "가족",
    10752: "전쟁",
    10770: "TV 영화",
  };
  return (
    <div className="big-box">
      <button> </button>
      <h1>어떤 장르에 관심 있으세요??</h1>
      <div className="middle-box">
        {Object.keys(genre).map((id) => (
          <div key={id} className="small-box">
            {genre[id]}
          </div>
        ))}
      </div>
      <div className="finish">
        <Button styles="btn-primary" text="완료하기!" />
      </div>
    </div>
  );
};

export default SelectInterest;
