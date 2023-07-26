import { NavLink } from "react-router-dom";
const NavigationBar = () => {
  return (
    <div>
      <div>
        <NavLink to="/">Main</NavLink>
      </div>
      <div>
        <NavLink to="/test">test</NavLink>
      </div>
      <div>
        <NavLink to="/review">review</NavLink>
      </div>
      <div>
        <NavLink to="/review/write">review write</NavLink>
      </div>
      <div>
        <NavLink to="/review/detail">review detail</NavLink>
      </div>
      <div>
        <NavLink to="/profile">profile</NavLink>
      </div>
    </div>
  );
};

export default NavigationBar;
