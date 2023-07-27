import React from "react";
import "../../styles/css/Input.css";

interface InputProps {
  id?: string;
  styles?: string;
  value?: string | number;
  onChange?: (event: React.ChangeEvent<HTMLInputElement>) => void;
  placeholder?: string;
  type?: string;
}

function Input({ id, styles, value, onChange, placeholder, type }: InputProps) {
  return (
    <input
      id={id}
      className={styles}
      type={type}
      placeholder={placeholder}
      value={value}
      onChange={onChange}
    />
  );
}

export default Input;
