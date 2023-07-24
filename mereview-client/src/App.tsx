import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import IndexPage from "./pages/IndexPage";
function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" Component={IndexPage}></Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
