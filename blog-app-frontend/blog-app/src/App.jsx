import "./App.css";
import { Routes, Route, BrowserRouter } from "react-router-dom";
import Home from "./pages/Home";
import Signup from "./pages/Signup";
import Login from "./pages/Login";
import About from "./pages/About";
import PrivateRoute from "./components/PrivateRoute"; 
import CreatePost from "./pages/DashBoard";
import DashBoard from "./pages/DashBoard";
import UserProfile from "./pages/UserProfile";

function App() {
  return (
   <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/about" element={<About />} />
     
        <Route path="/privateroute" element={<PrivateRoute />}>
          <Route path="dashboard" element={<DashBoard />} />
          <Route path="userprofile" element={<UserProfile />} />
        </Route>

      </Routes>
   </BrowserRouter>
  );
}

export default App;
