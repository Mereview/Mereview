import axios from "axios";

export const postSignUp = (data) => {
  // console.log(data.file);
  // let submitData: null | FormData = null;
  // if (data.file) {
  //   data.file.append(
  //     "request",
  //     new Blob([JSON.stringify(data)], { type: "application/json" })
  //   );
  //   submitData = data.file;
  // } else {
  //   const formData = new FormData();
  //   formData.append("file", null);
  //   formData.append(
  //     "request",
  //     new Blob([JSON.stringify(data)], { type: "application/json" })
  //   );
  //   submitData = formData;
  // }
  // console.log(submitData);

  console.log(data.get("file"));
  console.log(data.get("request"));

  axios
    .post(
      "http://localhost:8080/api/members/sign-up", // baseURL은 여기서 URL로 수정
      data,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }
    )
    .then((rsp) => alert("회원가입이 완료되었습니다!"))
    .catch((err) => {
      console.log(data.get("file"));
      console.log(data.get("request"));
      console.log(err.message);
      console.log(err.data);
    });
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
