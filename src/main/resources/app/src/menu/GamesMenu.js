import React from 'react'
import {NavLink} from 'react-router-dom'
import PropTypes from 'prop-types'

export class GamesMenu extends React.Component {
  render() {
    return (
      <div id="games-menu" className={this.props.className}>
        <ul>
          <li>
            <NavLink to="/portal/game/tictactoe">TicTacToe</NavLink>
          </li>
        </ul>
      </div>
    )
  }
}

GamesMenu.propTyeps = {
  className: PropTypes.object
}
