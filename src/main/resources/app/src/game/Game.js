import React from 'react'
import {Utils} from '../utils/Utils'
import PropTypes from 'prop-types'

export class Game extends React.Component {
  constructor(props) {
    super(props)
  }

  componentWillMount() {
    Utils.checkAuthenticatedUser(this.props.user, this.props.router);
  }

  render() {
    return (
      <div id="game-description">
        <div>
          <p>{this.props.router.match.params.name}</p>
        </div>
      </div>
    )
  }
}

Game.propTyeps = {
  router: PropTypes.object.isRequired,
  sendMessage: PropTypes.func.isRequired,
  user: PropTypes.object.isRequired
}