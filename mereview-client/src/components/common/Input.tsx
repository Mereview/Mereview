import React from "react";
import "./Input.css";

interface InputProps {
  styles: string;
  value: string;
  onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  placeholder?: string;
  type?: string;
}

function Input({ styles, value, onChange, placeholder, type }: InputProps) {
  return (
    <input
      className={styles}
      type={type}
      value={value}
      onChange={onChange}
      placeholder={placeholder}
    />
  );
}

export default Input;
