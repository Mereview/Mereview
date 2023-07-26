import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import IndexPage from "./pages/IndexPage";
import TestPage from "./pages/TestPage";
import ReviewHome from "./pages/ReivewHome";
import ReviewWrite from "./pages/ReviewWrite";
import ReviewDetail from "./pages/ReviewDetail";
import ProfilePage from "./pages/ProfilePage";
import NavigationBar from "./components/NavigationBar";

function App() {
  return (
    <div className="App">
      <Router>
        <NavigationBar />
        <Routes>
          <Route path="/" Component={IndexPage}></Route>
          <Route path="/test" Component={TestPage}></Route>
          <Route path="/review" Component={ReviewHome}></Route>
          <Route path="/review/write" Component={ReviewWrite}></Route>
          <Route path="/review/detail" Component={ReviewDetail}></Route>
          <Route path="/profile" Component={ProfilePage}></Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
