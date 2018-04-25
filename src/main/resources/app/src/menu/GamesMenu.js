import React from 'react'
import {NavLink} from 'react-router-dom'
import PropTypes from 'prop-types'
import {Utils} from '../utils/Utils'


export class GamesContent extends React.Component {
  render() {
    return (
      <div>
        Games ({this.props.games.length}):
        <ul>
          {
            this.props.games
              .map(game => (<li key={game.label}><NavLink to={`/portal/games/${game.label}`}>{game.name}</NavLink></li>))
          }
        </ul>
      </div>
    )
  }
}

GamesContent.propTypes = {
  games: PropTypes.array.isRequired,
  router: PropTypes.object,
}



export class GamesMenu extends React.Component {
  render() {
    return (
      <div id="games-menu">
        <div className={this.props.className}>
          <GamesContent games={this.props.games}/>
        </div>
      </div>
    )
  }
}

GamesMenu.propTyeps = {
  className: PropTypes.object.isRequired,
  games: PropTypes.array.isRequired
}



export class GamesPage extends React.Component {
  componentWillMount() {
    Utils.checkAuthenticatedUser(this.props.user, this.props.router)
  }

  render() {
    return (
      <GamesContent games={this.props.games}/>
    )
  }
}

GamesPage.propTyeps = {
  router: PropTypes.object.isRequired,
  user: PropTypes.object.isRequired,
  games: PropTypes.array.isRequired
}
