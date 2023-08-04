import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import IndexPage from "./pages/IndexPage";
import ReviewHome from "./pages/ReivewHome";
import ReviewWrite from "./pages/ReviewWrite";
import ReviewDetailPage from "./pages/ReviewDetailPage";
import ProfilePage from "./pages/ProfilePage";
import NavigationBar from "./components/NavigationBar";
import { useDispatch, useSelector } from "react-redux";
import { useEffect } from "react";
import "./styles/css/App.css";
import axios from "axios";
import { userActions } from "./store/user-slice";
function App() {
  const dispatch = useDispatch();

  const getUserInfo = () => {
    const id = localStorage.getItem("id");
    if (id) {
      dispatch(userActions.authToggler());
      const getUserInfoURL = `http://localhost:8080/members/${id}`;
      axios.get(getUserInfoURL).then((res) => console.log("성공"));
    }
  };
  useEffect(getUserInfo, []);
  const isAthenticated = useSelector((state: any) => state.user.isAthenticated);
  return (
    <div className="App">
      <Router>
        {isAthenticated ? <NavigationBar /> : null}
        <Routes>
          <Route path="/" Component={IndexPage}></Route>
          <Route path="/review" Component={ReviewHome}></Route>
          <Route path="/review/write" Component={ReviewWrite}></Route>
          <Route path="/detail" Component={ReviewDetailPage}></Route>
          <Route path="/profile" Component={ProfilePage}></Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
