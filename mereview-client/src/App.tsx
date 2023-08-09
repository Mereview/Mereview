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
import PageNotFound404 from "./pages/PageNotFound404";
function App() {
  const dispatch = useDispatch();
  const getUserInfo = () => {
    const id = localStorage.getItem("id");
    if (id) {
      dispatch(userActions.authToggler());
      const getUserInfoURL = `http://localhost:8080/api/members/${id}`;
      axios
        .get(getUserInfoURL)
        .then((res) => res.data.data)
        .then((data) => {
          console.log(data);
          dispatch(userActions.authorization(data));
        })
        .catch((err) => console.log("사용자 인증에서 오류가 발생했습니다."));
    }
  };
  useEffect(getUserInfo, []);
  const isAthenticated = useSelector((state: any) => state.user.isAthenticated);
  const user = useSelector((state: any) => state.user.user);
  return (
    <div className="App">
      <Router>
        {isAthenticated && user && <NavigationBar user={user} />}
        <Routes>
          <Route path="/" Component={IndexPage}></Route>
          <Route path="/review" Component={ReviewHome}></Route>
          <Route path="/review/write" Component={ReviewWrite}></Route>
          <Route path="/review/:id" Component={ReviewDetailPage}></Route>
          <Route path="/profile" Component={ProfilePage}></Route>
          <Route path="/profile/:id" Component={ProfilePage}></Route>
          <Route path="/404" Component={PageNotFound404}></Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
