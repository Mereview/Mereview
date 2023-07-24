import React from "react";
import "./Button.css";

interface ButtonProps {
  styles: string;
  onClick: (event: React.MouseEvent<HTMLButtonElement>) => void;
  text: string;
  value?: string | number;
}

function Button({ styles, onClick, text, value }: ButtonProps) {
  return (
    <button className={styles} onClick={onClick} value={value}>
      {text}
    </button>
  );
}

export default Button;
