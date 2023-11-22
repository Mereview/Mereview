import { createSlice } from "@reduxjs/toolkit";
import { UserInterface } from "../components/interface/UserInterface";
const initialState: UserInterface = {
  isAthenticated: false,
  thirdModal: false,
  user: {},
};
const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    authorization(state, action) {
      state.isAthenticated = true;
      state.user = action.payload;
    },
    logout(state) {
      localStorage.removeItem("token");
      localStorage.removeItem("id");
      return initialState;
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
