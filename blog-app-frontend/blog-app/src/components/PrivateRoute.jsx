import { Outlet } from "react-router-dom";
import { isLoggedIn } from "../auth";

const PrivateRoute = () => {

  return isLoggedIn() ? <Outlet /> : <h2 className="text-center mt-4"> Please log in to create a post.</h2>;
};

export default PrivateRoute;
