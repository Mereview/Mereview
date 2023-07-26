import { createSlice } from "@reduxjs/toolkit";

const userSlice = createSlice({
  name: "user",
  initialState: {
    isAthenticated: false,
    token: "",
  },
  reducers: {
    login(state) {
      state.isAthenticated = !state.isAthenticated;
    },
  },
});

export default userSlice;
export const uiActions = userSlice.actions;
