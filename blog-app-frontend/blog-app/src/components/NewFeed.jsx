import { useEffect, useState } from "react";
import { loadAllPost } from "../services/post-service";
import { Row, Col, Pagination, PaginationItem, PaginationLink } from "reactstrap";
import Post from "./Post";
import { toast } from "react-toastify";

const NewFeed = () => {
  const [postContent, setPostContent] = useState({ content: [], totalPages: 1 });
  const [currentPage, setCurrentPage] = useState(0);
  const pageSize = 5;

  useEffect(() => {
    fetchPosts(currentPage);
  }, [currentPage]);

  const fetchPosts = (page) => {
    loadAllPost(page, pageSize, "postId", "desc")
      .then((data) => {
        setPostContent(data || { content: [], totalPages: 1 });
      })
      .catch(() => {
        toast.error("Failed to load posts");
      });
  };

  const handlePageChange = (pageNumber) => {
    if (pageNumber >= 0 && pageNumber < postContent.totalPages) {
      setCurrentPage(pageNumber);
    }
  };

  return (
    <div className="container-fluid bg-secondary min-vh-100">
      <Row>
        <Col md={10} className="offset-md-1">
          {postContent.content?.length > 0 ? (
            postContent.content.map((post) => <Post key={post.postId} post={post} />)
          ) : (
            <p>No posts available.</p>
          )}
          <Pagination className="mt-4">
            <PaginationItem disabled={currentPage === 0}>
              <PaginationLink first onClick={() => handlePageChange(0)} />
            </PaginationItem>
            <PaginationItem disabled={currentPage === 0}>
              <PaginationLink previous onClick={() => handlePageChange(currentPage - 1)} />
            </PaginationItem>
            {[...Array(postContent.totalPages)].map((_, index) => (
              <PaginationItem key={index} active={index === currentPage}>
                <PaginationLink onClick={() => handlePageChange(index)}>{index + 1}</PaginationLink>
              </PaginationItem>
            ))}
            <PaginationItem disabled={currentPage >= postContent.totalPages - 1}>
              <PaginationLink next onClick={() => handlePageChange(currentPage + 1)} />
            </PaginationItem>
            <PaginationItem disabled={currentPage >= postContent.totalPages - 1}>
              <PaginationLink last onClick={() => handlePageChange(postContent.totalPages - 1)} />
            </PaginationItem>
          </Pagination>
        </Col>
      </Row>
    </div>
  );
};

export default NewFeed;
