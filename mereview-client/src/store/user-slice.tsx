import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import {
  InputDataInterface,
  UserInterface,
} from "../components/interface/UserInterface";
const initialState: UserInterface = {
  isAthenticated: false,
  thirdModal: false,
  token: "",
  email: "",
  nickname: "",
  signUpData: {
    email: "",
    password: "",
    nickname: "",
    birthDate: "",
    gender: "",
    file: "",
    interest: [{}],
  },
};
const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    authorization(state, action) {
      console.log(action.payload);
      state.isAthenticated = true;
      state.token = action.payload.accessToken;
      state.email = action.payload.email;
      state.nickname = action.payload.nickname;
      localStorage.setItem("id", action.payload.id);
      localStorage.setItem("token", action.payload.accessToken);
      console.log("로그인성공");
    },
    logout(state) {
      state.isAthenticated = false;
      state.token = "";
      state.email = "";
      state.nickname = "";
      localStorage.removeItem("token");
    },
    modal_toggler(state) {
      state.thirdModal = !state.thirdModal;
    },
    signUp_step1(state, action: PayloadAction<InputDataInterface>) {
      // 정보입력
      state.signUpData.email = action.payload.email;
      state.signUpData.password = action.payload.password;
      state.signUpData.nickname = action.payload.nickname;
      state.signUpData.birthDate = action.payload.birthDate;
      state.signUpData.gender = action.payload.gender;
    },

    signUp_step3(state, action) {
      // 관심사 선택
      state.signUpData.interest = action.payload;

      // const data = state.signUpData;

      // fetch 이용해서 백에 DATA POST
    },
    authToggler(state) {
      state.isAthenticated = true;
    },
  },
});

export default userSlice;
export const userActions = userSlice.actions;
