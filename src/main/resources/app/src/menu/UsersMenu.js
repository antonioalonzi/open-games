import React from 'react'
import PropTypes from 'prop-types'

export class UsersMenu extends React.Component {
  isCurrentUser(user) {
    if (!this.props.user) {
      return false
    } else {
      return user.username === this.props.user.username
    }
  }

  render() {
    return (
      <div id="users-menu">
        <div className={this.props.className}>
          Other logged in users ({this.props.loggedInUsers.length - 1}):
          <ul>
            {
              this.props.loggedInUsers
                .filter(user => !this.isCurrentUser(user))
                .map(user => (<li key={user.username}>{user.username}</li>))
            }
          </ul>
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
