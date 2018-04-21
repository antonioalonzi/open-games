import './header.css'
import React from 'react'
import {NavLink} from 'react-router-dom'
import PropTypes from 'prop-types'
import {Utils} from '../utils/Utils'

export class HeaderLeft extends React.Component {
  constructor(props) {
    super(props)
    this.toggleMenu = this.toggleMenu.bind(this)
  }

  toggleMenu() {
    Utils.dispatchEvent("toggle-menu-event")
  }

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
        { this.isMenuIconToBeDisplayed() ? <a href="#" onClick={this.toggleMenu}>Menu</a> : null }
        <NavLink to="/portal/">Open Games</NavLink>
      </div>
    )
  }
}


export class HeaderRight extends React.Component {
  render() {
    if (this.props.user) {
      return (
        <div id="header-right">
          <span>Welcome {this.props.user.username}</span>
          <a href={'/portal'}>Logout</a>
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
