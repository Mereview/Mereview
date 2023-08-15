import { movieApi, tmdbApi } from "./index";

const api = movieApi;
const tmdb = tmdbApi;

export async function searchMovies(keyword: string, success, fail) {
  await api
    .get(`?${keyword || ""}`)
    .then(success)
    .catch(fail);
}

export async function searchMovieDetail(movieId: number, success, fail) {
  await api.get(`/${movieId}`).then(success).catch(fail);
}

export async function getPopularMovies(page: number, success, fail) {
  await tmdb.get(`/movie/popular?language=ko-KR&page=${page}&region=KR`).then(success).catch(fail);
}
