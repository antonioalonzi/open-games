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

class App extends React.Component {
  constructor(props) {
    super(props);
    this.sendMessage = this.sendMessage.bind(this);
  }

  componentDidMount() {
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
        console.log("received user event", event);
      });
    });
  }

  sendMessage(url, message) {
    this.state.stompClient.send(url, {}, JSON.stringify(message));
  }

  render() {
    return (
      <Router>
        <div>
          <Header/>
          <Menu/>
          <div id="content">
            <Messages />
            <Switch>
              <Route path="/" exact={true} component={Welcome} />
              <Route path="/login" render={() => <Login sendMessage={this.sendMessage} />} />
            </Switch>
          </div>
        </div>
      </Router>
    );
  }
}

render(<App/>, document.getElementById("app"));
