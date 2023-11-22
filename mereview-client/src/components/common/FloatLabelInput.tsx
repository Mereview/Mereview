interface InputProps {
  id: string;
  value?: string | number;
  onChange?: (event: React.ChangeEvent<HTMLInputElement>) => void;
  placeholder?: string;
  type?: string;
}

function FloatLabelInput({
  id,
  value,
  onChange,
  placeholder,
  type,
}: InputProps) {
  return (
    <div className="form-floating mb-3 p-0 mx-auto">
      <input
        type={type}
        className="form-control bg-transparent text-black"
        id={id}
        onChange={onChange}
        placeholder={placeholder}
        value={value}
      />
      <label className="fw-bold" htmlFor={id}>
        {placeholder}
      </label>
    </div>
  );
}

export default FloatLabelInput;
