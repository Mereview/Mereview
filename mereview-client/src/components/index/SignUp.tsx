import { Container, Row, Col } from "react-bootstrap";
import { Input, Button } from "../common/index";
import { useState, useEffect } from "react";
import ImageUploader from "../common/ImageUploader";
import SelectInterest from "./SelectInterest";
import { useDispatch, useSelector } from "react-redux";
import { userActions } from "../../store/user-slice";
import { InputDataInterface } from "../interface/UserInterface";
const SignUp = () => {
  const [animate, setAnimate] = useState(false);
  useEffect(() => {
    setAnimate(true);
  }, []);
  const dispatch = useDispatch();
  // 초기 상태로서 빈 문자열 또는 null로 초기화합니다.
  const [selectedGender, setSelectedGender] = useState<string>(""); // 선택된 성별을 상태로 관리합니다.
  const [inputData, setInputData] = useState<InputDataInterface>({
    email: null,
    password: null,
    password2: null,
    nickname: null,
    birth: null,
    gender: null,
  });
  const valid = useSelector((state: any) => state.user.thirdModal);

  //
  const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = event.target;
    if (id === "gender") {
      setSelectedGender(value); // 바로 이렇게 바꿔도 되나...?
    }
    setInputData((prevInputData) => ({
      ...prevInputData,
      [id]: value,
    }));
  };

  const signUp_step1 = (event: any) => {
    event.preventDefault();
    for (const key in inputData) {
      if (inputData[key] === null) {
        console.log(inputData);
        alert("모든 정보를 입력해주세요!");
        return;
      }
    }
    dispatch(userActions.modal_toggler());
    dispatch(userActions.signUp_step1(inputData));
    console.log(valid);
  };
  return (
    <Container
      className={`maincpnt ${animate ? "animate" : ""}`}
      style={{ width: "40rem" }}
    >
      <Row>
        <Col>
          <ImageUploader />
        </Col>
        <Col>
          <form onSubmit={signUp_step1}>
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
                  id="password"
                  styles="input-line form-control bg-transparent text-black"
                  placeholder="Password"
                  onChange={onChange}
                  type="password"
                />
                <label className="fw-bold" htmlFor="password">
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
                <label className="fw-bold" htmlFor="password2">
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
                <label className="fw-bold" htmlFor="password">
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
                disabled={valid ? true : false}
              ></Button>
            </Row>
          </form>
        </Col>
      </Row>
      {valid ? <SelectInterest /> : null}
    </Container>
  );
};

export default SignUp;
