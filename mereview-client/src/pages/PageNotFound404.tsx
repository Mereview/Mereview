import { useNavigate } from "react-router-dom";

const PageNotFound404 = () => {
  const navigate = useNavigate();
  const goBack = () => {
    navigate("/review");
  };
  return (
    <div>
      <h1 style={{ color: "red" }}>Page Not Found 404</h1>
      <h3>해당 페이지를를 찾을 수 없습니다!</h3>
      <button onClick={goBack}>뒤로가기</button>
    </div>
  );
};

export default PageNotFound404;
