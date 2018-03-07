import React from "react";
import PropTypes from 'prop-types';


export class Label extends React.Component {
  render() {
    return (
      <label htmlFor={this.props.name}>{this.props.name}</label>
    );
  }
}

Label.propTypes = {
  name: PropTypes.string
};