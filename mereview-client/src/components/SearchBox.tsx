import React, { useState, useEffect } from "react";
import { Dropdown, Row } from "react-bootstrap";
import Form from "react-bootstrap/Form";
import { BsSortDown, BsSortUpAlt } from "react-icons/bs";
import { SearchBoxInterface } from "./interface/SearchBoxInterface";
import "../styles/css/SearchBox.css";

const SearchBox = ({
  searchBoxProps,
}: {
  searchBoxProps: SearchBoxInterface;
}) => {
  const {
    sortBy,
    setSortBy,
    dateDescend,
    setDateDescend,
    recommendDescend,
    setRecommendDescend,
  } = searchBoxProps;

  const handleSortByDate = () => {
    if (sortBy === "date") setDateDescend(!dateDescend);
    else setSortBy("date");
  };

  const handleSortByRecommend = () => {
    if (sortBy === "recommend") setRecommendDescend(!recommendDescend);
    else setSortBy("recommend");
  };

  // const handleSortByRandom = () => {
  //   setSortBy("random");
  // }

  return (
    <>
      <div className="dropdown">
        <div className="dropdown-content">
          <div className="basis"></div>
        </div>
      </div>
      <Form.Control
        className="search-box"
        type="text"
        placeholder="제목, 장르로 검색"
      />
      <div className="sort-buttons">
        <button
          className={sortBy === "date" ? "selected" : ""}
          onClick={() => handleSortByDate()}
        >
          <span className="sort-icon">
            {dateDescend ? <BsSortDown /> : <BsSortUpAlt />}
          </span>
          최신순
        </button>
        <button
          className={sortBy === "recommend" ? "selected" : ""}
          onClick={() => handleSortByRecommend()}
        >
          <span className="sort-icon">
            {recommendDescend ? <BsSortDown /> : <BsSortUpAlt />}
          </span>
          추천순
        </button>
        {/* <button onClick={() => handleSortByRandom()}>무작위</button> */}
      </div>
    </>
  );
};

export default SearchBox;
