import './header.css'
import React from 'react'
import {NavLink} from 'react-router-dom'
import PropTypes from 'prop-types'
import {Utils} from '../utils/Utils'

export class OpenMenuIcon extends React.Component {
  constructor(props) {
    super(props)
    this.toggleMenu = this.toggleMenu.bind(this)
  }

  toggleMenu() {
    Utils.dispatchEvent("toggle-menu-event")
  }

  render() {
    return (
      <a href="#" id="open-menu-icon-link" onClick={this.toggleMenu}>
        <svg height="28px" id="open-menu-icon" version="1.1" viewBox="0 0 32 32" width="28px">
          <path d="M4,10h24c1.104,0,2-0.896,2-2s-0.896-2-2-2H4C2.896,6,2,6.896,2,8S2.896,10,4,10z M28,14H4c-1.104,0-2,0.896-2,2  s0.896,2,2,2h24c1.104,0,2-0.896,2-2S29.104,14,28,14z M28,22H4c-1.104,0-2,0.896-2,2s0.896,2,2,2h24c1.104,0,2-0.896,2-2  S29.104,22,28,22z"/>
        </svg>
      </a>
    )
  }
}



export class HeaderLeft extends React.Component {
  isMenuIconToBeDisplayed() {
    if (this.props.user && Utils.isMobile()) {
      return true
    } else {
      return false
    }
  }

  render() {
    return (
      <div id="header-left">
        { this.isMenuIconToBeDisplayed() ? <OpenMenuIcon /> : null }
        <NavLink className="header-link" to="/portal/">Open Games</NavLink>
      </div>
    )
  }
}

HeaderLeft.propTyeps = {
  user: PropTypes.object.isRequired
}



export class HeaderRight extends React.Component {
  render() {
    if (this.props.user) {
      Utils.isMobile()
      return (
        <div id="header-right">
          <span className="header-info">Welcome {this.props.user.username}</span>
          { Utils.isDesktop() ? (<a className="header-link" href={'/portal'}>Logout</a>) : null }
        </div>
      )
    } else {
      return (
        <div id="header-right" />
      )
    }
  }
}

HeaderRight.propTyeps = {
  user: PropTypes.object.isRequired
}



export class Header extends React.Component {
  render() {
    return (
      <div id="header">
        <HeaderLeft user={this.props.user}/>
        <HeaderRight user={this.props.user}/>
      </div>
    )
  }
}

Header.propTyeps = {
  user: PropTypes.object.isRequired
}
