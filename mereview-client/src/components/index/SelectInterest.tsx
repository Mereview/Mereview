import { Button } from "../common/index";
import "../../styles/css/SelectInterest.css";
import { useDispatch, useSelector } from "react-redux";
import { useEffect, useState } from "react";
import { userActions } from "../../store/user-slice";
import { uiActions } from "../../store/ui-silce";
import { signup } from "../../api/members";
interface GenreInfo {
  [id: string]: [string, string];
}
interface InterestInterface {
  genreId: string;
  genreName: string;
}

const SelectInterest = ({ step1, step2, verificationCode }) => {
  const [loading, setLoading] = useState<boolean>(true);
  const [interest, setInterest] = useState<InterestInterface[]>([]);
  const dispatch = useDispatch();
  useEffect(() => {
    setLoading(false);
  }, [loading]);
  const onClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    const genreId = event.currentTarget.id;
    const genreName = event.currentTarget.textContent || "";

    setInterest((prevInterest: InterestInterface[]) => {
      const isGenreExist = prevInterest.some(
        (item) => item.genreId === genreId
      );
      if (isGenreExist) {
        return prevInterest.filter((item) => item.genreId !== genreId);
      } else {
        return [...prevInterest, { genreId, genreName }];
      }
    });
  };
  const goBack = () => {
    dispatch(userActions.modal_toggler());
  };
  const signUpData: any = {
    email: step1.email,
    password: step1.password,
    nickname: step1.nickname,
    gender: step1.gender,
    birthDate: step1.birthDate,
    interests: interest,
    verificationCode: verificationCode,
  };
  const data = new FormData();
  data.append("file", step2);
  data.append(
    "request",
    new Blob([JSON.stringify(signUpData)], { type: "application/json" })
  );
  const signUpHandler = () => {
    signup(
      data,
      (res) => {
        alert("회원가입이 완료되었습니다!");
        dispatch(userActions.modal_toggler());
        dispatch(uiActions.tabChange("signUpCompleted"));
      },
      (err) => {
        alert(err.response.data.message);
      }
    );
  };
  const genre: GenreInfo = {
    "1": ["액션", "/interest/action"], ///
    "2": ["모험", "/interest/adventure"],
    "3": ["애니메이션", "/interest/animation"], ///
    "4": ["코미디", "/interest/comedy"], ///
    "5": ["범죄", "/interest/crime"], // /
    "6": ["다큐멘터리", "/interest/documentary"], ///
    "7": ["드라마", "/interest/drama"], ///
    "8": ["가족", "/interest/familly"], ///
    "9": ["판타지", "/interest/fantasy"],
    "10": ["역사", "/interest/history"], ///
    "11": ["공포", "/interest/horror"], ///
    "12": ["음악", "/interest/music"], ///
    "13": ["미스터리", "/interest/mistery"], ///
    "14": ["로맨스", "/interest/romance"],
    "15": ["SF", "/interest/SF"], ///
    "16": ["TV 영화", "/interest/TVmovie"], ///
    "17": ["스릴러", "/interest/thriller"], ///
    "18": ["전쟁", "/interest/war"], ///
    "19": ["서부", "/interest/western"], ///
  };
  return (
    <div className="big-box">
      {loading && (
        <div className="loading-overlay">
          <div className="loading-spinner"></div>
        </div>
      )}
      <div className="title">
        <button className="cancle" onClick={goBack}>
          👈취소
        </button>
        <h1>어떤 장르에 관심 있으세요??</h1>
      </div>

      <div className="middle-box">
        {Object.keys(genre).map((id) => (
          <div
            id={id}
            className={`small-box ${
              interest.some((item) => item.genreId === id) ? "selected" : ""
            }`}
            key={id}
            style={{ backgroundImage: `url(${genre[id][1]}.png)` }}
            onClick={onClick}
          >
            <span id="title">{genre[id][0]}</span>
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
