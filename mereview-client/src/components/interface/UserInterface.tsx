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
  password2: null | string;
  nickname: null | string;
  birth: null | string;
  gender: null | string;
}
export interface SignUpInterface extends InputDataInterface {
  profileURL: string | null;
  interest: [
    {
      [genreId: string]: [genreName: string];
    }
  ];
}
