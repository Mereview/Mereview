import React from "react";
import "../../styles/css/Button.css";
interface ButtonProps {
  styles?: string;
  onClick?: (event: React.MouseEvent<HTMLButtonElement>) => void;
  btnType?: "button" | "submit" | "reset";
  text?: string;
  disabled?: boolean;
}

function Button({ styles, onClick, text, btnType, disabled }: ButtonProps) {
  return (
    <button
      className={`${styles} ${disabled ? "disabled" : ""}`}
      onClick={onClick}
      type={btnType}
      disabled={disabled}
    >
      {text}
    </button>
  );
}

export default Button;
