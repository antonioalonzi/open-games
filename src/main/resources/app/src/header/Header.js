import React from 'react';
import {NavLink} from 'react-router-dom';
import PropTypes from 'prop-types';

export class HeaderLeft extends React.Component {
  render() {
    return (
      <div id="header-left">
        <NavLink to="/portal/">Open Games</NavLink>
      </div>
    );
  }
}


export class HeaderRight extends React.Component {
  render() {
    if (this.props.user) {
      return (
        <div id="header-right">
          <span>Welcome {this.props.user.username}</span>
          <NavLink to="/portal/logout">Logout</NavLink>
        </div>
      );
    } else {
      return (
        <div id="header-right">
          <NavLink to="/portal/login">Login</NavLink>
        </div>
      );
    }
  }
}

HeaderRight.propTyeps = {
  user: PropTypes.object.isRequired
};


export class Header extends React.Component {
  render() {
    return (
      <div id="header">
        <HeaderLeft/>
        <HeaderRight user={this.props.user}/>
      </div>
    );
  }
}

Header.propTyeps = {
  user: PropTypes.object.isRequired
};
