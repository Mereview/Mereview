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

const ExperienceBar = (experienceProps: { userExpData: Experience[] }) => {
  const userData = experienceProps.userExpData;

  const labels: string[][] = [];
  const expData: number[] = [];
  const backgroundColor: string[] = [];
  const borderColor: string[] = [];
  for (const data of userData) {
    labels.push([data.genre, data.typeName]);
    expData.push(data.exp);
    // expData.push(data.expPercent);
    backgroundColor.push(tierColor[data.tier]);
    borderColor.push(tierBorderColor[data.tier]);
  }

  const chartData = {
    labels: labels,
    datasets: [
      {
        data: expData,
        backgroundColor: backgroundColor,
        borderColor: borderColor,
        borderWidth: 1,
        categoryPercentage: 1,
        barPercentage: 0.33,
      },
    ],
  };

  return (
    <>
      <div className="experience-bar-container">
        <Bar data={chartData} width={4000} height={500} />
      </div>
    </>
  );
};

export default ExperienceBar;
