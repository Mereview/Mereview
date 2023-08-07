import axios from "axios";

// axios 객체 생성
export const memberApi = axios.create({
  baseURL: "http://localhost:8080/api/members",
  headers: {
    "Content-type": "application/json",
  },
});
