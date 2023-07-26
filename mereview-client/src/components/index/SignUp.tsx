import { Container, Row, Col } from "react-bootstrap";
import { Input, Button, FloatLabelInput } from "../common/index";
import { useState } from "react";
import SelectProfileImage from "./SelectProfileImage";
interface InputDataTypes {
  [key: string]: null | string;
  email: null | string;
  password1: null | string;
  password2: null | string;
  nickname: null | string;
  birth: null | string;
  gender: null | string;
}

const SignUp = () => {
  const [selectedGender, setSelectedGender] = useState(""); // 선택된 성별을 상태로 관리합니다.
  const [inputData, setInputData] = useState<InputDataTypes>({
    email: null,
    password1: null,
    password2: null,
    nickname: null,
    birth: null,
    gender: null,
  });
  //
  const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = event.target;
    if (id === "gender") {
      setSelectedGender(event.target.value);
    }
    setInputData((prevInputData) => ({
      ...prevInputData,
      [id]: value,
    }));
  };
  const submitData = (event: any) => {
    event.preventDefault();
    for (const key in inputData) {
      if (inputData[key] === null) {
        alert("모든 정보를 입력해주세요!");
        return;
      }
    }
    // 보내는함수 작성
  };
  return (
    <Container>
      <Row>
        <Col>
          <SelectProfileImage />
        </Col>
        <Col>
          <Row>
            <Col>
              <form onSubmit={submitData}>
                <Row>
                  <div className="form-floating mb-3 p-0 mx-auto">
                    <Input
                      id="email"
                      styles="input-line form-control bg-transparent text-black"
                      placeholder="Email"
                      onChange={onChange}
                    />
                    <label className="fw-bold" htmlFor="email">
                      Email
                    </label>
                  </div>

                  <div className="form-floating mb-3 p-0 mx-auto">
                    <Input
                      id="password1"
                      styles="input-line form-control bg-transparent text-black"
                      placeholder="Password"
                      onChange={onChange}
                      type="password"
                    />
                    <label className="fw-bold" htmlFor="password1">
                      Password
                    </label>
                  </div>
                  <div className="form-floating mb-3 p-0 mx-auto">
                    <Input
                      id="password2"
                      styles="input-line form-control bg-transparent text-black"
                      placeholder="Password Confirmation"
                      onChange={onChange}
                      type="password"
                    />
                    <label className="fw-bold" htmlFor="password1">
                      Password Confirmation
                    </label>
                  </div>
                  <div className="form-floating mb-3 p-0 mx-auto">
                    <Input
                      id="nickname"
                      styles="input-line form-control bg-transparent text-black"
                      placeholder="닉네임을 입력해주세요"
                      onChange={onChange}
                    />
                    <label className="fw-bold" htmlFor="password1">
                      닉네임을 입력해주세요
                    </label>
                  </div>
                  <div className="form-floating mb-3 p-0 mx-auto">
                    <Input
                      id="birth"
                      styles="input-line form-control bg-transparent text-black"
                      placeholder="생년월일을 입력해주세요."
                      onChange={onChange}
                      type="date"
                    />{" "}
                    <label className="fw-bold" htmlFor="password1">
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
                      id="gender"
                      type="radio"
                      value="male"
                      checked={selectedGender === "male"}
                      onChange={onChange}
                    />
                    남자
                  </Col>
                  <Col>
                    <input
                      id="gender"
                      type="radio"
                      value="female"
                      checked={selectedGender === "female"}
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
                  ></Button>
                </Row>
              </form>
            </Col>
          </Row>
        </Col>
      </Row>
    </Container>
  );
};

export default SignUp;
