import { createSlice } from "@reduxjs/toolkit";

const uiSlice = createSlice({
  name: "ui",
  initialState: {
    isModal: false,
  },
  reducers: {
    toggle(state) {
      state.isModal = !state.isModal;
    },
  },
});

export default uiSlice;
export const uiActions = uiSlice.actions;
