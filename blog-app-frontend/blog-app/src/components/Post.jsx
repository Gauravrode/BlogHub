import { Button, Card, CardBody, CardText } from "reactstrap";

function Post({ post }) {
  return (
    <Card className="border-0 shadow-sm mt-3">
      <CardBody>
        <h3>{post.title}</h3>
        <CardText>
          <p>{post.content.substring(0,30)}...</p>
        </CardText>
        <div>
          <Button>Read More</Button>
        </div>
      </CardBody>
    </Card>
  );
}

export default Post;
