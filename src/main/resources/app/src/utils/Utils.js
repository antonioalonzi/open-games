import React from 'react';

export class Utils extends React.Component {
  static isBlank(str) {
    return (!str || /^\s*$/.test(str));
  }
}