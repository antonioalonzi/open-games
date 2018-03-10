import React from 'react';
import {Form, Input, Submit} from '../form/Form';

export class Login extends React.Component {
  constructor(props) {
    super(props);
    this.onFormSubmit = this.onFormSubmit.bind(this);
  }

  onFormSubmit(formObject) {
    let errors = {};

    console.log(formObject);

    // if (Utils.isBlank(this.state.username)) {
    //   errors['username'] = 'Username is mandatory.';
    // }
    //
    // if (Utils.isBlank(this.state.password)) {
    //   errors['password'] = 'Password is mandatory.';
    // }
    //
    // this.setState({errors: errors});
    //
    // if (errors === {}) {
    //   const formData = {
    //     username: 'guest',
    //     password: 'password'
    //   };
    //
    //   this.props.sendMessage('/api/auth/login', formData);
    // }
  }

  render() {
    return (
      <div className="form-container" id="login-form">
        <h1>Login</h1>
        <Form onFormSubmit={this.onFormSubmit}>
          <Input type={'text'} name={'Username'}/>
          <Input type={'password'} name={'Password'}/>
          <Submit/>
        </Form>
      </div>
    );
  }
}