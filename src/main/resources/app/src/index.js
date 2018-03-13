import React from 'react';
import {render} from 'react-dom';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import {Header} from "./header/Header";
import {Login} from "./login/Login";
import {Menu} from "./menu/Menu";
import {Welcome} from "./welcome/Welcome";
import SockJs from "sockjs-client"
import Stomp from "@stomp/stompjs"
import {Messages} from "./messages/Messages";
import {Utils} from "./utils/Utils";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.sendMessage = this.sendMessage.bind(this);
    this.state = {
      stompClient: null,
      user: null
    }
  };

  componentWillMount() {
    let socket = new SockJs("/open-games-ws");
    let stompClient = Stomp.over(socket);
    this.setState({
      stompClient: stompClient
    });
    stompClient.connect({}, () => {
      this.sessionId = socket._transport.url.split("/")[5];

      stompClient.subscribe("/topic/events", (event) => {
        console.log("received topic event: ", event);
      });

      stompClient.subscribe("/user/" + this.sessionId + "/events", (event) => {
        const eventBody = JSON.parse(event.body);
        switch (eventBody.type) {
          case 'login-event': this.login(eventBody.value); break;
        }
      });
    });
  }

  sendMessage(url, message) {
    this.state.stompClient.send(url, {}, JSON.stringify(message));
  }

  login(loginResponse) {
    if (loginResponse.loginResponseStatus === 'ERROR') {
      Utils.dispatchEvent('message', {type: 'error', text: loginResponse.message});
    } else {
      Utils.dispatchEvent('message', {type: 'info', text: loginResponse.message});
      this.setState({
        user: loginResponse.userDetails
      });
    }
  }

  render() {
    return (
      <Router>
        <div>
          <Header user={this.state.user}/>
          <Menu/>
          <div id="content">
            <Messages />
            <Switch>
              <Route path="/" exact={true} component={Welcome} />
              <Route path="/portal/" exact={true} component={Welcome} />
              <Route path="/portal/index.html" exact={true} component={Welcome} />
              <Route path="/portal/login" render={() => <Login sendMessage={this.sendMessage} />} />
            </Switch>
          </div>
        </div>
      </Router>
    );
  }
}

render(<App/>, document.getElementById("app"));
