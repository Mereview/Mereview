import {
  FormControl,
  Select,
  MenuItem,
  SelectChangeEvent,
  TextField,
  FormControlLabel,
  Switch,
} from "@mui/material";
import { BsSearch, BsSortDown, BsSortUpAlt } from "react-icons/bs";
import { SearchBoxInterface } from "./interface/SearchBoxInterface";
import "../styles/css/SearchBox.css";
import { ChangeEvent } from "react";

const SearchBox = ({
  searchBoxProps,
  searchSubmit,
}: {
  searchBoxProps: SearchBoxInterface;
  searchSubmit: () => void;
}) => {
  const {
    sortBy,
    setSortBy,
    dateDescend,
    setDateDescend,
    recommendDescend,
    setRecommendDescend,
    searchParam,
    setSearchParam,
    searchCriteria,
    setSearchCriteria,
    onlyInterest,
    setOnlyInterest,
  } = searchBoxProps;

  const handleCriteria = (event: SelectChangeEvent) => {
    setSearchCriteria(event.target.value as string);
  };

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

  const handleSearchParam = (event: ChangeEvent<HTMLInputElement>) => {
    setSearchParam(event.target.value as string);
  };

  // const handleSortByRandom = () => {
  //   setSortBy("random");
  // }

  return (
    <>
      <FormControl className="search-form">
        <div className="search-container">
          <Select
            id="criteria-select"
            className="criteria-select"
            value={searchCriteria}
            onChange={handleCriteria}
            sx={{ height: "34.25px" }}
          >
            <MenuItem value="제목">제목</MenuItem>
            <MenuItem value="장르">장르</MenuItem>
            <MenuItem value="작성자">작성자</MenuItem>
          </Select>
          <TextField
            id="search-keyword"
            className="search-keyword"
            onChange={handleSearchParam}
          />
          <button
            type="submit"
            className="search-button"
            onClick={searchSubmit}
          >
            <BsSearch className="search-icon" />
          </button>
        </div>
      </FormControl>
      <div className="sort-container">
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
        <FormControlLabel
          className="only-interest-switch"
          control={
            <Switch checked={onlyInterest} onChange={handleOnlyInterest} />
          }
          label="관심 장르만 보기"
          labelPlacement="end"
        />
      </div>
    </>
  );
};

export default SearchBox;
