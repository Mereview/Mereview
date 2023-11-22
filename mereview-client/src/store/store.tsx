import { configureStore } from "@reduxjs/toolkit";
import uiSlice from "./ui-silce";
import userSlice from "./user-slice";
import notificationSlice from "./notification-slice";
const store = configureStore({
  reducer: {
    ui: uiSlice.reducer,
    user: userSlice.reducer,
    notification: notificationSlice.reducer,
  },
});
export default store;
