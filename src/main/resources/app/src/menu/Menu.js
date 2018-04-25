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
    Utils.dispatchEvent("toggle-menu-event")
  }

  render() {
    if (Utils.isMobile()) {
      if (this.props.menuMobileExpanded) {
        return (
          <div id="mobile-menu">
            <ul>
              <li><NavLink className="header-link" to="/portal/games" onClick={this.closeMenu}>Games</NavLink></li>
              <li><NavLink className="header-link" to="/portal/users" onClick={this.closeMenu}>Users</NavLink></li>
              <li><a className="header-link" href={'/portal'}>Logout</a></li>
            </ul>
          </div>
        )
      } else {
        return null
      }

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
  menuMobileExpanded: PropTypes.array.isRequired
}
