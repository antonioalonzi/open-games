import './tictactoe.css'
import React from 'react'
import PropTypes from 'prop-types'
import {Utils} from "../../utils/Utils";


export class TicTacToe extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      board: [
        ['', '', ''],
        ['', 'X', 'O'],
        ['', '', '']
      ],
      playersInfo: [],
      currentPlayerIndex: 0
    }
    this.onInitializationEvent = this.onInitializationEvent.bind(this)
  }

  componentWillMount() {
    Utils.addEventListener('tic-tac-toe-initialization-event', this.onInitializationEvent)
  }

  componentWillUnmount() {
    Utils.removeEventListener('tic-tac-toe-initialization-event', this.onInitializationEvent)
  }

  opponent() {
    if (this.props.user.username === this.props.table.owner) {
      return this.props.table.joiners[0]
    } else {
      return this.props.table.owner
    }
  }

  onInitializationEvent(event) {
    this.setState({
      playersInfo: event.value.playersInfo,
      currentPlayerIndex: event.value.currentPlayerIndex
    })
  }

  selectCell(i, j) {
    alert(i + '-' + j)
  }

  icon(i, j) {
    const symbol = this.state.board[i][j];
    switch (symbol) {
      case 'X':
        return <i className={'fa fa-times tic-tac-toe-symbol'} />
      case 'O':
        return <i className={'fa fa-genderless tic-tac-toe-symbol'} />
      default:
        return null
    }
  }

  render() {
    return (
      <div>
        <div id='tic-tac-toe-status'>
          <span>{this.props.user.username}</span> vs <span>{this.opponent()}</span>
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