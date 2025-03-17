import React, { useState } from "react";
import { creatPost } from "../services/post-service";
import {
  Form,
  FormGroup,
  Label,
  Input,
  Button,
  FormFeedback,
  Container,
  Card,
  CardHeader,
  CardBody,
} from "reactstrap";

const AddPost = () => {
  const [post, setPost] = useState({
    title: "",
    content: "",
    categoryId: 1,
    userId: "1",
  });

  const [errors, setErrors] = useState({});

  const categories = [
    { id: 1, name: "Technology" },
    { id: 2, name: "Health" },
    { id: 3, name: "Business" },
    { id: 4, name: "Lifestyle" },
  ];

  const handleChange = (e) => {
    setPost({ ...post, [e.target.name]: e.target.value });
  };

  const handleCategoryChange = (e) => {
    setPost({ ...post, categoryId: parseInt(e.target.value) });
  };

  const validate = () => {
    let newErrors = {};

    if (!post.title || post.title.length < 3) {
      newErrors.title = "Title must be at least 3 characters long.";
    }

    if (!post.content || post.content.length < 10) {
      newErrors.content = "Content must be at least 10 characters long.";
    }

    if (!post.categoryId || post.categoryId === 0) {
      newErrors.categoryId = "Please select a valid category.";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (validate()) {
      creatPost(post)
        .then(() => {
          alert("Post Added Successfully!");
          setPost({ title: "", content: "", categoryId: 1, userId: "1" });
          setErrors({});
        })
        .catch(() => {});
    }
  };

  const handleReset = () => {
    setPost({ title: "", content: "", categoryId: 1, userId: "1" });
    setErrors({});
  };

  return (
    <Container className="mt-4 d-flex justify-content-center">
      <Card className="shadow-lg p-4" style={{ maxWidth: "500px", width: "100%", borderRadius: "10px" }}>
        <CardHeader className="bg-dark text-white text-center">
          <h3>Add a New Post</h3>
        </CardHeader>
        <CardBody>
          <Form onSubmit={handleSubmit}>
            <FormGroup>
              <Label for="title">Title</Label>
              <Input
                type="text"
                name="title"
                id="title"
                placeholder="Enter post title"
                value={post.title}
                onChange={handleChange}
                invalid={!!errors.title}
              />
              <FormFeedback>{errors.title}</FormFeedback>
            </FormGroup>
            <FormGroup>
              <Label for="content">Content</Label>
              <Input
                type="textarea"
                name="content"
                id="content"
                placeholder="Enter post content"
                value={post.content}
                onChange={handleChange}
                invalid={!!errors.content}
                style={{ minHeight: "120px" }}
              />
              <FormFeedback>{errors.content}</FormFeedback>
            </FormGroup>
            <FormGroup>
              <Label for="categoryId">Category</Label>
              <Input
                type="select"
                name="categoryId"
                id="categoryId"
                value={post.categoryId}
                onChange={handleCategoryChange}
                invalid={!!errors.categoryId}
              >
                {categories.map((cat) => (
                  <option key={cat.id} value={cat.id}>
                    {cat.id} - {cat.name}
                  </option>
                ))}
              </Input>
              <FormFeedback>{errors.categoryId}</FormFeedback>
            </FormGroup>
            <Button color="dark" block className="mt-3" type="submit">
              Add Post
            </Button>
            <Button color="secondary" block className="mt-2" type="button" onClick={handleReset}>
              Reset
            </Button>
          </Form>
        </CardBody>
      </Card>
    </Container>
  );
};

export default AddPost;
