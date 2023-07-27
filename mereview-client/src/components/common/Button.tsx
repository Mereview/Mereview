import React from "react";
import "../../styles/css/Button.css";
interface ButtonProps {
  styles?: string;
  onClick?: (event: React.MouseEvent<HTMLButtonElement>) => void;
  btnType?: "button" | "submit" | "reset";
  text?: string;
}

function Button({ styles, onClick, text, btnType }: ButtonProps) {
  return (
    <button className={styles} onClick={onClick} type={btnType}>
      {text}
    </button>
  );
}

export default Button;
