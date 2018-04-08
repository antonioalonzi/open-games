import React from 'react'
import {Utils} from '../utils/Utils'
import PropTypes from 'prop-types'

export class UsersMenu extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      loggedInUsers: []
    }
    this.onUserLoggedIn = this.onUserLoggedIn.bind(this)
  }

  componentWillMount() {
    Utils.addEventListener('user-logged-in', this.onUserLoggedIn)
  }

  componentWillUnmount() {
    Utils.removeEventListener('user-logged-in', this.onUserLoggedIn)
  }

  onUserLoggedIn(event) {
    const user = {username: event.value.username}
    this.setState(prevState => ({
      loggedInUsers: [...prevState.loggedInUsers, user]
    }))
  }

  isCurrentUser(user) {
    if (!this.props.user) {
      return false
    } else {
      return user.username === this.props.user.username
    }
  }

  render() {
    return (
      <div id="users-menu" className={this.props.className}>
        Other logged in users ({this.state.loggedInUsers.length - 1}):
        <ul>
          {
            this.state.loggedInUsers
              .filter(user => !this.isCurrentUser(user))
              .map(user => (<li key={user.username}>{user.username}</li>))
          }
        </ul>
      </div>
    )
  }
}

UsersMenu.propTyeps = {
  className: PropTypes.object.isRequired,
  user: PropTypes.object.isRequired
}
