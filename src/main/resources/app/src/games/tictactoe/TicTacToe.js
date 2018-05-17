import './tictactoe.css'
import React from 'react'
import PropTypes from 'prop-types'
import {Utils} from "../../utils/Utils";

class TicTacToeIcon extends React.Component {
  render () {
    switch (this.props.symbol) {
      case 'X':
        return <i className={`fa fa-times ${this.props.className}`} />
      case 'O':
        return <i className={`fa fa-genderless ${this.props.className}`} />
      default:
        return null
    }
  }
}

TicTacToeIcon.propTyeps = {
  symbol: PropTypes.string.isRequired,
  className: PropTypes.string
}



class TicTacToePlayerStatus extends React.Component {
  playerStatus(i) {
    return (
      <span><TicTacToeIcon symbol={this.props.playersInfo[i].symbol} /> {this.props.playersInfo[i].username}</span>
    )
  }

  render () {
    if (this.props.playersInfo.length > 0) {
      return (
        <div id='tic-tac-toe-status-players'>
          <div id='tic-tac-toe-status-players-current'>
            <span>{ this.playerStatus(0) }</span>
            <span>{ this.props.currentPlayerIndex === 0 ? <i className={'fa fa-hand-o-left'} /> : null }</span>
            <span>vs</span>
            <span>{ this.props.currentPlayerIndex === 1 ? <i className={'fa fa-hand-o-right'} /> : null }</span>
            <span>{ this.playerStatus(1) }</span>
          </div>
          { this.props.finished ? <div id='tic-tac-toe-status-players-winner'>{this.props.finished.winningPlayer} win!</div> : null }
        </div>
      )
    } else {
      return null
    }
  }
}

TicTacToeIcon.propTyeps = {
  playersInfo: PropTypes.array.isRequired,
  currentPlayerIndex: PropTypes.number.isRequired,
  finished: PropTypes.object.isRequired
}



export class TicTacToe extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      gamePlayId: '',
      board: [
        ['', '', ''],
        ['', '', ''],
        ['', '', '']
      ],
      playersInfo: [],
      currentPlayerIndex: 0,
      finished: false
    }
    this.onInitializationEvent = this.onInitializationEvent.bind(this)
    this.onUpdateEvent = this.onUpdateEvent.bind(this)
    this.onFinishEvent = this.onFinishEvent.bind(this)
  }

  componentWillMount() {
    Utils.addEventListener('tic-tac-toe-init-event', this.onInitializationEvent)
    Utils.addEventListener('tic-tac-toe-update-event', this.onUpdateEvent)
    Utils.addEventListener('tic-tac-toe-finish-event', this.onFinishEvent)
  }

  componentWillUnmount() {
    Utils.removeEventListener('tic-tac-toe-init-event', this.onInitializationEvent)
    Utils.removeEventListener('tic-tac-toe-update-event', this.onUpdateEvent)
    Utils.removeEventListener('tic-tac-toe-finish-event', this.onFinishEvent)
  }

  onInitializationEvent(event) {
    this.setState({
      gamePlayId: event.value.id,
      playersInfo: event.value.playersInfo,
      currentPlayerIndex: event.value.currentPlayerIndex
    })
  }

  onUpdateEvent(event) {
    this.setState({
      currentPlayerIndex: event.value.gameState.currentPlayerIndex,
      board: event.value.gameState.board
    })
  }

  getPlayerWithSymbol(symbol) {
    return this.state.playersInfo.filter((playerInfo) => playerInfo.symbol === symbol)[0].username
  }

  onFinishEvent(event) {
    this.setState({
      finished: {
        winningPlayer: this.getPlayerWithSymbol(event.value.winningSymbol)
      }
    })
  }

  isFinished() {

  }

  isUserTurn() {
    return this.props.user.username === this.state.playersInfo[this.state.currentPlayerIndex].username
  }

  selectCell(i, j) {
    if (this.isUserTurn() && !this.isFinished()) {
      if (this.state.board[i][j] === '') {
        const action = {
          id: this.state.gamePlayId,
          action: {i: i, j: j}
        }
        this.props.sendMessage('/api/games/tic-tac-toe/actions', action)
      }
    }
  }

  icon(i, j) {
    const symbol = this.state.board[i][j]
    return <TicTacToeIcon symbol={symbol} className={'tic-tac-toe-symbol'} />
  }

  render() {
    return (
      <div>
        <div id='tic-tac-toe-status'>
          <TicTacToePlayerStatus playersInfo={this.state.playersInfo} currentPlayerIndex={this.state.currentPlayerIndex} finished={this.state.finished}/>
        </div>

        <div id='tic-tac-toe-scheme'>
          <div className={'tic-tac-toe-row'}>
            <div id={'tic-tac-toe-cell-00'} className={'tic-tac-toe-cell'} onClick={() => this.selectCell(0, 0)}>
              { this.icon(0, 0) }
            </div>
            <div id={'tic-tac-toe-cell-01'} className={'tic-tac-toe-cell'} onClick={() => this.selectCell(0, 1)}>
              { this.icon(0, 1) }
            </div>
            <div id={'tic-tac-toe-cell-02'} className={'tic-tac-toe-cell'} onClick={() => this.selectCell(0, 2)}>
              { this.icon(0, 2) }
            </div>
          </div>
          <div className={'tic-tac-toe-row'}>
            <div id={'tic-tac-toe-cell-10'} className={'tic-tac-toe-cell'} onClick={() => this.selectCell(1, 0)}>
              { this.icon(1, 0) }
            </div>
            <div id={'tic-tac-toe-cell-11'} className={'tic-tac-toe-cell'} onClick={() => this.selectCell(1, 1)}>
              { this.icon(1, 1) }
            </div>
            <div id={'tic-tac-toe-cell-12'} className={'tic-tac-toe-cell'} onClick={() => this.selectCell(1, 2)}>
              { this.icon(1, 2) }
            </div>
          </div>
          <div className={'tic-tac-toe-row'}>
            <div id={'tic-tac-toe-cell-20'} className={'tic-tac-toe-cell'} onClick={() => this.selectCell(2, 0)}>
              { this.icon(2, 0) }
            </div>
            <div id={'tic-tac-toe-cell-21'} className={'tic-tac-toe-cell'} onClick={() => this.selectCell(2, 1)}>
              { this.icon(2, 1) }
            </div>
            <div id={'tic-tac-toe-cell-22'} className={'tic-tac-toe-cell'} onClick={() => this.selectCell(2, 2)}>
              { this.icon(2, 2) }
            </div>
          </div>
        </div>
      </div>
    )
  }
}

TicTacToe.propTyeps = {
  router: PropTypes.object.isRequired,
  sendMessage: PropTypes.func.isRequired,
  user: PropTypes.object.isRequired,
  game: PropTypes.object.isRequired,
  table: PropTypes.object.isRequired
}