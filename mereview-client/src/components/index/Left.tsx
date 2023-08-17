import { Row, Col } from "react-bootstrap";
import "../../styles/css/Left.css";
import { useSelector, useDispatch } from "react-redux";
import { uiActions } from "../../store/ui-silce";

const Left = () => {
  const style = { padding: "0px", margin: "0px" };
  const leftStyle = {
    padding: "0px",
    margin: "0px",
    backgroundImage: "url(/IndexPictures/old.png)",
    backgroundSize: "cover",
  };
  const dispatch = useDispatch();
  const tab = useSelector(
    (state: { ui: { tabToggle: string } }) => state.ui.tabToggle
  );
  const tabChange = (event: React.MouseEvent<HTMLButtonElement>) => {
    const id = event.currentTarget.id;
    dispatch(uiActions.tabChange(id));
  };
  return (
    <Row className="ctn" style={leftStyle}>
      <Col style={style}>
        <div></div>
      </Col>
      <Col md={2} style={style}>
        <Row className="blank" style={style}></Row>
        <Row style={style}>
          <button
            id="login"
            style={style}
            className={`btn login ${tab === "login" ? "selected" : ""}`}
            onClick={tabChange}
          >
            LOGIN
          </button>
        </Row>
        <Row style={style}>
          <button
            id="signup"
            style={style}
            className={`btn signup ${tab === "signup" ? "selected" : ""}`}
            onClick={tabChange}
          >
            SIGN UP
          </button>
        </Row>
      </Col>
    </Row>
  );
};

export default Left;
