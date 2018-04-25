import './game.css'
import React from 'react'
import {Utils} from '../utils/Utils'
import PropTypes from 'prop-types'
import {GameTable, NewGameTable} from './GameTable'


export class Game extends React.Component {
  constructor(props) {
    super(props)
    this.onCreateTableResponse = this.onCreateTableResponse.bind(this)
    this.onStartGame = this.onStartGame.bind(this)
  }

  componentWillMount() {
    Utils.checkAuthenticatedUser(this.props.user, this.props.router)
    Utils.addEventListener('create-table-response', this.onCreateTableResponse)
    Utils.addEventListener('join-table-response', this.onJoinTableResponse)
    Utils.addEventListener('start-game', this.onStartGame)
  }

  componentWillUnmount() {
    Utils.removeEventListener('create-table-response', this.onCreateTableResponse)
    Utils.removeEventListener('join-table-response', this.onJoinTableResponse)
    Utils.addEventListener('start-game', this.onStartGame)
  }

  onCreateTableResponse(createTableResponse) {
    if (createTableResponse.value.responseStatus === 'ERROR') {
      Utils.dispatchEvent('message', {type: 'error', text: createTableResponse.value.message})
    } else {
      Utils.dispatchEvent('message', {type: 'info', text: createTableResponse.value.message})
    }
  }

  onJoinTableResponse(joinTableResponse) {
    if (joinTableResponse.value.responseStatus === 'ERROR') {
      Utils.dispatchEvent('message', {type: 'error', text: joinTableResponse.value.message})
    } else {
      Utils.dispatchEvent('message', {type: 'info', text: joinTableResponse.value.message})
    }
  }

  static gameByLabel(games, label) {
    return games.filter(game => game.label === label)[0]
  }

  onStartGame(event) {
    this.props.router.history.push('/portal/game/' + event.value + "/play")
  }

  gameLabel() {
    return this.props.router.match.params.label
  }

  game() {
    return Game.gameByLabel(this.props.games, this.gameLabel())
  }

  tables() {
    return this.props.tables
      .filter(table => table.game === this.gameLabel())
      .filter(table => table.status === 'NEW')
  }

  render() {
    const newTable = GameTable.isUserActiveInATable(this.props.user, this.props.tables) ? null : <NewGameTable user={this.props.user} game={this.game()} sendMessage={this.props.sendMessage} />
    if (this.game()) {
      return (
        <div id='game-description'>
          <div>
            <h2>{this.game().name}</h2>
            <p>{this.game().description}</p>
            <h3>Tables ({this.tables().length}):</h3>
            <div id='game-tables-container'>
              { this.tables().map(table => (<GameTable key={table.id} table={table} sendMessage={this.props.sendMessage} isUserActiveInATable={GameTable.isUserActiveInATable(this.props.user, this.props.tables)}/>)) }
              { newTable }
            </div>
          </div>
        </div>
      )
    } else {
      return null
    }
  }
}

Game.propTyeps = {
  router: PropTypes.object.isRequired,
  sendMessage: PropTypes.func.isRequired,
  user: PropTypes.object.isRequired,
  games: PropTypes.array.isRequired,
  tables: PropTypes.array.isRequired
}