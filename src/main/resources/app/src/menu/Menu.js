import React from 'react';
import {NavLink} from 'react-router-dom';

export class Menu extends React.Component {
  render() {
    return (
      <div id="menu">
        <ul>
          <li>
            <NavLink to="/portal/game/tictactoe">TicTacToe</NavLink>
          </li>
        </ul>
      </div>
    );
  }
}