import { Button } from "../common/index";
import "../../styles/css/SelectInterest.css";
import { useDispatch, useSelector } from "react-redux";
import { useEffect, useState } from "react";
import { userActions } from "../../store/user-slice";

const SelectInterest = () => {
  const [loading, setLoading] = useState(true);
  const [interest, setInterest] = useState([]);
  const dispatch = useDispatch();
  useEffect(() => {
    setLoading(true);
    setLoading(false);
  }, [loading]);
  const onClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    const genreId = event.currentTarget.id;
    const genreName = event.currentTarget.textContent || "";

    setInterest((prevInterest) => {
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
  const signUp_step3 = () => {
    dispatch(userActions.signUp_step3(interest));
    dispatch(userActions.modal_toggler());
  };

  const genre: { [id: number]: string[] } = {
    "12": ["모험", "/interest/adventure"],
    "14": ["판타지", "/interest/fantasy"],
    "16": ["애니메이션", "/interest/animation"], ///
    "18": ["드라마", "/interest/drama"], ///
    "27": ["공포", "/interest/horror"], ///
    "28": ["액션", "/interest/action"], ///
    "35": ["코미디", "/interest/comedy"], ///
    "36": ["역사", "/interest/history"], ///
    "37": ["서부", "/interest/western"], ///
    "53": ["스릴러", "/interest/thriller"], ///
    "80": ["범죄", "/interest/crime"], // /
    "99": ["다큐멘터리", "/interest/documentary"], ///
    "878": ["과학소설", "/interest/SF"], ///
    "9648": ["미스터리", "/interest/mistery"], ///
    "10402": ["음악", "/interest/music"], ///
    "10749": ["로맨스", "/interest/romance"],
    "10571": ["가족", "/interest/familly"], ///
    "10752": ["전쟁", "/interest/war"], ///
    "10770": ["TV 영화", "/interest/TVmovie"], ///
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
            {genre[id][0]}
          </div>
        ))}
      </div>
      <p></p>
      <div className="finish">
        <Button
          onClick={signUp_step3}
          styles="btn-primary"
          text={`${interest.length} 개 선택, 완료하기!`}
        />
      </div>
    </div>
  );
};

export default SelectInterest;
