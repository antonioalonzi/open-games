import React from 'react';
import {Form, Input, Submit} from '../form/Form';
import {Utils} from "../utils/Utils";

export class Login extends React.Component {
  constructor(props) {
    super(props);
    this.onFormSubmit = this.onFormSubmit.bind(this);
  }

  componentDidMount() {
    Utils.addEventListener('login-event', this.onLoginEvent);
  }

  componentWillUnmount() {
    Utils.removeEventListener('login-event', this.onLoginEvent);
  }

  onFormSubmit(formData) {
    this.props.sendMessage('/api/auth/login', formData);
  }

  onLoginEvent(event) {
    if (event.value.loginResponseStatus === 'ERROR') {
      Utils.dispatchEvent('message', {type: 'error', text: event.value.message});
    } else {
      console.log('SUCCESS', event);
    }
  }

  render() {
    return (
      <div id="login-form">
        <h1>Login</h1>
        <Form onFormSubmit={this.onFormSubmit}>
          <Input type={'text'} name={'Username'} mandatory={true}/>
          <Input type={'password'} name={'Password'} mandatory={true}/>
          <Submit value={'Login'}/>
        </Form>
      </div>
    );
  }
}