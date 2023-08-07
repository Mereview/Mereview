import { movieApi } from "./index";

const api = movieApi;

export async function login(data: JSON, success, fail) {
  await api.post(`/login`, JSON.stringify(data)).then(success).catch(fail);
}

export async function deleteMember(id: number, success, fail) {
  await api.delete(`/${id}`).then(success).catch(fail);
}

export async function searchMemberInfo(id: number, success, fail) {
  await api.get(`/${id}`).then(success).catch(fail);
}

export async function updateMemberInfo(data: JSON, success, fail) {
  await api.post(`/${data["id"]}`).then(success).catch(fail);
}
