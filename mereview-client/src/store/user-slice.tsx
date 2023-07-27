import { createSlice } from "@reduxjs/toolkit";

const userSlice = createSlice({
  name: "user",
  initialState: {
    isAthenticated: false,
    token: "",
    email: "",
    profileURL: "",
    gender: "",
    birth: "",
    interest: [],
  },
  reducers: {
    login(state, payload) {
      state.isAthenticated = !state.isAthenticated;
      // state.email = payload
      // state.profileURL = payload
      // state.gender = payload
      // state.birth = 'a'
    },
  },
});

export default userSlice;
export const userActions = userSlice.actions;
