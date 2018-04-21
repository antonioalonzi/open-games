import './styles.css'
import React from 'react'
import {render} from 'react-dom'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import SockJs from 'sockjs-client'
import Stomp from '@stomp/stompjs'
import {Utils} from './utils/Utils'
import {Messages} from './messages/Messages'
import {Header} from './header/Header'
import {Menu} from './menu/Menu'
import {Login} from './login/Login'
import {Game} from './game/Game'
import {GamePlay} from './game/GamePlay'
import {GameTable} from './game/GameTable'

class App extends React.Component {
  constructor(props) {
    super(props)
    this.sendMessage = this.sendMessage.bind(this)
    this.setAppState = this.setAppState.bind(this)
    this.onGamePublished = this.onGamePublished.bind(this)
    this.onTableCreated = this.onTableCreated.bind(this)
    this.onTableUpdated = this.onTableUpdated.bind(this)
    this.onUserLoggedIn = this.onUserLoggedIn.bind(this)
    this.onUserDisconnected = this.onUserDisconnected.bind(this)
    this.onToggleMenu = this.onToggleMenu.bind(this)
    this.state = {
      stompClient: null,
      menuMobileExpanded: false,
      user: null,
      loggedInUsers: [],
      games: [],
      tables: []
    }
  }

  componentWillMount() {
    let socket = new SockJs('/open-games-ws')
    let stompClient = Stomp.over(socket)
    this.setState({
      stompClient: stompClient
    })
    stompClient.connect({}, () => {
      this.sessionId = socket._transport.url.split('/')[5]

      stompClient.subscribe('/topic/events', (event) => {
        const eventBody = JSON.parse(event.body)
        Utils.dispatchEvent(eventBody.type, eventBody.value)
      })

      stompClient.subscribe('/user/' + this.sessionId + '/events', (event) => {
        const eventBody = JSON.parse(event.body)
        Utils.dispatchEvent(eventBody.type, eventBody.value)
      })

      this.sendMessage('/api/init', '{}')
    })

    Utils.addEventListener('set-state', this.setAppState)
    Utils.addEventListener('game-published-event', this.onGamePublished)
    Utils.addEventListener('table-created-event', this.onTableCreated)
    Utils.addEventListener('table-updated-event', this.onTableUpdated)
    Utils.addEventListener('user-logged-in-event', this.onUserLoggedIn)
    Utils.addEventListener('user-disconnected-event', this.onUserDisconnected)
    Utils.addEventListener('toggle-menu-event', this.onToggleMenu)
  }

  sendMessage(url, message) {
    this.state.stompClient.send(url, {}, JSON.stringify(message))
  }

  onToggleMenu() {
    this.setState({
      menuMobileExpanded: !this.state.menuMobileExpanded
    })
  }

  onUserLoggedIn(event) {
    const user = {username: event.value.username}
    this.setState({
      loggedInUsers: [...this.state.loggedInUsers, user]
    })
  }

  onUserDisconnected(event) {
    const user = {username: event.value.username}
    this.setState({
      loggedInUsers: this.state.loggedInUsers.splice(this.state.loggedInUsers.indexOf(user), 1)
    })
  }

  setAppState(event) {
    let currentState = this.state
    currentState[event.value.key] = event.value.text
    this.setState(currentState)
  }

  onGamePublished(event) {
    const game = {
      label: event.value.label,
      name: event.value.name,
      description: event.value.description,
      minNumPlayers: event.value.minNumPlayers,
      maxNumPlayers: event.value.maxNumPlayers
    }
    this.setState({
      games: [...this.state.games, game]
    })
  }

  onTableCreated(event) {
    const table = {
      id: event.value.id,
      game: event.value.game,
      owner: event.value.owner,
      status: event.value.status,
      joiners: event.value.joiners
    }
    this.setState({
      tables: [...this.state.tables, table]
    })
  }

  onTableUpdated(event) {
    const tables = this.state.tables
    const table = App.findTableById(tables, event.value.id)
    if (event.value.status) {
      table.status = event.value.status
    }
    if (event.value.joiners) {
      table.joiners = event.value.joiners
    }
    this.setState({
      tables: tables
    })

    if (table.status === 'IN_PROGRESS') {
      if (table.owner === this.state.user.username || table.joiners.indexOf(this.state.user.username) >= 0) {
        Utils.dispatchEvent('start-game', table.game)
      }
    }
  }

  static findTableById(tables, tableId) {
    return tables.filter(table => table.id === tableId)[0]
  }

  render() {
    const hiddenIfNotLoggedIn = this.state.user ? '' : 'hidden'
    const activeTable = GameTable.getUserActiveTable(this.state.user, this.state.tables)
    const activeTableGame = activeTable ? Game.gameByLabel(this.state.games, activeTable.game) : null
    return (
      <Router>
        <div>
          <Header user={this.state.user} />
          <Menu className={hiddenIfNotLoggedIn} games={this.state.games} user={this.state.user} loggedInUsers={this.state.loggedInUsers} menuMobileExpanded={this.state.menuMobileExpanded} />
          <div id="content">
            <Messages />
            <Switch>
              <Route path="/portal/game/:label" exact={true} render={(router) => <Game user={this.state.user} games={this.state.games} tables={this.state.tables} router={router} sendMessage={this.sendMessage} />} />
              <Route path="/portal/game/:label/play" exact={true} render={(router) => <GamePlay user={this.state.user} game={activeTableGame} table={activeTable} router={router} sendMessage={this.sendMessage} />} />
              <Route render={(router) => <Login user={this.state.user} router={router} sendMessage={this.sendMessage} />} />
            </Switch>
          </div>
        </div>
      </Router>
    )
  }
}

render(<App/>, document.getElementById('app'))
