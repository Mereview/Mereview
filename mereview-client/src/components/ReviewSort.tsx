import { Select, MenuItem, SelectChangeEvent, FormControlLabel, Switch } from "@mui/material";
import { BsSortDown, BsSortUpAlt } from "react-icons/bs";
import { ReviewSortInterface } from "./interface/ReviewSortInterface";
import "../styles/css/ReviewSort.css";
import { ChangeEvent } from "react";

const ReviewSort = ({ sortProps }: { sortProps: ReviewSortInterface }) => {
  const {
    sortBy,
    setSortBy,
    dateDescend,
    setDateDescend,
    recommendDescend,
    setRecommendDescend,
    searchTerm,
    setSearchTerm,
    onlyInterest,
    setOnlyInterest,
  } = sortProps;

  const handleSortByDate = () => {
    if (sortBy === "date") setDateDescend(!dateDescend);
    else setSortBy("date");
  };

  const handleSortByRecommend = () => {
    if (sortBy === "recommend") setRecommendDescend(!recommendDescend);
    else setSortBy("recommend");
  };

  const handleOnlyInterest = (event: ChangeEvent<HTMLInputElement>) => {
    setOnlyInterest(event.target.checked as boolean);
  };

  const handleSearchTerm = (event: SelectChangeEvent) => {
    setSearchTerm(event.target.value as string);
  };

  // const handleSortByRandom = () => {
  //   setSortBy("random");
  // }

  return (
    <>
      <div className="sort-container">
        <button className={sortBy === "date" ? "selected" : ""} onClick={() => handleSortByDate()}>
          <span className="sort-icon">{dateDescend ? <BsSortDown /> : <BsSortUpAlt />}</span>
          최신순
        </button>
        <button
          className={sortBy === "recommend" ? "selected" : ""}
          onClick={() => handleSortByRecommend()}
        >
          <span className="sort-icon">{recommendDescend ? <BsSortDown /> : <BsSortUpAlt />}</span>
          추천순
        </button>
        <span className="term-select">
          <Select
            id="term-select"
            value={searchTerm}
            onChange={handleSearchTerm}
            sx={{ height: "34.25px" }}
          >
            <MenuItem value={"all"}>전체 기간</MenuItem>
            <MenuItem value={1}>1개월</MenuItem>
            <MenuItem value={6}>6개월</MenuItem>
            <MenuItem value={12}>1년</MenuItem>
          </Select>
        </span>
        {/* <button onClick={() => handleSortByRandom()}>무작위</button> */}
        <FormControlLabel
          className="only-interest-switch"
          control={<Switch checked={onlyInterest} onChange={handleOnlyInterest} />}
          label="관심 장르만 보기"
          labelPlacement="end"
        />
      </div>
    </>
  );
};

export default ReviewSort;
