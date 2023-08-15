import axios from "axios";

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

export const tmdbApi = axios.create({
  baseURL: "https://api.themoviedb.org/3",
  headers: {
    "Content-type": "application/json",
    Authorization: `${process.env.REACT_APP_TMBD_KEY}`,
  },
});

export const emailApi = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL}/email`,
  headers: {
    "Content-type": "application/json",
  },
});
