import React from "react";
import "../../styles/css/Input.css";

interface InputProps {
  styles?: string;
  value?: string | number;
  onChange?: (event: React.ChangeEvent<HTMLInputElement>) => void;
  placeholder?: string;
  type?: string;
}

function Input({ styles, value, onChange, placeholder, type }: InputProps) {
  return (
    <input
      className={styles}
      type={type}
      placeholder={placeholder}
      value={value}
      onChange={onChange}
    />
  );
}

export default Input;
