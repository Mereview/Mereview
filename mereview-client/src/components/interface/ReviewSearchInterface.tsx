export interface ReviewSearchInterface {
  searchKeyword: string;
  setSearchKeyword: React.Dispatch<React.SetStateAction<string>>;
  searchCriteria: string;
  setSearchCriteria: React.Dispatch<React.SetStateAction<string>>;
  emptySearchKeyword: boolean;
  setEmptySearchKeyword: React.Dispatch<React.SetStateAction<boolean>>;
}
