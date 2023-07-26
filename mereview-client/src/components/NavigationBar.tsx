import { Navbar, Nav } from "react-bootstrap";

const NavigationBar = () => {
  return (
    <div>
      <Navbar bg="light" expand="lg" className="ms-3">
        <Navbar.Brand href="/">
          <img src="logo192.png" height="70"></img>
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="ms-auto">
            <Nav.Link href="/review">review</Nav.Link>
            <Nav.Link href="/review/write">review write</Nav.Link>
            <Nav.Link href="/review/detail">review detail</Nav.Link>
            <Nav.Link href="/profile">profile</Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Navbar>
      <div className="container mt-3">{/* Your content goes here */}</div>
    </div>
  );
};

export default NavigationBar;
