import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import {
  InputDataInterface,
  UserInterface,
} from "../components/interface/UserInterface";
const initialState: UserInterface = {
  isAthenticated: false,
  id: null,
  thirdModal: false,
  token: "",
  email: "",
  gender: "",
  birthDate: "",
  nickname: "",
  interests: [],
  tiers: [],
  achievements: [],
};
const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    authorization(state, action) {
      console.log(action.payload);
      state.isAthenticated = true;
      state.id = action.payload.id;
      state.email = action.payload.email;
      state.gender = action.payload.gender;
      state.nickname = action.payload.nickname;
      state.birthDate = action.payload.birthDate;
      state.interests = action.payload.interests;
      console.log("로그인성공");
    },
    logout(state) {
      state.isAthenticated = false;
      state.token = "";
      state.email = "";
      state.nickname = "";
      localStorage.removeItem("token");
      localStorage.removeItem("id");
    },
    modal_toggler(state) {
      state.thirdModal = !state.thirdModal;
    },

    authToggler(state) {
      state.isAthenticated = true;
    },
  },
});

export default userSlice;
export const userActions = userSlice.actions;
