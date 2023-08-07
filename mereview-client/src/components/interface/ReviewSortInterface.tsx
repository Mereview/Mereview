export interface ReviewSortInterface {
  sortBy: string;
  setSortBy: React.Dispatch<React.SetStateAction<string>>;
  dateDescend: boolean;
  setDateDescend: React.Dispatch<React.SetStateAction<boolean>>;
  recommendDescend: boolean;
  setRecommendDescend: React.Dispatch<React.SetStateAction<boolean>>;
  searchTerm: string;
  setSearchTerm: React.Dispatch<React.SetStateAction<string>>;
  onlyInterest: boolean;
  setOnlyInterest: React.Dispatch<React.SetStateAction<boolean>>;
}
