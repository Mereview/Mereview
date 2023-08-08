import { movieApi } from "./index";

const api = movieApi;

export async function searchMovies(keyword: string, success, fail) {
  await api
    .get(`?${keyword || ""}`)
    .then(success)
    .catch(fail);
}
