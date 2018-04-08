import React from 'react'
import {Utils} from '../utils/Utils'
import PropTypes from 'prop-types'

export class Game extends React.Component {
  constructor(props) {
    super(props)
  }

  componentWillMount() {
    Utils.checkAuthenticatedUser(this.props.user, this.props.router)
  }

  render() {
    const gameLabel = this.props.router.match.params.label
    const game = this.props.games.filter((game) => game.label===gameLabel)[0]
    if (game) {
      return (
        <div id="game-description">
          <div>
            <h2>{game.name}</h2>
            <p>{game.description}</p>
          </div>
        </div>
      )
    } else {
      return null;
    }
  }
}

Game.propTyeps = {
  router: PropTypes.object.isRequired,
  sendMessage: PropTypes.func.isRequired,
  user: PropTypes.object.isRequired,
  games: PropTypes.array.isRequired
}