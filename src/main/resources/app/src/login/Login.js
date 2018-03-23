import React from 'react';
import {Form, Input, Submit} from '../form/Form';

export class Login extends React.Component {
  constructor(props) {
    super(props);
    this.onFormSubmit = this.onFormSubmit.bind(this);
  }

  onFormSubmit(formData) {
    this.props.sendMessage('/api/auth/login', formData);
  }

  render() {
    return (
      <div id="login-form">
        <h1>Login</h1>
        <div>Use guest / password</div>
        <Form onFormSubmit={this.onFormSubmit}>
          <Input type={'text'} name={'Username'} mandatory={true} value={'guest'}/>
          <Input type={'password'} name={'Password'} mandatory={true} value={'password'}/>
          <Submit value={'Login'}/>
        </Form>
      </div>
    );
  }
}