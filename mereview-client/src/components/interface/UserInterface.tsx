export interface UserInterface {
  isAthenticated: boolean;
  thirdModal: boolean;
  id: number | null;
  token: string | null;
  email: string | null;
  nickname: string | null;
  gender: string | null;
  birthDate: string | null;
  interests: [];
  tiers: [];
  achievements: [];
  profile_URL: null | string;
}

export interface InputDataInterface {
  email: null | string;
  password: null | string;
  password2?: null | string;
  nickname: null | string;
  birthDate: null | string;
  gender: null | string;
}
