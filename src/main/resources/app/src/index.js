import React from 'react'
import {render} from 'react-dom'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import SockJs from 'sockjs-client'
import Stomp from '@stomp/stompjs'
import {Utils} from './utils/Utils'
import {Messages} from './messages/Messages'
import {Header} from './menu/Header'
import {GamesMenu} from './menu/GamesMenu'
import {UsersMenu} from './menu/UsersMenu'
import {Login} from './login/Login'
import {Game} from './game/Game'

class App extends React.Component {
  constructor(props) {
    super(props)
    this.sendMessage = this.sendMessage.bind(this)
    this.setAppState = this.setAppState.bind(this)
    this.onGamePublished = this.onGamePublished.bind(this)
    this.state = {
      stompClient: null,
      user: null,
      games: []
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
    Utils.addEventListener('game-published', this.onGamePublished)
  }

  sendMessage(url, message) {
    this.state.stompClient.send(url, {}, JSON.stringify(message))
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
      description: event.value.description
    }
    this.setState(prevState => ({
      games: [...prevState.games, game]
    }))
  }

  render() {
    const hiddenIfNotLoggedIn = this.state.user ? '' : 'hidden'
    return (
      <Router>
        <div>
          <Header user={this.state.user} />
          <GamesMenu className={hiddenIfNotLoggedIn} games={this.state.games} />
          <UsersMenu className={hiddenIfNotLoggedIn} user={this.state.user} />
          <div id="content">
            <Messages />
            <Switch>
              <Route path="/" exact={true} render={(router) => <Login user={this.state.user} router={router} sendMessage={this.sendMessage} />} />
              <Route path="/portal/" exact={true} render={(router) => <Login user={this.state.user} router={router} sendMessage={this.sendMessage} />} />
              <Route path="/portal/game/:label" exact={true} render={(router) => <Game user={this.state.user} games={this.state.games} router={router} sendMessage={this.sendMessage} />} />
            </Switch>
          </div>
        </div>
      </Router>
    )
  }
}

render(<App/>, document.getElementById('app'))
