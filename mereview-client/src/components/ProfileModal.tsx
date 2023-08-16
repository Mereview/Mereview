import "../styles/css/ProfileModal.css";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { userActions } from "../store/user-slice";

const ProfileModal = () => {
  const [animate, setAnimate] = useState(false);
  useEffect(() => {
    setAnimate(true);
  }, []);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const userNickname = useSelector((state: any) => state.user.user.nickname);
  const logout = () => {
    dispatch(userActions.logout());
    navigate("/");
  };
  return (
    <div className="toplevel">
      <div className={`arrow ${animate ? "animate" : ""}`}></div>
      <div className={`first ${animate ? "animate" : ""}`}>
        Hello, {userNickname}
      </div>
      <div className={`second ${animate ? "animate" : ""}`}>
        <Link to={"/profile"} style={{ color: "white" }}>
          MY PAGE
        </Link>
      </div>
      <div className={`last ${animate ? "animate" : ""}`} onClick={logout}>
        LOG OUT
      </div>
    </div>
  );
};

export default ProfileModal;
