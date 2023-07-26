import { Container, Row, Col } from "react-bootstrap";
import { Input, Button, FloatLabelInput } from "../common/index";
import { useState } from "react";
const SignUp = () => {
  const [selectedGender, setSelectedGender] = useState(""); // 선택된 성별을 상태로 관리합니다.

  const handleGenderChange = (event: any) => {
    setSelectedGender(event.target.value); // 선택된 성별을 업데이트합니다.
  };
  return (
    <Container>
      <Row>
        <Col>왼쪽 이미지 추가</Col>
        <Col>
          <form>
            <Row>
              <Input styles="input-line" placeholder="Email"></Input>
              <Input
                styles="input-line"
                placeholder="Password"
                type="password"
              ></Input>
              <Input
                styles="input-line"
                placeholder="Password Confirmation"
                type="password"
              ></Input>
              <Input
                styles="input-line"
                placeholder="닉네임을 입력해주세요"
              ></Input>
              <Input
                styles="input-line"
                placeholder="생년월일을 입력해주세요."
                type="date"
              ></Input>
            </Row>
            <Row>
              <div>
                <label>
                  <input
                    type="radio"
                    value="male"
                    checked={selectedGender === "male"}
                    onChange={handleGenderChange}
                  />
                  남자
                </label>
                <label>
                  <input
                    type="radio"
                    value="female"
                    checked={selectedGender === "female"}
                    onChange={handleGenderChange}
                  />
                  여자
                </label>
              </div>
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
    </Container>
  );
};

export default SignUp;
