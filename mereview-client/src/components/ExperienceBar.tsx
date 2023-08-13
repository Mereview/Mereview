import React from "react";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Tooltip,
} from "chart.js";
import { Bar } from "react-chartjs-2";
import { Experience } from "./interface/ProfilePageInterface";
import "../styles/css/ExperienceBar.css";

const tierBackgroundColor = {
  NONE: "rgba(0, 0, 0, 0)",
  BRONZE: "rgba(148, 97, 61, 0.2)", // bronze
  SILVER: "rgba(143, 143, 143, 0.2)", // silver
  GOLD: "rgba(242, 205, 92, 0.2)", // gold
  PLATINUM: "rgba(80, 200, 120, 0.2)", // platinum
  DIAMOND: "rgba(112, 209, 244, 0.2)", // diamond
};

const tierColor = {
  NONE: "rgba(0, 0, 0, 0.35)",
  BRONZE: "rgba(148, 97, 61, 0.5)", // bronze
  SILVER: "rgba(143, 143, 143, 0.5)", // silver
  GOLD: "rgba(242, 205, 92, 0.5)", // gold
  PLATINUM: "rgba(80, 200, 120, 0.5)", // platinum
  DIAMOND: "rgba(112, 209, 244, 0.5)", // diamond
};

const tierBorderColor = {
  NONE: "rgba(0, 0, 0, 1)",
  BRONZE: "rgba(148, 97, 61, 1)", // bronze
  SILVER: "rgba(143, 143, 143, 1)", // silver
  GOLD: "rgba(242, 205, 92, 1)", // gold
  PLATINUM: "rgba(80, 200, 120, 1)", // platinum
  DIAMOND: "rgba(112, 209, 244, 1)", // diamond
};

ChartJS.register(CategoryScale, LinearScale, BarElement, Tooltip);

interface chartDataInterface {
  data: number[];
  backgroundColor: string[];
  borderColor?: string[];
  borderWidth?: number;
  categoryPercentage: number;
  barPercentage: number;
}

const ExperienceBar = React.memo(
  (experienceProps: { userExpData: Experience[] }) => {
    const userData = experienceProps.userExpData;

    const labels: string[][] = [];
    const expData: chartDataInterface = {
      data: [],
      backgroundColor: [],
      borderColor: [],
      borderWidth: 1,
      categoryPercentage: 1,
      barPercentage: 0.33,
    };
    const backgournd: chartDataInterface = {
      data: [],
      backgroundColor: [],
      categoryPercentage: 1,
      barPercentage: 0.33,
    };
    const minimunVisible: chartDataInterface = {
      data: [],
      backgroundColor: [],
      borderColor: [],
      borderWidth: 1,
      categoryPercentage: 1,
      barPercentage: 0.33,
    };

    const chartData = {
      labels: labels,
      datasets: [expData, backgournd, minimunVisible],
    };

    const chartOption = {
      scales: {
        x: {
          stacked: true,
        },
        y: {
          min: 0,
          max: 100,
        },
      },
      plugins: {
        tooltip: {
          enabled: false,
        },
      },
    };

    for (const data of userData) {
      labels.push([data.genre, data.typeName]);
      expData.data.push(data.expPercent);
      expData.backgroundColor.push(tierColor[data.tier]);
      expData.borderColor.push(tierBorderColor[data.tier]);
      backgournd.data.push(100);
      backgournd.backgroundColor.push(tierBackgroundColor[data.tier]);
      if (data.expPercent < 2.5) {
        minimunVisible.data.push(2.5 - data.expPercent);
        minimunVisible.borderColor.push(tierBorderColor[data.tier]);
        minimunVisible.backgroundColor.push(tierColor[data.tier]);
      } else {
        minimunVisible.data.push(null);
        minimunVisible.borderColor.push(null);
        minimunVisible.backgroundColor.push(null);
      }
    }

    return (
      <>
        <div className="experience-bar-container">
          <Bar
            data={chartData}
            options={chartOption}
            width={4000}
            height={500}
          />
        </div>
      </>
    );
  }
);

export default ExperienceBar;
