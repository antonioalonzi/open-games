import React from 'react';
import {render} from 'react-dom';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import {Header} from "./header/Header";
import {Login} from "./login/Login";
import {Menu} from "./menu/Menu";
import {Welcome} from "./welcome/Welcome";

class App extends React.Component {
  render() {
    return (
      <Router>
        <div>
          <Header/>
          <Menu/>
          <div id="content">
            <Switch>
              <Route path="/" exact={true} component={Welcome}/>
              <Route path="/login" component={Login}/>
            </Switch>
          </div>
        </div>
      </Router>
    );
  }
}

render(<App/>, document.getElementById('app'));
