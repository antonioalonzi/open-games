import React from 'react';
import {render} from 'react-dom';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import {Header} from "./header/Header";
import {Login} from "./login/Login";
import {Menu} from "./menu/Menu";
import {Welcome} from "./welcome/Welcome";
import SockJsClient from "react-stomp";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.sendMessage = this.sendMessage.bind(this);
  }

  sendMessage(url, msg) {
    this.clientRef.sendMessage("/api/login", JSON.stringify(msg));
  }

  render() {
    return (
      <Router>
        <div>
          <SockJsClient url="http://localhost:8080/open-games-ws" topics={["/topic/messages"]}
                        onMessage={(msg) => { console.log(msg); }}
                        ref={ (client) => { this.clientRef = client }} />
          <Header/>
          <Menu/>
          <div id="content">
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

render(<App/>, document.getElementById('app'));
