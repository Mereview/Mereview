import Slider from "@mui/material/Slider";
import React from "react";
import { useState } from "react";

const Slide = () => {
  const [gauge, setGauge] = useState(0);
  const gaugeHandler = (
    event: Event,
    newValue: number | number[],
    activeThumb: number
  ) => {
    setGauge(newValue as number);
  };
  return (
    <div style={{ display: "inline-block" }}>
      <input></input>
      <Slider value={gauge} onChange={gaugeHandler} />
    </div>
  );
};

export default Slide;
