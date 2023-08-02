import { Button, FloatLabelInput } from "../common/index";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import { useState, useEffect } from "react";
import { useDispatch } from "react-redux";
import { userActions } from "../../store/user-slice";

const LoginForm = () => {
  const [animate, setAnimate] = useState(false);
  useEffect(() => {
    setAnimate(true);
  }, []);
  const dispatch = useDispatch();
  const [loginData, setLoginData] = useState({
    email: "",
    password: "",
  });
  const onChange = (event: any) => {
    const { id, value } = event.target;
    setLoginData((prevInputData) => ({
      ...prevInputData,
      [id]: value,
    }));
  };

  ///로그인 로직

  const Login = (event: any) => {
    event.preventDefault();
    const url = "http://localhost:80/api/auth/login";
    const userData = {
      email: loginData.email,
      password: loginData.password,
    };
    if (userData.email === "" || userData.password === "") {
      alert("아이디와 비밀번호를 확인해주세요!");
      return;
    }
    const requestOption: RequestInit = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(userData),
    };

    fetch(url, requestOption)
      .then((res) => {
        if (!res.ok) {
          throw new Error("Network response was not ok");
        }
        return res.json();
      })
      .then((data) => {
        console.log(data);
        dispatch(userActions.login(data)); // token받고, isAthenticated true로 바꾸기
      })
      .catch((err) => {
        console.log(err.data);
      });
  };

  return (
    <Container
      className={`maincpnt ${animate ? "animate" : ""}`}
      style={{ width: "40rem" }}
    >
      <Row>
        <form onSubmit={Login}>
          <Row>
            <FloatLabelInput
              id="email"
              placeholder="Email"
              onChange={onChange}
              value={loginData.email}
            />
            <FloatLabelInput
              id="password"
              placeholder="Password"
              onChange={onChange}
              value={loginData.password}
              type="password"
            />
          </Row>
          <Row className="justify-content-center">
            <a href="">비밀번호를 잊으셨나요?</a>
          </Row>

          <Row className="justify-content-end">
            <Button styles="btn-primary" text="LOGIN" btnType="submit"></Button>
          </Row>
        </form>
      </Row>
    </Container>
  );
};

export default LoginForm;
