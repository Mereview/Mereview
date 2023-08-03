import { createSlice } from "@reduxjs/toolkit";

const uiSlice = createSlice({
  name: "ui",
  initialState: {
    isModal: false,
    tabToggle: "login",
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
  },
});

export default uiSlice;
export const uiActions = uiSlice.actions;
