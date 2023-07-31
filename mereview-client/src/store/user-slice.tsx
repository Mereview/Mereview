import { createSlice } from "@reduxjs/toolkit";

const userSlice = createSlice({
  name: "user",
  initialState: {
    isAthenticated: false,
    token: "",
    email: "",
    nickname: "",
    signUpData: {
      email: "",
      password: "",
      nickname: "",
      profileURL: "",
      birth: "",
      gender: "",
      interest: [],
    },
  },
  reducers: {
    login(state, payload) {
      state.isAthenticated = !state.isAthenticated;
      // state.email = payload
      // state.profileURL = payload
      // state.gender = payload
      // state.birth = 'a'
    },
    signUp_step1(state, payload) {},
    signUp_step2(state, payload) {},
  },
});

export default userSlice;
export const userActions = userSlice.actions;
