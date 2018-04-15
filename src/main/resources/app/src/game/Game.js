import React from 'react'
import {Utils} from '../utils/Utils'
import PropTypes from 'prop-types'
import {Form, Input, Submit} from '../form/Form'


class NewTable extends React.Component {
  constructor(props) {
    super(props)
    this.onFormSubmit = this.onFormSubmit.bind(this)
  }

  onFormSubmit(formData) {
    this.props.sendMessage('/api/table/create', formData)
  }

  render() {
    return (
      <div className='game-table'>
        <Form onFormSubmit={this.onFormSubmit}>
          <Input type={'hidden'} name={'game'} value={this.props.game.label} />
          <Submit value={'New'}/>
        </Form>
      </div>
    )
  }
}

NewTable.propTyeps = {
  sendMessage: PropTypes.func.isRequired,
  user: PropTypes.object.isRequired,
  game: PropTypes.object.isRequired,
}



class GameTable extends React.Component {
  constructor(props) {
    super(props)
    this.onClick = this.onClick.bind(this)
  }

  onClick() {
    this.props.sendMessage('/api/table/join', {tableId: this.props.table.id})
  }

  render() {
    return (
      <div className='game-table'>
        <div>Owner: {this.props.table.owner}</div>
        <div>Status: {this.props.table.status}</div>
        <div>Joiners: { this.props.table.joiners.map(joiner => <span key={joiner}>{joiner}</span>) }</div>
        { this.props.isUserActiveInATable ? null : <input type="button" value="Join" onClick={this.onClick}/> }
      </div>
    )
  }
}

GameTable.propTyeps = {
  sendMessage: PropTypes.func.isRequired,
  table: PropTypes.object.isRequired,
  isUserActiveInATable: PropTypes.bool.isRequired
}



export class Game extends React.Component {
  constructor(props) {
    super(props)
    this.onCreateTableResponse = this.onCreateTableResponse.bind(this)
  }

  componentWillMount() {
    Utils.checkAuthenticatedUser(this.props.user, this.props.router)
    Utils.addEventListener('create-table-response', this.onCreateTableResponse)
    Utils.addEventListener('join-table-response', this.onJoinTableResponse)
  }

  componentWillUnmount() {
    Utils.removeEventListener('create-table-response', this.onCreateTableResponse)
    Utils.removeEventListener('join-table-response', this.onJoinTableResponse)
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

  gameLabel() {
    return this.props.router.match.params.label
  }

  game() {
    return this.props.games.filter(game => game.label === this.gameLabel())[0]
  }

  tables() {
    return this.props.tables
      .filter(table => table.game === this.gameLabel())
      .filter(table => table.status === 'NEW')
  }

  isUserActiveInATable() {
    return this.props.tables
      .filter(table => table.status === 'NEW' || table.status === 'IN_PROGRESS')
      .filter(table => table.owner === this.props.user.username || table.joiners.indexOf(this.props.user.username) >= 0)
      .length > 0
  }

  render() {
    const newTable = this.isUserActiveInATable() ? null : <NewTable user={this.props.user} game={this.game()} sendMessage={this.props.sendMessage} />
    if (this.game()) {
      return (
        <div id='game-description'>
          <div>
            <h2>{this.game().name}</h2>
            <p>{this.game().description}</p>
            <h3>Tables ({this.tables().length}):</h3>
            <div id='game-tables-container'>
              { this.tables().map(table => (<GameTable key={table.id} table={table} sendMessage={this.props.sendMessage} isUserActiveInATable={this.isUserActiveInATable()}/>)) }
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