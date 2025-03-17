import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import {
  Collapse,
  Navbar,
  NavbarToggler,
  NavbarBrand,
  Nav,
  NavItem,
  NavLink,
} from "reactstrap";
import { doLogout, getCurrentUserDetail, isLoggedIn } from "../auth";

const CustomNavbar = () => {
  const [login, setLogin] = useState(false);
  const [user, setUser] = useState(undefined);
  const [isOpen, setIsOpen] = useState(false);

  useEffect(() => {
    setLogin(isLoggedIn());
    setUser(getCurrentUserDetail());
  }, [login]);

  const logout = () => {
    doLogout(() => {
      setLogin(false);
      setTimeout(() => {
        window.location.href = "/";
      }, 1500);
    });
  };

  const toggle = () => setIsOpen(!isOpen);
  const location = useLocation();

  if (location.pathname === "/login" || location.pathname === "/signup" || location.pathname === "privateRoute/createPost") {
    return null;
  }

  return (
    <Navbar color="dark" dark expand="md" className="px-3 mt-0 w-100">
      <NavbarBrand href="/">My Blog</NavbarBrand>
      <NavbarToggler onClick={toggle} />
      <Collapse isOpen={isOpen} navbar>
        <Nav className="me-auto" navbar>
          <NavItem>
            <NavLink href="/">Home</NavLink>
          </NavItem>
          <NavItem>
            <NavLink href="/about">About</NavLink>
          </NavItem>
        </Nav>
        <Nav navbar>
          {login ? (
            <>
              <NavItem>
                <NavLink href="/privateroute/dashboard">Dash Board</NavLink>
              </NavItem>
              <NavItem>
                <NavLink onClick={logout}>Logout</NavLink>
              </NavItem>
              <NavItem>
                <NavLink href="/privateroute/userprofile">{user.email}</NavLink>
              </NavItem>
            </>
          ) : (
            <>
              <NavItem>
                <NavLink href="/signup">Signup</NavLink>
              </NavItem>
              <NavItem>
                <NavLink href="/login">Login</NavLink>
              </NavItem>
            </>
          )}
        </Nav>
      </Collapse>
    </Navbar>
  );
};

export default CustomNavbar;
