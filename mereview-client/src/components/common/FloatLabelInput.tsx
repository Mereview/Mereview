interface InputProps {
  value?: string | number;
  onChange?: (event: React.ChangeEvent<HTMLInputElement>) => void;
  placeholder?: string;
  type?: string;
}

function FloatLabelInput({ value, onChange, placeholder, type }: InputProps) {
  return (
    <form className="text-center text-secondary">
      <div className="form-floating mb-3 mx-auto">
        <input
          type={type}
          className="form-control bg-transparent text-black"
          id="floatInput"
          onChange={onChange}
          placeholder={placeholder}
          value={value}
        />
        <label className="fw-bold" htmlFor="floatInput">
          {placeholder}
        </label>
      </div>
    </form>
  );
}

export default FloatLabelInput;
