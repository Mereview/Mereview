import { reviewApi, reviewApiFormData } from "./index";

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

  await api.get(`?${queryString}`).then(success).catch(fail);
}

export async function searchReview(data: Object, success, fail) {
  await api
    .get(`/${data["reviewId"]}?loginMemberId=${data["loginMemberId"]}`)
    .then(success)
    .catch(fail);
}

export async function updateReview(
  reviewId: number,
  data: FormData,
  success,
  fail
) {
  await apiForm.put(`/${reviewId}`, data).then(success).catch(fail);
}

export async function deleteReview(reviewId: number, success, fail) {
  await api.delete(`/${reviewId}`).then(success).catch(fail);
}

/* Comment API */
export async function createReviewComment(data: Object, success, fail) {
  await api.post(`/comments/`, data).then(success).catch(fail);
}

export async function updateReviewComment(
  commentId: number,
  data: JSON,
  success,
  fail
) {
  await api.put(`/comments/${commentId}`, data).then(success).catch(fail);
}

export async function deleteReviewComment(commentId: number, success, fail) {
  await api.delete(`/comments/${commentId}`).then(success).catch(fail);
}

export async function updateCommentLike(data: Object, success, fail) {
  await api.post(`/comments/likes`, data).then(success).catch(fail);
}

export async function evaluationsReview(data: Object, success, fail) {
  await api.post(`/evaluations`, data).then(success).catch(fail);
}

/** Notification 온 리뷰들 가져오는 API */
export async function getConfirmedNotifications(memberId, success, fail) {
  const queryParams = {
    memberId: memberId,
    status : "CONFIRMED"
  };

  const queryString = new URLSearchParams(queryParams).toString();

  await api.get(`/notifications?${queryString}`)
    .then(success)
    .catch(fail);
}

export async function getUnConfirmedNotifications(memberId, success, fail) {
  const queryParams = {
    memberId: memberId,
    status : "UNCONFIRMED"
  };

  const queryString = new URLSearchParams(queryParams).toString();

  await api.get(`/notifications?${queryString}`)
    .then(success)
    .catch(fail);
}

export async function toggleNotificationStatus(request, success, fail) {
  try {
    const response = await api.put("/notifications/", request);
    success(response.data);
  } catch (error) {
    fail(error);
  }
}
