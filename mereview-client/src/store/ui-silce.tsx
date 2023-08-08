import { createSlice } from "@reduxjs/toolkit";

const uiSlice = createSlice({
  name: "ui",
  initialState: {
    isModal: false,
    tabToggle: "login",
    grantAuth: false,
  },
  reducers: {
    tabChange(state, action) {
      if (state.tabToggle === "login" && action.payload === "signup") {
        state.tabToggle = "signup";
      } else if (state.tabToggle === "signup" && action.payload === "login") {
        state.tabToggle = "login";
      } else if (action.payload === "signUpCompleted") {
        state.tabToggle = "login";
      }
    },
    grantAuthToggler(state) {
      state.grantAuth = true;
    },
  },
});

export default uiSlice;
export const uiActions = uiSlice.actions;
