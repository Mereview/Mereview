import Slider from "@mui/material/Slider";
import React from "react";
import { useState, forwardRef, useImperativeHandle } from "react";
import Input from "../common/Input";
import { Row, Col } from "react-bootstrap";
import { Connect } from "react-redux";

const KeywordSlider = forwardRef((props, ref) => {
  const [keyInfo, setKeyInfo] = useState({ name: "", weight: 0 });
  const getKeyInfo = () => {
    return keyInfo;
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
  }));
  return (
    <div>
      <Row className="mx-1 my-4">
        <Col md={5}>
          <input
            className="form-control"
            id="name"
            onChange={infoHandler}
            value={keyInfo.name}
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
    </div>
  );
});

export default KeywordSlider;
