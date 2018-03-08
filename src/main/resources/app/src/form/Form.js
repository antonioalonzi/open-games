import React from 'react';
import PropTypes from 'prop-types';


export class Label extends React.Component {
  render() {
    const lowerCaseName = this.props.name.toLowerCase();
    return (
      <label htmlFor={lowerCaseName}>{this.props.name}</label>
    );
  }
}

Label.propTypes = {
  name: PropTypes.string.isRequired
};


export class Input extends React.Component {
  render() {
    const lowerCaseName = this.props.name.toLowerCase();
    return (
      <div className="form-row">
        <div className="form-col-25">
          <Label name={this.props.name}/>
        </div>
        <div className="form-col-75">
          <input type="text" id={lowerCaseName} name={lowerCaseName} placeholder={this.props.name + '...'} value={this.props.value} onChange={this.props.onChange}/>
        </div>
      </div>
    );
  }
}

Input.propTypes = {
  type: PropTypes.string.isRequired,
  name: PropTypes.string.isRequired,
  value: PropTypes.string,
  onChange: PropTypes.function
};


export class Submit extends React.Component {
  render() {
    return (
      <div className="form-row">
        <input type="submit" value="Submit"/>
      </div>
    );
  }
}