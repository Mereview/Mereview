import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Tooltip,
} from "chart.js";
import { Bar } from "react-chartjs-2";

// Use API later
const genres: Record<string, string> = {
  "28": "액션",
  "12": "모험",
  "16": "애니메이션",
  "35": "코미디",
  "80": "범죄",
  "99": "다큐멘터리",
  "18": "드라마",
  "10751": "가족",
  "14": "판타지",
  "36": "역사",
  "27": "공포",
  "10402": "음악",
  "9648": "미스터리",
  "10749": "로맨스",
  "878": "SF",
  "10770": "TV 영화",
  "53": "스릴러",
  "10752": "전쟁",
  "37": "서부",
};

type Experience = {
  genre: string;
  typeName: "재밌어요" | "유용해요";
  exp: number;
};

const tierScores: number[] = [0, 30, 90, 270, 1000];

const tierColor = {
  0: "rgba(148, 97, 61, 0.5)", // bronze
  30: "rgba(143, 143, 143, 0.5)", // silver
  90: "rgba(242, 205, 92, 0.5)", // gold
  270: "rgba(80, 200, 120, 0.5)", // platinum
};

const tierBorderColor = {
  0: "rgba(148, 97, 61, 1)", // bronze
  30: "rgba(143, 143, 143, 1)", // silver
  90: "rgba(242, 205, 92, 1)", // gold
  270: "rgba(80, 200, 120, 1)", // platinum
};

ChartJS.register(CategoryScale, LinearScale, BarElement, Tooltip);

const ExperienceBar = (experienceProps: { userExpData: Experience[] }) => {
  const userData = experienceProps.userExpData;
  userData.sort((a, b) => {
    if (a.exp > b.exp) return -1;
    else if (a.exp < b.exp) return 1;
    else return 0;
  });

  const labels: string[] = [];
  const typeLables: string[] = [];
  const expData: number[] = [];
  const backgroundColor: string[] = [];
  const borderColor: string[] = [];
  for (const data of userData) {
    labels.push(genres[data.genre] + " - " + data.typeName);
    expData.push(data.exp);
    let selectedTierScore: number = 0;
    for (const tierScore of tierScores) {
      if (data.exp >= tierScore) {
        selectedTierScore = tierScore;
      } else {
        backgroundColor.push(tierColor[selectedTierScore]);
        borderColor.push(tierBorderColor[selectedTierScore]);
        break;
      }
    }
  }

  const chartData = {
    labels: labels,
    datasets: [
      {
        data: expData,
        backgroundColor: backgroundColor,
        borderColor: borderColor,
        borderWidth: 1,
      },
    ],
  };

  return (
    <>
      <Bar data={chartData} />
    </>
  );
};

export default ExperienceBar;
