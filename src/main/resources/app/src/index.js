import React from 'react';
import {render} from 'react-dom';
import {BrowserRouter as Router, Route} from "react-router-dom";
import {Header} from "./header/Header";
import {Login} from "./login/Login";

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

render(<App/>, document.getElementById('app'));
