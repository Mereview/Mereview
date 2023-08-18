import "../styles/css/ProfileModal.css";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { userActions } from "../store/user-slice";

const ProfileModal = ({
  setIsModal,
}: {
  setIsModal: React.Dispatch<React.SetStateAction<boolean>>;
}) => {
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
  const closeModal = () => {
    setIsModal(false);
  };
  return (
    <div className="toplevel mt-3">
      <div className={`arrow ${animate ? "animate" : ""}`}></div>
      <div className={`first ${animate ? "animate" : ""}`} style={{ cursor: "default" }}>
        Hello, {userNickname}
      </div>
      <div className={`second ${animate ? "animate" : ""}`} onClick={closeModal}>
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
