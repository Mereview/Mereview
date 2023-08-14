import {
  FormControl,
  Select,
  MenuItem,
  SelectChangeEvent,
  TextField,
} from "@mui/material";
import { BsSearch } from "react-icons/bs";
import { ReviewSearchInterface } from "./interface/ReviewSearchInterface";
import "../styles/css/ReviewSearch.css";
import { useEffect, useRef, ChangeEvent } from "react";

const ReviewSearch = ({
  searchProps,
  searchSubmit,
}: {
  searchProps: ReviewSearchInterface;
  searchSubmit: () => void;
}) => {
  const {
    searchKeyword,
    setSearchKeyword,
    searchCriteria,
    setSearchCriteria,
    emptySearchKeyword,
    setEmptySearchKeyword,
  } = searchProps;
  const paramRef = useRef(null);
  useEffect(() => {
    if (emptySearchKeyword) {
      paramRef.current.focus();
    }
  }, [emptySearchKeyword]);

  const handleCriteria = (event: SelectChangeEvent) => {
    setSearchCriteria(event.target.value as string);
  };

  const handleSearchParam = (event: ChangeEvent<HTMLInputElement>) => {
    setSearchKeyword(event.target.value as string);
  };

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
            {/* <MenuItem value="장르">장르</MenuItem> */}
            <MenuItem value="작성자">작성자</MenuItem>
          </Select>
          <TextField
            inputRef={paramRef}
            id="search-keyword"
            className="search-keyword"
            onChange={handleSearchParam}
            error={emptySearchKeyword && searchKeyword === ""}
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
    </>
  );
};

export default ReviewSearch;
