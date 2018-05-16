import './login.css'
import React from 'react'
import {Form, Input, Submit} from '../form/Form'
import {Utils} from '../utils/Utils'
import PropTypes from 'prop-types'

export class Login extends React.Component {
  constructor(props) {
    super(props)
    this.onFormSubmit = this.onFormSubmit.bind(this)
    this.onLoginResponse = this.onLoginResponse.bind(this)
  }

  componentWillMount() {
    Utils.addEventListener('login-event', this.onLoginResponse)
  }

  componentWillUnmount() {
    Utils.removeEventListener('login-event', this.onLoginResponse)
  }

  onFormSubmit(formData) {
    this.props.sendMessage('/api/auth/login', formData)
  }

  onLoginResponse(loginResponse) {
    if (loginResponse.value.responseStatus === 'ERROR') {
      Utils.dispatchEvent('message', {type: 'error', text: loginResponse.value.message})
    } else {
      Utils.dispatchEvent('message', {type: 'info', text: loginResponse.value.message})
      Utils.dispatchEvent('set-state', {key: 'user', text: loginResponse.value.userDetails})
      this.props.router.history.push('/portal/')
    }
  }

  render() {
    if (this.props.user) {
      return (
        <div>
          <p>Welcome to Open Games.</p>
          <p>Choose a game from the menu and enjoy!</p>
        </div>
      )
    } else {
      return (
        <div id="login-form">
          <div>
            <p>Welcome to Open Games.</p>
            <p>Play online for free a selection of games.</p>
            <p>Choose your username for playing.</p>
          </div>
          <Form onFormSubmit={this.onFormSubmit}>
            <Input type={'text'} name={'Username'} mandatory autoFocus/>
            <Submit value={'Join'}/>
          </Form>
        </div>
      )
    }
  }
}

Login.propTyeps = {
  router: PropTypes.object.isRequired,
  sendMessage: PropTypes.func.isRequired,
  user: PropTypes.object.isRequired
}
