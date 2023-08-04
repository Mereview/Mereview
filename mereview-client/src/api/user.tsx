import axios from "axios";
import userSlice from "../store/user-slice";
import { userActions } from "../store/user-slice";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";

export const postSignUp = (data) => {
  const formData = new FormData();
  formData.append("file", data.file);
  formData.append(
    "request",
    new Blob([JSON.stringify(data)], { type: "application/json" })
  );
  console.log(formData.get("request"));
  axios
    .post(
      "http://localhost:8080/api/members/sign-up", // baseURL은 여기서 URL로 수정
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

// export const login = (inputUserData) => {
//   axios
//     .post("http://localhost:8080/members/login", inputUserData, {
//       headers: {
//         "Content-Type": "application/json",
//       },
//     })
//     .then((res) => res.data.data)
//     .then((data) => {
//       console.log(data);
//       const dispatch = useDispatch();
//       const history = useNavigate();

//       dispatch(userActions.authorization(data.refreshToken));
//       history("/review");
//     })
//     .catch((err) => console.log(err, inputUserData));
// };
