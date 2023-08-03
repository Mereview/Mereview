import Slider from "@mui/material/Slider";
import React from "react";
import { useState, forwardRef, useImperativeHandle } from "react";
import Input from "../common/Input";
import { Row, Col } from "react-bootstrap";
import { Connect } from "react-redux";

const KeywordSlider = forwardRef((props, ref) => {
  const [keyInfo, setKeyInfo] = useState({ keyword: "", gauge: 0 });
  const getKeyInfo = () => {
    return keyInfo;
  };
  const infoHandler = (e) => {
    if (typeof e.target.value === "number") {
      const newKeyword = keyInfo.keyword;
      const newGauge = e.target.value;
      setKeyInfo({ keyword: newKeyword, gauge: newGauge });
    } else {
      const newKeyword = e.target.value;
      const newGauge = keyInfo.gauge;
      setKeyInfo({
        keyword: newKeyword,
        gauge: newGauge,
      });
    }
  };
  useImperativeHandle(ref, () => ({
    getKeyInfo,
  }));
  return (
    <div>
      <Row className="mx-1 my-4">
        <Col md={4}>
          <input
            className="form-control"
            onChange={infoHandler}
            value={keyInfo.keyword}
          ></input>
        </Col>
        <Col md={2}>
          <p>{keyInfo.gauge}</p>
        </Col>
        <Slider
          value={keyInfo.gauge}
          onChange={infoHandler}
          style={{ width: "50%" }}
        />
      </Row>
    </div>
  );
});

export default KeywordSlider;
