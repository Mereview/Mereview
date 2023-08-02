import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import IndexPage from "./pages/IndexPage";
import ReviewHome from "./pages/ReivewHome";
import ReviewWrite from "./pages/ReviewWrite";
import ReviewDetailPage from "./pages/ReviewDetailPage";
import ProfilePage from "./pages/ProfilePage";
import NavigationBar from "./components/NavigationBar";
import { useSelector } from "react-redux/es/hooks/useSelector";
import "./styles/css/App.css";
function App() {
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
