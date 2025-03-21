import React, { useState } from "react";
import { Link } from "react-router-dom";
import { Card, CardBody, CardHeader, Container, Form, FormGroup, Input, Label, Button, Alert } from "reactstrap";
import Base from "../components/Base";
import { signUp } from "../services/user-service";

const Signup = () => {
  const [formData, setFormData] = useState({
    name: "",
    about: "",
    email: "",
    password: ""
  });

  const [errors, setErrors] = useState({});
  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    const { id, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [id]: value
    }));
  };

  const handleReset = () => {
    setFormData({
      name: "",
      about: "",
      email: "",
      password: ""
    });
    setErrors({});
    setMessage("");
  };

  const validate = () => {
    let newErrors = {};
    if (!formData.name.trim()) newErrors.name = "Name is required";
    if (!formData.about.trim()) newErrors.about = "About section cannot be empty";
    if (!formData.email.trim()) {
      newErrors.email = "Email is required";
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = "Invalid email format";
    }
    if (formData.password.length < 6) newErrors.password = "Password must be at least 6 characters";
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");

    if (validate()) {
      try {
        const resp = await signUp(formData);
        setMessage("User registered successfully! You can now log in.");
        handleReset();
      } catch (error) {
        if (error.response && error.response.data && error.response.data.message) {
          setMessage(error.response.data.message);
        } else {
          setMessage("Registration failed. Please try again.");
        }
      }
    }
  };

  return (
    <Base>
      <div className="d-flex justify-content-end p-3 gap-2">
        <Link to="/">
          <Button color="dark">Home</Button>
        </Link>
        <Link to="/login">
          <Button color="dark">Login</Button>
        </Link>
      </div>

      <Container className="mt-4 d-flex justify-content-center">
        <Card className="shadow-lg p-4" style={{ maxWidth: "450px", width: "100%", borderRadius: "10px" }}>
          <CardHeader className="bg-dark text-white text-center">
            <h3>Register</h3>
          </CardHeader>
          <CardBody>
            {message && <Alert color={message.includes("successful") ? "success" : "danger"}>{message}</Alert>}
            <Form onSubmit={handleSubmit}>
              <FormGroup>
                <Label for="name">Full Name</Label>
                <Input type="text" id="name" placeholder="Enter your name" value={formData.name} onChange={handleChange} />
                {errors.name && <small className="text-danger">{errors.name}</small>}
              </FormGroup>
              <FormGroup>
                <Label for="about">About You</Label>
                <Input type="textarea" id="about" rows="3" placeholder="Tell us about yourself" value={formData.about} onChange={handleChange} />
                {errors.about && <small className="text-danger">{errors.about}</small>}
              </FormGroup>
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
              <div className="d-flex justify-content-between">
                <Button color="dark" type="submit">Register</Button>
                <Button color="secondary" type="button" onClick={handleReset}>Reset</Button>
              </div>
            </Form>
          </CardBody>
        </Card>
      </Container>
    </Base>
  );
};

export default Signup;
