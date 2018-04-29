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
        <i id="open-menu-icon" className="fa fa-bars" />
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
          <span className="header-info">{this.props.user.username}</span>
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
