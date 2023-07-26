import { Navbar, Nav } from "react-bootstrap";

const NavigationBar = () => {
  return (
    <div>
      <Navbar bg="dark" expand="lg">
        <Navbar.Brand href="/" className="ms-4">
          <img src={"/logo1.png"} height="60"></img>
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="ms-auto me-4">
            <Nav.Link href="/review" className="text-white fs-4">
              review
            </Nav.Link>
            <Nav.Link href="/profile" className="text-white fs-4">
              profile
            </Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    </div>
  );
};

export default NavigationBar;
