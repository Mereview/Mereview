export interface UserInterface {
  isAthenticated: boolean;
  thirdModal: boolean;
  token: string | null;
  email: string | null;
  nickname: string | null;
  signUpData: SignUpInterface;
}

export interface InputDataInterface {
  email: null | string;
  password: null | string;
  password2?: null | string;
  nickname: null | string;
  birthDate: null | string;
  gender: null | string;
}
export interface SignUpInterface extends InputDataInterface {
  file: string | null;
  interest: [
    {
      [genreId: string]: [genreName: string];
    }
  ];
}
