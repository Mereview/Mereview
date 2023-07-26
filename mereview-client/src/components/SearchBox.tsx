import React from "react";
import Form from "react-bootstrap/Form";
import "../styles/css/SearchBox.css";

const SearchBox = () => {
  return (
    <>
      <Form.Control
        className="search-box"
        type="text"
        placeholder="제목, 장르로 검색"
      />
    </>
  );
};

export default SearchBox;
