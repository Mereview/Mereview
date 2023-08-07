import axios from "axios";

export const postSignUp = (data) => {
  const formData = new FormData();
  formData.append("file", data.file[0]);
  formData.append(
    "request",
    new Blob([JSON.stringify(data)], { type: "application/json" })
  );
  axios
    .post(
      "http://i9c211.p.ssafy.io:8080/members/sign-up", // baseURL은 여기서 URL로 수정
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }
    )
    .then((rsp) => console.log(rsp, data))
    .catch((err) => console.log(data));
};

export const login = (inputUserData) => {
  axios
    .post("https://localhost:8080/members/login", inputUserData, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((res) => console.log("login success", inputUserData))
    .catch((err) => console.log(err));
};
