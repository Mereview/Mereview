import axios from "axios";

export const postSignUp = (data) => {
  axios({
    method: "post",
    baseURL: "http://localhost:8080/members/sign-up",
    headers: {
      "Content-Type": "application/json",
    },
    data,
  })
    .then((rsp) => console.log(rsp))
    .catch((err) => console.log(data));
};
