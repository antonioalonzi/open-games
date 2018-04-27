import React from 'react'
import PropTypes from 'prop-types'
import {Utils} from '../utils/Utils'


export class UsersContent extends React.Component {
  isCurrentUser(user) {
    if (!this.props.user) {
      return false
    } else {
      return user.username === this.props.user.username
    }
  }

  render() {
    return (
      <div>
        Other logged in users ({this.props.loggedInUsers.length - 1}):
        <ul>
          {
            this.props.loggedInUsers
              .filter(user => !this.isCurrentUser(user))
              .map(user => (<li key={user.username}>{user.username}</li>))
          }
        </ul>
      </div>
    )
  }
}

UsersContent.propTyeps = {
  user: PropTypes.object.isRequired,
  loggedInUsers: PropTypes.array.isRequired
}



export class UsersMenu extends React.Component {
  render() {
    return (
      <div id="users-menu">
        <div className={this.props.className}>
          <UsersContent user={this.props.user} loggedInUsers={this.props.loggedInUsers}/>
        </div>
      </div>
    )
  }
}

UsersMenu.propTyeps = {
  className: PropTypes.object.isRequired,
  user: PropTypes.object.isRequired,
  loggedInUsers: PropTypes.array.isRequired
}



export class UsersPage extends React.Component {
  componentWillMount() {
    Utils.checkAuthenticatedUser(this.props.user, this.props.router)
  }

  render() {
    return (
      <UsersContent user={this.props.user} loggedInUsers={this.props.loggedInUsers}/>
    )
  }
}

UsersMenu.propTyeps = {
  router: PropTypes.object.isRequired,
  user: PropTypes.object.isRequired,
  loggedInUsers: PropTypes.array.isRequired
}
