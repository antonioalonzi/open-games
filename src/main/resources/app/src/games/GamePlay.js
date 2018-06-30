import React from 'react'
import {Prompt} from 'react-router-dom'
import {Utils} from '../utils/Utils'
import PropTypes from 'prop-types'
import {TicTacToe} from './tictactoe/TicTacToe'


export class GamePlay extends React.Component {
  constructor(props) {
    super(props)
    this.exit = this.exit.bind(this)
    this.maximize = this.maximize.bind(this)
    this.state = {
      displayPrompt: false
    }
  }

  componentWillMount() {
    Utils.checkAuthenticatedUser(this.props.user, this.props.router)
  }

  exit() {
    this.setState({displayPrompt: true})
    setTimeout(() => this.props.router.history.push('/portal'), 250);
  }

  maximize() {
    alert('maximize')
  }

  isTableInProgress() {
    return this.props.table && this.props.table.status==='IN_PROGRESS'
  }

  render() {
    return (
      <div id='game-play'>
        { this.state.displayPrompt ? <Prompt when={this.isTableInProgress()} message="Are you sure you want to leave the game?" /> : null }
        <div id='game-play-content'>
          <div id='game-play-icons'>
            { Utils.isDesktop() ? (<i id='game-play-maximize' className="fa fa-window-maximize" onClick={this.maximize} />) : null }
            <i id='game-play-exit' className="fa fa-window-close" onClick={this.exit} />
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