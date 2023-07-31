import { Button } from "../common/index";
import "../../styles/css/SelectInterest.css";
import { useSelector } from "react-redux";
import { useState } from "react";

const SelectInterest = () => {
  const [interest, setInterest] = useState([]);

  const onClick = (event: any) => {
    const genreName = event.target.textContent;
    setInterest((cur) =>
      cur.includes(genreName)
        ? cur.filter((item) => item !== genreName)
        : [...cur, genreName]
    );
  };
  const signUpHandler = () => {};
  const genre: { [id: number]: string[] } = {
    12: ["모험", "/interest/adventure"],
    14: ["판타지", "/interest/fantasy"],
    16: ["애니메이션", "/interest/animation"], ///
    18: ["드라마", "/interest/drama"], ///
    27: ["공포", "/interest/horror"], ///
    28: ["액션", "/interest/action"], ///
    35: ["코미디", "/interest/comedy"], ///
    36: ["역사", "/interest/history"], ///
    37: ["서부", "/interest/western"], ///
    53: ["스릴러", "/interest/thriller"], ///
    80: ["범죄", "/interest/crime"], // /
    99: ["다큐멘터리", "/interest/documentary"], ///
    878: ["과학소설", "/interest/SF"], ///
    9648: ["미스터리", "/interest/mistery"], ///
    10402: ["음악", "/interest/music"], ///
    10749: ["로맨스", "/interest/romance"],
    10571: ["가족", "/interest/familly"], ///
    10752: ["전쟁", "/interest/war"], ///
    10770: ["TV 영화", "/interest/TVmovie"], ///
  };
  return (
    <div className="big-box">
      <div className="title">
        <button>👈</button>
        <h1>어떤 장르에 관심 있으세요??</h1>
      </div>
      <div className="middle-box">
        {Object.keys(genre).map((id) => (
          <div
            className={`small-box ${
              interest.includes(genre[id][0]) ? "selected" : ""
            }`}
            key={id}
            style={{ backgroundImage: `url(${genre[id][1]}.png)` }}
            onClick={onClick}
          >
            {genre[id][0]}
          </div>
        ))}
      </div>
      <p></p>
      <div className="finish">
        <Button
          onClick={signUpHandler}
          styles="btn-primary"
          text={`${interest.length} 개 선택, 완료하기!`}
        />
      </div>
    </div>
  );
};

export default SelectInterest;
