import './menu.css'
import React from 'react'
import PropTypes from 'prop-types'
import {NavLink} from 'react-router-dom'
import {GamesMenu} from './GamesMenu'
import {UsersMenu} from './UsersMenu'
import {Utils} from '../utils/Utils'

export class Menu extends React.Component {
  constructor(props) {
    super(props)
    this.closeMenu = this.closeMenu.bind(this)
  }

  closeMenu() {
    Utils.dispatchEvent('toggle-menu-event')
  }

  menuLeftPosition() {
    if (this.props.menuMobileExpanded) {
      return '0'
    } else {
      return '-50%'
    }
  }

  render() {
    if (Utils.isMobile()) {
      return (
        <div id="mobile-menu" style={ {left: this.menuLeftPosition()} } >
          <i className="fa fa-gamepad" /><NavLink className="header-link" to="/portal/games" onClick={this.closeMenu}>Games</NavLink>
          <i className="fa fa-users" /><NavLink className="header-link" to="/portal/users" onClick={this.closeMenu}>Users</NavLink>
          <i className="fa fa-power-off" /><a className="header-link" href={'/portal'}>Logout</a>
        </div>
      )

    } else if (Utils.isDesktop()) {
      return (
        <div id="desktop-menu">
          <GamesMenu className={this.props.className} games={this.props.games}/>
          <UsersMenu className={this.props.className} user={this.props.user} loggedInUsers={this.props.loggedInUsers}/>
        </div>
      )
    }
  }
}

Menu.propTyeps = {
  className: PropTypes.object.isRequired,
  games: PropTypes.array.isRequired,
  user: PropTypes.object.isRequired,
  loggedInUsers: PropTypes.array.isRequired,
  menuMobileExpanded: PropTypes.bool.isRequired
}
