import './gameMenu.css'
import React from 'react'
import PropTypes from 'prop-types'
import {GamesMenu} from './GamesMenu'
import {UsersMenu} from './UsersMenu'

export class Menu extends React.Component {
  render() {
    return (
      <div id="menu">
        <div id="left-menu-icons">
          <div>G</div>
          <div>U</div>
        </div>
        <GamesMenu className={this.props.className} games={this.props.games} />
        <UsersMenu className={this.props.className} user={this.props.user} loggedInUsers={this.props.loggedInUsers} />
      </div>
    )
  }
}

Menu.propTyeps = {
  className: PropTypes.object.isRequired,
  games: PropTypes.array.isRequired,
  user: PropTypes.object.isRequired,
  loggedInUsers: PropTypes.array.isRequired
}
