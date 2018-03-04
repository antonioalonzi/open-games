import React from "react";

export class Login extends React.Component {
  constructor(props) {
    super(props);
    this.handlerSubmit = this.handlerSubmit.bind(this);
  }

  handlerSubmit(event) {
    event.preventDefault();
    const formData = {
      username: "guest",
      password: "password"
    };

    this.props.sendMessage("/api/auth/login", formData);
  }

  render() {
    return (
      <div className="form-container" id="login-form">
        <h1>Login</h1>
        <form action="" onSubmit={this.handlerSubmit}>
          <div className="form-row">
            <div className="form-col-25">
              <label htmlFor="username">Username</label>
            </div>
            <div className="form-col-75">
              <input type="text" id="username" name="username" placeholder="Your username..."/>
            </div>
          </div>
          <div className="form-row">
            <div className="form-col-25">
              <label htmlFor="password">Password</label>
            </div>
            <div className="form-col-75">
              <input type="password" id="password" name="password" placeholder="Your password..."/>
            </div>
          </div>
          <div className="form-row">
            <input type="submit" value="Submit"/>
          </div>
        </form>
      </div>
    );
  }
}