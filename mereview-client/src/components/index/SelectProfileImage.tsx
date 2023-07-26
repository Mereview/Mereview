import { Container, Row, Col } from "react-bootstrap";
import { Input, Button, FloatLabelInput } from "../common/index";
import { useState } from "react";
import "../../styles/css/SelectProfileImage.css";

const SelectProfileImage = () => {
  const [selectedImage, setSelectedImage] = useState("/defaultProfile.png");
  const selectImg = () => {
    console.log("hi");
  };
  return (
    <Container>
      <Row></Row>
      <Row>
        <div
          className="selectImg"
          onClick={selectImg}
          style={{ backgroundImage: `url(${selectedImage})` }}
        ></div>
      </Row>
      <Row></Row>
    </Container>
  );
};

export default SelectProfileImage;
