export interface SearchBoxInterface {
  sortBy: string;
  setSortBy: React.Dispatch<React.SetStateAction<string>>;
  dateDescend: boolean;
  setDateDescend: React.Dispatch<React.SetStateAction<boolean>>;
  recommendDescend: boolean;
  setRecommendDescend: React.Dispatch<React.SetStateAction<boolean>>;
}
