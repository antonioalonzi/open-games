import React from 'react'
import {NavLink} from 'react-router-dom'
import PropTypes from 'prop-types'

export class GamesMenu extends React.Component {
  render() {
    return (
      <div id="games-menu" className={this.props.className}>
        Games ({this.props.games.length}):
        <ul>
          {
            this.props.games
              .map(game => (<li key={game.label}><NavLink to={`/portal/game/${game.label}`}>{game.name}</NavLink></li>))
          }
        </ul>
      </div>
    )
  }
}

GamesMenu.propTyeps = {
  className: PropTypes.object.isRequired,
  games: PropTypes.array.isRequired
}
