import React from 'react'
import {Utils} from '../utils/Utils'
import PropTypes from 'prop-types'
import {Form, Input, Submit} from '../form/Form'


export class GamePlay extends React.Component {
  constructor(props) {
    super(props)
  }

  componentWillMount() {
    Utils.checkAuthenticatedUser(this.props.user, this.props.router)
  }

  render() {
    return (
      <div id='game-play'>
        {this.props.game.name}
        {this.props.table.id}
      </div>
    )
  }
}

GamePlay.propTyeps = {
  router: PropTypes.object.isRequired,
  sendMessage: PropTypes.func.isRequired,
  user: PropTypes.object.isRequired,
  game: PropTypes.object.isRequired,
  table: PropTypes.object.isRequired
}