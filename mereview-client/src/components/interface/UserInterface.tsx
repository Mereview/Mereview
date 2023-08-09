export interface UserInterface {
  isAthenticated: boolean;
  thirdModal: boolean;
  user: Object;
}

export interface InputDataInterface {
  email: null | string;
  password: null | string;
  password2?: null | string;
  nickname: null | string;
  birthDate: null | string;
  gender: null | string;
}
