import { Button, FloatLabelInput } from "../common/index";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import { useState, useEffect, ChangeEvent } from "react";
import { useDispatch } from "react-redux";
import { userActions } from "../../store/user-slice";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const LoginForm = () => {
  const [animate, setAnimate] = useState<boolean>(false);
  useEffect(() => {
    setAnimate(true);
  }, []);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [loginData, setLoginData] = useState({
    email: "",
    password: "",
  });
  const onChange = (event: ChangeEvent<HTMLInputElement>) => {
    const { id, value } = event.target;
    setLoginData((prevInputData) => ({
      ...prevInputData,
      [id]: value,
    }));
  };

  ///로그인 로직

  const loginHandler = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (loginData.email === "" || loginData.password === "") {
      alert("아이디와 비밀번호를 확인해주세요!");
      return;
    }
    const loginURL = "http://localhost:8080/api/members/login";
    axios
      .post(loginURL, loginData, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        localStorage.setItem("id", res.data.data.id);
        localStorage.setItem("token", res.data.data.accessToken);
        dispatch(userActions.authToggler());
        navigate("/review");
      })
      .catch((err) => console.log(err));
  };

  return (
    <Container
      className={`maincpnt ${animate ? "animate" : ""}`}
      style={{ width: "40rem", marginTop: "150px" }}
    >
      <Row>
        <form onSubmit={loginHandler}>
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
            <Button styles="btn-primary" text="LOGIN" btnType="submit" />
          </Row>
        </form>
      </Row>
    </Container>
  );
};

export default LoginForm;
