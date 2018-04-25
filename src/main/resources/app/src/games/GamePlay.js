import React from 'react'
import {Utils} from '../utils/Utils'
import PropTypes from 'prop-types'
import {TicTacToe} from './tictactoe/TicTacToe'
import {Button} from '../form/Form'


export class GamePlay extends React.Component {
  constructor(props) {
    super(props)
    this.exit = this.exit.bind(this)
    this.maximize = this.maximize.bind(this)
  }

  componentWillMount() {
    Utils.checkAuthenticatedUser(this.props.user, this.props.router)
  }

  exit() {
    alert('exit')
  }

  maximize() {
    alert('maximize')
  }

  render() {
    return (
      <div id='game-play'>
        <div id='game-play-content'>
          <div id='game-play-exit'>
            <Button value='x' onClick={this.exit} />
          </div>

          <TicTacToe router={this.props.router} sendMessage={this.props.sendMessage} user={this.props.user}
                     game={this.props.game} table={this.props.table} />
        </div>
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