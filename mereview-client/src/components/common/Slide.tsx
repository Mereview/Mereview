import Slider from "@mui/material/Slider";
import React from "react";
import { useState } from "react";
import Input from "./Input";
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
    <div>
      <Input styles="input-line"></Input>
      <Slider value={gauge} onChange={gaugeHandler} />
    </div>
  );
};

export default Slide;
