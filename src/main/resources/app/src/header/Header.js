import React from "react";
import {NavLink} from "react-router-dom";

export class Header extends React.Component {
    render () {
        return (
            <div id="header">
                <div id="header-left">
                    <NavLink to="/">Open Games</NavLink>
                </div>
                <div id="header-right">
                    <NavLink to="/login">Login</NavLink>
                </div>
            </div>
        );
    }
}