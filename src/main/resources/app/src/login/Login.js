import React from 'react';
import {Input, Submit} from '../form/Form';
import {Utils} from "../utils/Utils";

export class Login extends React.Component {
  constructor(props) {
    super(props);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleInputChange = this.handleInputChange.bind(this);
    this.state = {
      username: '',
      password: '',
      errors: {}
    }
  }

  handleInputChange(event) {
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    this.setState({
      [name]: value
    });
  }

  handleSubmit(event) {
    event.preventDefault();

    if (Utils.isBlank(this.state.username)) {
      alert('username is blank');
    }

    if (Utils.isBlank(this.state.password)) {
      alert('password is blank');
    }

    const formData = {
      username: 'guest',
      password: 'password'
    };

    this.props.sendMessage('/api/auth/login', formData);
  }

  render() {
    return (
      <div className="form-container" id="login-form">
        <h1>Login</h1>
        <form action="" onSubmit={this.handleSubmit}>
          <Input type={'text'} name={'Username'} value={this.state.username} onChange={this.handleInputChange}/>
          <Input type={'password'} name={'Password'} value={this.state.password} onChange={this.handleInputChange}/>
          <Submit/>
        </form>
      </div>
    );
  }
}