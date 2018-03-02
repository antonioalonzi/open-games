import React from "react";

export class Login extends React.Component {
  render() {
    return (
      <div className="form-container">
        <div>Login</div>
        <form action="/api/login">
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