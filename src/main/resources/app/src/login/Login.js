import React from 'react';
import {Form, Input, Submit} from '../form/Form';
import {Utils} from '../utils/Utils';

export class Login extends React.Component {
  constructor(props) {
    super(props);
    this.onFormSubmit = this.onFormSubmit.bind(this);
    this.onLoginResponse = this.onLoginResponse.bind(this);
  }

  componentWillMount() {
    Utils.addEventListener('login-event', this.onLoginResponse);
  }

  componentWillUnmount() {
    // TODO many add the component will unmount
    //Utils.removeEventListener('login-event', this.onLoginResponse);
  }

  onFormSubmit(formData) {
    this.props.sendMessage('/api/auth/login', formData);
  }

  onLoginResponse(loginResponse) {
    if (loginResponse.value.loginResponseStatus === 'ERROR') {
      Utils.dispatchEvent('message', {type: 'error', text: loginResponse.value.message});
    } else {
      Utils.dispatchEvent('message', {type: 'info', text: loginResponse.value.message});
      Utils.dispatchEvent('set-state', {key: 'user', text: loginResponse.value.userDetails});
      this.props.router.history.push('/portal/');
    }
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