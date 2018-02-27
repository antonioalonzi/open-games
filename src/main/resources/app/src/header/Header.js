import React from "react";
import {Link} from "react-router-dom";

export class Header extends React.Component {
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