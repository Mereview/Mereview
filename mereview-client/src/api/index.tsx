import axios from "axios";

// axios 객체 생성
export const memberApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/members`,
  headers: {
    "Content-type": "application/json",
  },
});

export const memberApiFormData = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/members`,
  headers: {
    "Content-type": "multipart/form-data",
  },
});

export const reviewApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/reviews`,
  headers: {
    "Content-type": "application/json",
  },
});

export const reviewApiFormData = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/reviews`,
  headers: {
    "Content-type": "multipart/form-data",
  },
});

export const movieApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/movies`,
  headers: {
    "Content-type": "application/json",
  },
});
