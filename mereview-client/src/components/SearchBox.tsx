import React from "react";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import "../styles/css/SearchBox.css";

const SearchBox = () => {
  return (
    <div>
      Hello?
      <Row>
        <Col>col1 ^^!</Col>
        <div className="col">col2 ^&^</div>
      </Row>
      <div className="row">
        <Col>col1 ^@^!</Col>
        <div className="col">col2 ^&!^</div>
      </div>
    </div>
  );
};

export default SearchBox;
