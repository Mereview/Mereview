import { emailApi } from ".";

const api = emailApi;

export async function emailSend(data: Object, success, fail) {
  await emailApi.post(`/send`, data).then(success).catch(fail);
}

export async function emailCheck(data: Object, success, fail) {
  await emailApi.post(`/check`, data).then(success).catch(fail);
}
