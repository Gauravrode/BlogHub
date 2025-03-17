import React, { useState } from "react";
import { useLocation, Link } from "react-router-dom";
import { Card, CardBody, CardHeader, Container, Form, FormGroup, Input, Label, Button, Alert } from "reactstrap";
import Base from "../components/Base";
import { loginUser } from "../services/user-service";
import { doLogin } from "../auth";

const Login = () => {
  const location = useLocation();

  const [formData, setFormData] = useState({
    email: "",
    password: ""
  });

  const [errors, setErrors] = useState({});
  const [loginError, setLoginError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const handleChange = (e) => {
    const { id, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [id]: value
    }));
  };

  const validate = () => {
    let newErrors = {};
    if (!formData.email.trim()) {
      newErrors.email = "Email is required";
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = "Invalid email format";
    }
    if (formData.password.length < 6) {
      newErrors.password = "Password must be at least 6 characters";
    }
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setLoginError("");
    setSuccessMessage("");

    if (validate()) {
      loginUser(formData)
        .then((jwtTokenData) => {
          doLogin(jwtTokenData, () => {});
          setSuccessMessage("Login successful! Redirecting...");
          setTimeout(() => {
            window.location.href = "/";
          }, 1500);
        })
        .catch((error) => {
          if (error.response) {
            setLoginError(error.response.data?.message || "Invalid credentials. Please try again.");
          } else {
            setLoginError("Network error. Please try again later.");
          }
        });
    }
  };

  return (
    <Base>
      <div className="d-flex justify-content-end p-3 gap-2">
        <Link to="/">
          <Button color="dark">Home</Button>
        </Link>
        <Link to="/signup">
          <Button color="dark">Register</Button>
        </Link>
      </div>

      <Container className="mt-4 d-flex justify-content-center">
        <Card className="shadow-lg p-4" style={{ maxWidth: "450px", width: "100%", borderRadius: "10px" }}>
          <CardHeader className="bg-dark text-white text-center">
            <h3>Login</h3>
          </CardHeader>
          <CardBody>
            {loginError && <Alert color="danger">{loginError}</Alert>}
            {successMessage && <Alert color="success">{successMessage}</Alert>}

            <Form onSubmit={handleSubmit}>
              <FormGroup>
                <Label for="email">Email Address</Label>
                <Input type="email" id="email" placeholder="Enter your email" value={formData.email} onChange={handleChange} />
                {errors.email && <small className="text-danger">{errors.email}</small>}
              </FormGroup>
              <FormGroup>
                <Label for="password">Password</Label>
                <Input type="password" id="password" placeholder="Enter your password" value={formData.password} onChange={handleChange} />
                {errors.password && <small className="text-danger">{errors.password}</small>}
              </FormGroup>
              <Button color="dark" block className="mt-3" type="submit">
                Login
              </Button>
            </Form>
          </CardBody>
        </Card>
      </Container>
    </Base>
  );
};

export default Login;
