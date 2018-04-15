import './tictactoe.css'
import React from 'react'
import PropTypes from 'prop-types'


export class TicTacToe extends React.Component {
  constructor(props) {
    super(props)
  }

  componentWillMount() {
  }

  componentWillUnmount() {
  }

  render() {
    return (
      <div>
        {this.props.game.name}
        {this.props.table.id}
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