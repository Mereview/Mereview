import { memberApi, memberApiFormData, emailApi } from "./index";

const api = memberApi;
const apiForm = memberApiFormData;

export async function signup(data: FormData, success, fail) {
  await apiForm.post(`/sign-up`, data).then(success).catch(fail);
}

export async function emailSend(data: Object, success, fail) {
  await emailApi.post(`/send`, data).then(success).catch(fail);
}

export async function emailCheck(data: Object, success, fail) {
  await emailApi.post(`/check`, data).then(success).catch(fail);
}

export async function login(data: Object, success, fail) {
  await api.post(`/login`, JSON.stringify(data)).then(success).catch(fail);
}

export async function deleteMember(memberId: number, success, fail) {
  await api.delete(`/${memberId}`).then(success).catch(fail);
}

export async function searchMemberInfo(memberId: number, success, fail) {
  await api.get(`/${memberId}`).then(success).catch(fail);
}

export async function updateMemberInfo(data: Object, success, fail) {
  await api.post(`/${data["id"]}`).then(success).catch(fail);
}

export async function updateProfilePic(data: FormData, success, fail) {
  await apiForm.put(`/profile-image`, data).then(success).catch(fail);
}

export async function follow(data: Object, success, fail) {
  await api.post(`/follow`, data).then(success).catch(fail);
}
