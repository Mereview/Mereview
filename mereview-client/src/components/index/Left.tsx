import { Row, Col } from "react-bootstrap";
import "../../styles/css/Left.css";
import { useSelector, useDispatch } from "react-redux";
import { uiActions } from "../../store/ui-silce";

const Left = () => {
  const dispatch = useDispatch();
  const tab = useSelector((state: any) => state.ui.tabToggle);
  const tabChange = (event) => {
    const data = event.target.class;
    if (tab === "login") {
    }
    dispatch(uiActions.tabChange(tab));
  };
  return (
    <Row className="ctn">
      <Col>
        <div></div>
      </Col>
      <Col md={2}>
        <Row className="blank"></Row>
        <Row>
          <button
            className={`btn login ${tab === "login" ? "selected" : ""}`}
            onClick={tabChange}
          >
            LOGIN
          </button>
        </Row>
        <Row>
          <button
            className={`btn signup ${tab === "signup" ? "selected" : ""}`}
            onClick={tabChange}
          >
            SING UP
          </button>
        </Row>
      </Col>
    </Row>
  );
};

export default Left;
