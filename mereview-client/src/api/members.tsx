import { memberApi } from "./index";

const api = memberApi;

export async function searchMemberInfo(id: number, success, fail) {
  await api.get(`/${id}`).then(success).catch(fail);
}
