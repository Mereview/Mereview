import { memberApi } from "./index";

const api = memberApi;

export async function getInfo(id, success, fail) {
  await api.get(`/${id}`).then(success).catch(fail);
}
