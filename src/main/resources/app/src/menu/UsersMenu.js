import React from 'react';
import {Utils} from '../utils/Utils';
import PropTypes from 'prop-types';

export class UsersMenu extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      loggedInUsers: []
    };
    this.onUserLoggedIn = this.onUserLoggedIn.bind(this);
  }

  componentWillMount() {
    Utils.addEventListener('user-logged-in', this.onUserLoggedIn);
  }

  componentWillUnmount() {
    Utils.removeEventListener('user-logged-in', this.onUserLoggedIn);
  }

  onUserLoggedIn(event) {
    console.log(event);
    const loggedInUsers = this.state.loggedInUsers;
    loggedInUsers.push({
      'username': event.value.username
    });
    this.setState({
      loggedInUsers: loggedInUsers
    });
  }

  render() {
    return (
      <div id="users-menu" className={this.props.className}>
        Other logged in users ({this.state.loggedInUsers.length}):
        <ul>
          {this.state.loggedInUsers.map(user => (
            <li key={user.username}>{user.username}</li>
          ))}
        </ul>
      </div>
    );
  }
}

UsersMenu.propTyeps = {
  className: PropTypes.object
};
