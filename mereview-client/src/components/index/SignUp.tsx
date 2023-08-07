import { Container, Row, Col } from "react-bootstrap";
import { Input, Button } from "../common/index";
import { useEffect } from "react";
import SelectInterest from "./SelectInterest";
import { useDispatch, useSelector } from "react-redux";
import { userActions } from "../../store/user-slice";
import { InputDataInterface } from "../interface/UserInterface";
import "../../styles/css/SignUp.css";
import "../../styles/css/ImageUploader.css";
import { useDropzone } from "react-dropzone";
import { useCallback, useState } from "react";
import axios from "axios";

const SignUp = () => {
  const formData = new FormData();

  const [animate, setAnimate] = useState(false);
  const [selectedGender, setSelectedGender] = useState<string>("");
  const [checkEmail, setCheckEmail] = useState(false);
  const [checking, setChecking] = useState(false); // 선택된 성별을 상태로 관리합니다.
  const [selectedImage, setSelectedImage] = useState<string | null>(null);
  const [fileData, setFileData] = useState<FormData | null>(null);
  const [verificationCode, setVerificationCode] = useState<string | null>("");
  const [inputData, setInputData] = useState<InputDataInterface>({
    email: null,
    password: null,
    password2: null,
    nickname: null,
    birthDate: null,
    gender: null,
  });
  const [passwordValid, setPasswordValid] = useState<boolean>(true);
  useEffect(() => {
    setAnimate(true);
  }, []);
  const dispatch = useDispatch();
  // 초기 상태로서 빈 문자열 또는 null로 초기화합니다.
  const valid = useSelector((state: any) => state.user.thirdModal);

  //
  const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    let { id, value } = event.target;
    if (id === "male" || id === "female") {
      setSelectedGender(value); // 바로 이렇게 바꿔도 되나...?
      id = "gender";
    } else if (value === "") {
      setPasswordValid(true);
    } else if (id === "password2" && inputData.password !== value) {
      setPasswordValid(false);
    } else if (id === "password2" && inputData.password === value) {
      setPasswordValid(true);
    }
    setInputData((prevInputData) => ({
      ...prevInputData,
      [id]: value,
    }));
  };
  const codeChangeHnadler = (event) => {
    setVerificationCode(event.target.value);
  };
  const signUp_step1 = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const isValid = Object.values(inputData).every((value) => value !== null);
    if (!isValid || !passwordValid) {
      console.log(inputData);
      alert("정보를 정확하게 입력해주세요!");
      return;
    }
    if (!checkEmail) {
      alert("메일 인증을 완료해주세요!");
      return;
    }
    dispatch(userActions.modal_toggler());
  };

  //이메일 인증 핸들러
  const emailCheckHandler = (event: React.MouseEvent<HTMLButtonElement>) => {
    const id = event.currentTarget.id;
    if (id === "step1" && inputData.email.includes("@")) {
      // axios 로 사용자에게 메일보내는 로직
      const SEND_EMAIL_URL = "http://localhost:8080/api/email/send";
      const data = { email: inputData.email };
      setChecking(true);
      alert(
        `${inputData.email} 로 메일을 보냈습니다. 인증번호를 확인해주세요!`
      );
      axios
        .post(SEND_EMAIL_URL, data, {
          headers: {
            "Content-Type": "application/json",
          },
        })
        .catch((err) => {
          alert("메일 전송에 실패했습니다!");
        });
    } else if (id === "step2") {
      const CHECK_EMAIL_URL = "http://localhost:8080/api/email/check";
      const data = {
        email: inputData.email,
        verificationCode: verificationCode,
      };

      axios
        .post(CHECK_EMAIL_URL, data, {
          headers: {
            "Content-Type": "application/json",
          },
        })
        .then((res) => {
          setCheckEmail(true);
          setChecking(false);
          alert("이메일 인증에 성공했습니다!");
        })
        .catch((err) => alert("인증번호를 확인해주세요!"));
    } else {
      alert("메일을 정확히 입력해주세요!");
    }
  };

  // 이미지업로드 함수부분
  const onDrop = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0];
    if (file) {
      console.log(file);
      const objectURL = URL.createObjectURL(file);
      setSelectedImage(objectURL);
      formData.append("file", file);
      setFileData(formData);
    }
  }, []);
  console.log(fileData);
  const { getRootProps, getInputProps } = useDropzone({
    onDrop,
    accept: {
      "image/*": [".jpeg", ".jpg", ".png", ".gif"],
    },
    maxFiles: 1,
  });

  return (
    <Container
      className={`maincpnt ${animate ? "animate" : ""}`}
      style={{ width: "40rem", marginRight: "280px", marginTop: "80px" }}
    >
      <Row>
        <Col>
          <div className="fileUpload ">
            {selectedImage ? (
              <img
                src={selectedImage}
                alt="Preview"
                style={{
                  width: "12rem",
                  height: "12rem",
                  borderRadius: "9999px",
                  border: "1px solid black",
                }}
              />
            ) : (
              <img
                src={"/testProfile.gif"}
                alt="defaulImg"
                style={{
                  width: "12rem",
                  height: "12rem",
                  borderRadius: "9999px",
                  border: "1px solid black",
                  backgroundColor: "gray",
                }}
              />
            )}
            <div className="inputBox" {...getRootProps()}>
              <input {...getInputProps()} />
              <p>여기에 이미지를 끌어다 놓거나 클릭하여 이미지를 선택하세요.</p>
            </div>
          </div>
        </Col>
        <Col>
          <form onSubmit={signUp_step1}>
            <Row>
              <div className="email">
                {checking && (
                  <div className="code">
                    <div className="form-floating mb-3 p-0 mx-auto">
                      <Input
                        id="code"
                        styles="input-line form-control bg-transparent text-black"
                        placeholder="Code"
                        onChange={codeChangeHnadler}
                        value={verificationCode}
                      />
                      <label className="fw-bold" htmlFor="email">
                        Code
                      </label>
                    </div>
                    <button
                      id="step2"
                      className="identify"
                      type="button"
                      onClick={emailCheckHandler}
                    >
                      확인하기
                    </button>
                  </div>
                )}
                <div className="form-floating mb-3 p-0 mx-auto">
                  <Input
                    id="email"
                    styles="input-line form-control bg-transparent text-black"
                    placeholder="Email"
                    onChange={onChange}
                    disabled={checkEmail}
                  />
                  <label className="fw-bold" htmlFor="email">
                    Email
                  </label>
                </div>
                {checkEmail ? (
                  <img className="identify" src="/check.png" alt="" />
                ) : (
                  <button
                    id="step1"
                    className="identify"
                    type="button"
                    onClick={emailCheckHandler}
                    disabled={checking}
                  >
                    메일인증
                  </button>
                )}
              </div>
              <div className="form-floating mb-3 p-0 mx-auto">
                <Input
                  id="password"
                  styles="input-line form-control bg-transparent text-black"
                  placeholder="Password"
                  onChange={onChange}
                  type="password"
                />
                <label className="fw-bold" htmlFor="password">
                  Password: 대/소/특수문자 포함 9자 이상
                </label>
              </div>
              <div className="form-floating mb-3 p-0 mx-auto">
                <Input
                  id="password2"
                  styles={`input-line form-control bg-transparent text-black`}
                  placeholder="Password Confirmation"
                  onChange={onChange}
                  type="password"
                />
                <label
                  className="fw-bold"
                  htmlFor="password2"
                  style={{ color: `${passwordValid ? "black" : "red"}` }}
                >
                  {passwordValid
                    ? "Password Confirmation"
                    : "비밀번호가 일치하지 않습니다."}
                </label>
              </div>
              <div className="form-floating mb-3 p-0 mx-auto">
                <Input
                  id="nickname"
                  styles="input-line form-control bg-transparent text-black"
                  placeholder="닉네임을 입력해주세요"
                  onChange={onChange}
                />
                <label className="fw-bold" htmlFor="password">
                  닉네임을 입력해주세요
                </label>
              </div>
              <div className="form-floating mb-3 p-0 mx-auto">
                <Input
                  id="birthDate"
                  styles="input-line form-control bg-transparent text-black"
                  placeholder="생년월일을 입력해주세요."
                  onChange={onChange}
                  type="date"
                />
                <label className="fw-bold" htmlFor="password">
                  생년월일을 입력해주세요
                </label>
              </div>
            </Row>
            <Row>
              <Col>
                <span>성별을 입력해주세요</span>
              </Col>
              <Col className="text-align-center">
                <input
                  id="male"
                  type="radio"
                  value="MALE"
                  checked={selectedGender === "MALE"}
                  onChange={onChange}
                />
                남자
              </Col>
              <Col>
                <input
                  id="female"
                  type="radio"
                  value="FEMALE"
                  checked={selectedGender === "FEMALE"}
                  onChange={onChange}
                />
                여자
              </Col>
            </Row>

            <Row className="justify-content-end">
              <Button
                styles="btn-primary"
                text="NEXT"
                btnType="submit"
                disabled={valid ? true : false}
              ></Button>
            </Row>
          </form>
        </Col>
      </Row>
      {valid ? (
        <SelectInterest
          step1={inputData}
          step2={fileData}
          verificationCode={verificationCode}
        />
      ) : null}
    </Container>
  );
};

export default SignUp;
