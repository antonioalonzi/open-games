import React from 'react'
import PropTypes from 'prop-types'
import {Button, Form, Input, Submit} from '../form/Form'


export class NewGameTable extends React.Component {
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

NewGameTable.propTyeps = {
  sendMessage: PropTypes.func.isRequired,
  user: PropTypes.object.isRequired,
  game: PropTypes.object.isRequired,
}



export class GameTable extends React.Component {
  constructor(props) {
    super(props)
    this.onClick = this.onClick.bind(this)
  }

  onClick() {
    this.props.sendMessage('/api/table/join', {tableId: this.props.table.id})
  }

  static getUserActiveTable(user, tables) {
    if (user == null) {
      return null
    }

    return tables
      .filter(table => table.status === 'NEW' || table.status === 'IN_PROGRESS')
      .filter(table => table.owner === user.username || table.joiners.indexOf(user.username) >= 0)[0]
  }

  static isUserActiveInATable(user, tables) {
    if (GameTable.getUserActiveTable(user, tables)) {
      return true
    } else {
      return false
    }
  }

  render() {
    return (
      <div className='game-table'>
        <div>Owner: {this.props.table.owner}</div>
        <div>Status: {this.props.table.status}</div>
        <div>Joiners: { this.props.table.joiners.map(joiner => <span key={joiner}>{joiner}</span>) }</div>
        { this.props.isUserActiveInATable ? null : <Button value="Join" className={'form'} onClick={this.onClick} /> }
      </div>
    )
  }
}

GameTable.propTyeps = {
  sendMessage: PropTypes.func.isRequired,
  table: PropTypes.object.isRequired,
  isUserActiveInATable: PropTypes.bool.isRequired
}
