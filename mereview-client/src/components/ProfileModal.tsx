import "../styles/css/ProfileModal.css";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { userActions } from "../store/user-slice";

const ProfileModal = () => {
  const [animate, setAnimate] = useState(false);
  useEffect(() => {
    setAnimate(true);
  }, []);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const logout = () => {
    dispatch(userActions.logout());
    navigate("/");
  };
  return (
    <div className="toplevel">
      <div className={`arrow ${animate ? "animate" : ""}`}></div>
      <div className={`first ${animate ? "animate" : ""}`}>
        반갑다 user.nickname
      </div>
      <div className={`second ${animate ? "animate" : ""}`}>
        <Link to={"/profile"}>MY PAGE</Link>
      </div>
      <div className={`last ${animate ? "animate" : ""}`} onClick={logout}>
        LOG OUT
      </div>
    </div>
  );
};

export default ProfileModal;