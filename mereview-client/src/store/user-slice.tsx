import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import {
  InputDataInterface,
  UserInterface,
} from "../components/interface/UserInterface";
const initialState: UserInterface = {
  isAthenticated: true,
  thirdModal: false,
  token: "",
  email: "",
  nickname: "",
  signUpData: {
    email: "",
    password: "",
    password2: "",
    nickname: "",
    birth: "",
    gender: "",
    profileURL: "",
    interest: [{}],
  },
};
const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    login(state, payload) {
      state.isAthenticated = true;
      // state.email = payload
      // state.profileURL = payload
      // state.gender = payload
      // state.birth = 'a'
    },
    logout(state) {
      state.isAthenticated = false;
      state.token = "";
      state.email = "";
      state.nickname = "";
    },
    modal_toggler(state) {
      state.thirdModal = !state.thirdModal;
    },
    signUp_step1(state, action: PayloadAction<InputDataInterface>) {
      // 정보입력
      state.signUpData.email = action.payload.email;
      state.signUpData.password = action.payload.password;
      state.signUpData.password2 = action.payload.password2;
      state.signUpData.nickname = action.payload.nickname;
      state.signUpData.birth = action.payload.birth;
      state.signUpData.gender = action.payload.gender;
    },
    signUp_step2(state, action) {
      // 사진선택
      state.signUpData.profileURL = action.payload;
    },
    signUp_step3(state, action) {
      // 관심사 선택
      state.signUpData.interest = action.payload;

      // const data = state.signUpData;

      // fetch 이용해서 백에 DATA POST
    },
  },
});

export default userSlice;
export const userActions = userSlice.actions;
