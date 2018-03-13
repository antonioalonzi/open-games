import React from 'react';
import {NavLink} from 'react-router-dom';

export class Menu extends React.Component {
  render() {
    return (
      <div id="menu">
        <ul>
          <li>
            <NavLink to="/portal/page-1">Page 1</NavLink>
          </li>
          <li>
            <NavLink to="/portal/page-2">Page 2</NavLink>
          </li>
        </ul>
      </div>
    );
  }
}