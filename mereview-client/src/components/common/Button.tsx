import React from "react";
import "./Button.css";

interface ButtonProps {
  styles: string;
  onClick?: (event: React.MouseEvent<HTMLButtonElement>) => void;
  text: string;
}

function Button({ styles, onClick, text }: ButtonProps) {
  return (
    <button className={styles} onClick={onClick}>
      {text}
    </button>
  );
}

export default Button;
