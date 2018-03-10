import React from 'react';
import PropTypes from 'prop-types';


export class Form extends React.Component {
  constructor(props) {
    super(props);
    this.onSubmit = this.onSubmit.bind(this);
    this.handleInputChange = this.handleInputChange.bind(this);

    let fields = {};
    this.props.children.forEach(function(child) {
      if (child.props.name) {
        fields[child.props.name.toLowerCase()] = '';
      }
    });

    this.state = {
      fields: fields
    }
  }

  handleInputChange(event) {
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    this.setState({
      fields: {
        [name]: value
      }
    });
  }

  onSubmit(event) {
    event.preventDefault();

    this.props.onFormSubmit(this.state);
  }

  render() {
    return (
      <form action="" onSubmit={this.onSubmit}>
        {this.props.children.map((child, index) => {
          return React.cloneElement(child, {
            formState: this.state,
            handleInputChange: this.handleInputChange,
            key: index
          });
        })}
      </form>
    );
  }
}

Form.propTypes = {
  onFormSubmit: PropTypes.func.isRequired
};


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
  constructor(props) {
    super(props);
  }

  render() {
    const lowerCaseName = this.props.name.toLowerCase();
    return (
      <div className="form-row">
        <div className="form-col-25">
          <Label name={this.props.name}/>
        </div>
        <div className="form-col-75">
          <div>
            <input type="text" id={lowerCaseName} name={lowerCaseName} placeholder={this.props.name + '...'}
                   value={this.props.formState.fields[lowerCaseName]} onChange={this.props.handleInputChange}/>
          </div>
          <div className="form-error">
            {/*{this.props.errors[lowerCaseName]}*/}
          </div>
        </div>
      </div>
    );
  }
}

Input.propTypes = {
  type: PropTypes.string.isRequired,
  name: PropTypes.string.isRequired
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