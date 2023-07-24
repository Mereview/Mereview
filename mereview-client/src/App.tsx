import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import IndexPage from "./pages/IndexPage";
import TestPage from "./pages/TestPage";

function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" Component={IndexPage}></Route>
          <Route path="/test" Component={TestPage}></Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
