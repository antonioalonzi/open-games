import React from 'react'
import {Utils} from '../utils/Utils'
import PropTypes from 'prop-types'
import {Form, Input, Submit} from '../form/Form'


class NewTable extends React.Component {
  constructor(props) {
    super(props)
    this.onFormSubmit = this.onFormSubmit.bind(this)
    this.onCreateTableResponse = this.onCreateTableResponse.bind(this)
  }

  componentWillMount() {
    Utils.addEventListener('create-table-response', this.onCreateTableResponse)
  }

  componentWillUnmount() {
    Utils.removeEventListener('create-table-response', this.onCreateTableResponse)
  }

  onFormSubmit(formData) {
    this.props.sendMessage('/api/table', formData)
  }

  onCreateTableResponse(createTableResponse) {
    if (createTableResponse.value.responseStatus === 'ERROR') {
      Utils.dispatchEvent('message', {type: 'error', text: createTableResponse.value.message})
    } else {
      Utils.dispatchEvent('message', {type: 'info', text: createTableResponse.value.message})
    }
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
  render() {
    return (
      <div className='game-table'>
        <div>owner: {this.props.table.owner}</div>
        <div>status: {this.props.table.status}</div>
      </div>
    )
  }
}

GameTable.propTyeps = {
  table: PropTypes.object.isRequired
}



export class Game extends React.Component {
  componentWillMount() {
    Utils.checkAuthenticatedUser(this.props.user, this.props.router)
  }

  render() {
    const gameLabel = this.props.router.match.params.label
    const game = this.props.games.filter((game) => game.label===gameLabel)[0]
    const tables = this.props.tables.filter((table) => table.game===gameLabel)
    if (game) {
      return (
        <div id='game-description'>
          <div>
            <h2>{game.name}</h2>
            <p>{game.description}</p>
            <h3>Tables ({this.props.tables.length}):</h3>
            <div id='game-tables-container'>
              { tables.map(table => (<GameTable key={table.id} table={table} />)) }
              <NewTable user={this.props.user} game={game} tables={tables} sendMessage={this.props.sendMessage} />
            </div>
          </div>
        </div>
      )
    } else {
      return null;
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