import './tictactoe.css'
import React from 'react'
import PropTypes from 'prop-types'
import {Utils} from "../../utils/Utils";


export class TicTacToe extends React.Component {
  constructor(props) {
    super(props)
  }

  componentWillMount() {
    Utils.addEventListener('ltic-tac-toe-initialization-event', this.onLoginResponse)
  }

  componentWillUnmount() {
    Utils.removeEventListener('tic-tac-toe-initialization-event', this.onLoginResponse)
  }

  opponent() {
    if (this.props.user.username === this.props.table.owner) {
      return this.props.table.joiners[0]
    } else {
      return this.props.table.owner
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
            <div id={'tic-tac-toe-cell-11'} className={'tic-tac-toe-cell'} />
            <div id={'tic-tac-toe-cell-12'} className={'tic-tac-toe-cell'} />
            <div id={'tic-tac-toe-cell-13'} className={'tic-tac-toe-cell'} />
          </div>
          <div className={'tic-tac-toe-row'}>
            <div id={'tic-tac-toe-cell-21'} className={'tic-tac-toe-cell'} />
            <div id={'tic-tac-toe-cell-22'} className={'tic-tac-toe-cell'} />
            <div id={'tic-tac-toe-cell-23'} className={'tic-tac-toe-cell'} />
          </div>
          <div className={'tic-tac-toe-row'}>
            <div id={'tic-tac-toe-cell-31'} className={'tic-tac-toe-cell'} />
            <div id={'tic-tac-toe-cell-32'} className={'tic-tac-toe-cell'} />
            <div id={'tic-tac-toe-cell-33'} className={'tic-tac-toe-cell'} />
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