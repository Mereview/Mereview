import Slider from "@mui/material/Slider";
import React from "react";
import { useState, forwardRef, useImperativeHandle } from "react";
import Input from "../common/Input";
import { Row, Col } from "react-bootstrap";
import { Connect } from "react-redux";
import { KeywordDataInterface } from "../interface/ReviewDataInterface";

const KeywordSlider = forwardRef((props, ref) => {
  const [keyInfo, setKeyInfo] = useState<KeywordDataInterface>({
    name: "",
    weight: 0,
  });
  const getKeyInfo = () => {
    return keyInfo;
  };
  const setKey = (setValue) => {
    setKeyInfo({
      name: setValue.keywordName,
      weight: setValue.keywordWeight,
    });
  };
  const infoHandler = (e) => {
    // console.log(e.target);
    let { id, value } = e.target;
    if (e.target.id == null) {
      id = e.target.name;
    }
    setKeyInfo((prevInputData) => ({
      ...prevInputData,
      [id]: value,
    }));
  };
  useImperativeHandle(ref, () => ({
    getKeyInfo,
    setKey,
  }));
  return (
    // <div>
    <Row className="mx-1 my-2">
      <Col md={5}>
        <input
          className="form-control"
          id="name"
          onChange={infoHandler}
          value={keyInfo.name}
          placeholder="키워드"
        ></input>
      </Col>
      <Col md={2}>
        <p>{keyInfo.weight}</p>
      </Col>
      <Slider
        name="weight"
        value={keyInfo.weight}
        onChange={infoHandler}
        style={{ width: "40%" }}
      />
    </Row>
    // </div>
  );
});

export default KeywordSlider;
