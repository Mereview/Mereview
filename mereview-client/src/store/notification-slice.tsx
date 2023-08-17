import { createSlice } from "@reduxjs/toolkit";

const notificationSlice = createSlice({
  name: "notification",
  initialState: {
    notification: {},
  },
  reducers: {
    getNotification(state, action) {
      state.notification = action.payload;
    },
  },
});
export default notificationSlice;
export const notificationActions = notificationSlice.actions;
