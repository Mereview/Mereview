import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import IndexPage from "./pages/IndexPage";
import ReviewHome from "./pages/ReviewHome";
import ReviewWrite from "./pages/ReviewWrite";
import ReviewDetailPage from "./pages/ReviewDetailPage";
import ProfilePage from "./pages/ProfilePage";
import MovieDetail from "./pages/MovieDetail";
import NavigationBar from "./components/NavigationBar";
import { useDispatch, useSelector } from "react-redux";
import { useEffect } from "react";
import "./styles/css/App.css";
import axios from "axios";
import { userActions } from "./store/user-slice";
import PageNotFound404 from "./pages/PageNotFound404";
import { searchMemberInfoSimple } from "./api/members";
function App() {
  const dispatch = useDispatch();

  useEffect(() => {
    const getUserInfo = () => {
      const id = localStorage.getItem("id");
      if (id) {
        dispatch(userActions.authToggler());
        searchMemberInfoSimple(
          Number(id),
          (res) => dispatch(userActions.authorization(res.data.data)),
          (err) => console.log("사용자 인증 오류 발생")
        );
      }
    };
    getUserInfo();
  }, []);
  const isAuthenticated = useSelector(
    (state: any) => state.user.isAthenticated
  );
  const user = useSelector((state: any) => state.user.user);
  return (
    <div className="App">
      <Router>
        {isAuthenticated && user && <NavigationBar user={user} />}
        <Routes>
          <Route path="/" Component={IndexPage}></Route>
          <Route path="/review" Component={ReviewHome}></Route>
          <Route path="/review/write" Component={ReviewWrite}></Route>
          <Route path="/review/:id" Component={ReviewDetailPage}></Route>
          <Route path="/profile" Component={ProfilePage}></Route>
          <Route path="/profile/:id" Component={ProfilePage}></Route>
          <Route path="/404" Component={PageNotFound404}></Route>
          <Route path="/movie/:id" Component={MovieDetail}></Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
