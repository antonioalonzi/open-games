import React from 'react';
import {render} from 'react-dom';
import {BrowserRouter as Router, Link, Route} from "react-router-dom";

class Header extends React.Component {
    render () {
        return (
            <div id="header">
                <div id="header-left">
                    <Link to="/">Open Games</Link>
                </div>
                <div id="header-right">
                    <Link to="/login">Login</Link>
                </div>
            </div>
        );
    }
}

class App extends React.Component {
    render () {
        return (
            <Router>
                <div>
                    <Header/>
                    <Route path="/login" component={Login} />
                </div>
            </Router>
        );
    }
}

class Login extends React.Component {
    render() {
        return (
            <div>Login</div>
        );
    }
}

render(<App/>, document.getElementById('app'));
