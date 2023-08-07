import { reviewApi, reviewApiFormData } from "./index";
import { login } from "./members";

const api = reviewApi;
const apiForm = reviewApiFormData;

export async function createReview(data: FormData, success, fail) {
  await apiForm.post(`/`, data).then(success).catch(fail);
}

export async function searchReviews(data: Object, success, fail) {
  const queryParams: string[] = [];
  for (const [key, value] of Object.entries(data)) {
    queryParams.push(`${key}=${value}`);
  }
  const queryString: string = queryParams.join("&");

  await api.get(`/?${queryString}`).then(success).catch(fail);
}

export async function searchReview(data: Object, success, fail) {
  await api
    .get(`/${data["reviewId"]}?loginMemberId=${data["loginMemberId"]}`)
    .then(success)
    .catch(fail);
}

export async function updateReview(data: JSON, success, fail) {
  await api.put(`/${data["id"]}`).then(success).catch(fail);
}

export async function deleteReview(data: JSON, success, fail) {
  await api.delete(`/follow`, data).then(success).catch(fail);
}

/* Comment API */
export async function createReviewComment(data: JSON, success, fail) {
  await api.post(`/follow`, data).then(success).catch(fail);
}

export async function updateReviewComment(data: JSON, success, fail) {
  await api.delete(`/follow`, data).then(success).catch(fail);
}

export async function deleteReviewComment(data: JSON, success, fail) {
  await api.delete(`/follow`, data).then(success).catch(fail);
}

export async function updateCommentLike(data: JSON, success, fail) {
  await api.delete(`/follow`, data).then(success).catch(fail);
}
