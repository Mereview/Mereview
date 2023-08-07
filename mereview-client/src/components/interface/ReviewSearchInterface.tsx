export interface ReviewSearchInterface {
  searchParam: string;
  setSearchParam: React.Dispatch<React.SetStateAction<string>>;
  searchCriteria: string;
  setSearchCriteria: React.Dispatch<React.SetStateAction<string>>;
  emptySearchParam: boolean;
  setEmptySearchParam: React.Dispatch<React.SetStateAction<boolean>>;
}
