import React from "react";
import { useNavigate } from "react-router-dom";
import { FollowUserInfo } from "./interface/ProfilePageInterface";
import "../styles/css/FollowUser.css";

const FollowUser = (props: FollowUserInfo) => {
  const { memberId, profileImageId, nickname } = props;
  const navigate = useNavigate();

  const handleClick = (event: React.MouseEvent<HTMLParagraphElement>) => {
    event.stopPropagation();
    navigate(`/profile/${memberId}`);
  };

  const defaultProfileImage = "/testProfile.gif";

  return (
    <>
      <div className="follow-user-container" onClick={handleClick}>
        <div className="follow-profile-img">
          <img
            src={
              profileImageId
                ? `${process.env.REACT_APP_API_URL}/image/download/profiles/${profileImageId}`
                : defaultProfileImage
            }
            alt="Profile"
          />
        </div>
        <div className="follow-user-nickname">{nickname}</div>
      </div>
    </>
  );
};

export default FollowUser;
