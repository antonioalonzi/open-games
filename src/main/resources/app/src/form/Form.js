import './form.css'
import React from 'react'
import PropTypes from 'prop-types'


export class Form extends React.Component {
  constructor(props) {
    super(props)
    this.onSubmit = this.onSubmit.bind(this)
    this.handleInputChange = this.handleInputChange.bind(this)

    let fields = {}
    this.props.children.forEach(function(child) {
      if (child.props.name) {
        fields[child.props.name.toLowerCase()] = {
          displayName: child.props.name,
          mandatory: child.props.mandatory,
          value: child.props.value ? child.props.value : '',
          errors: []
        }
      }
    })

    this.state = {
      fields: fields
    }
  }

  handleInputChange(event) {
    const target = event.target
    const value = target.type === 'checkbox' ? target.checked : target.value
    const name = target.name

    let fields = this.state.fields
    fields[name] = {
      value: value,
      errors: []
    }

    this.setState({
      fields: fields
    })
  }

  onSubmit(event) {
    event.preventDefault()
    this.validateFields()
    if (this.isFormValid()) {
      this.props.onFormSubmit(this.createForm())
    }
  }

  createForm() {
    let form = {}
    for (let fieldName in this.state.fields) {
      let field = this.state.fields[fieldName]
      form[fieldName] = field.value
    }
    return form
  }

  validateFields() {
    for (let fieldName in this.state.fields) {
      let field = this.state.fields[fieldName]
      this.validateField(fieldName, field)
    }
  }

  validateField(fieldName, field) {
    this.mandatoryFieldValidation(field, fieldName)
  }

  mandatoryFieldValidation(field, fieldName) {
    if (field.mandatory) {
      if (field.value.trim() === '') {
        let fields = this.state.fields
        fields[fieldName].errors = [field.displayName + ' is mandatory.']
        this.setState({
          fields: fields
        })
      }
    }
  }

  isFormValid() {
    for (let fieldName in this.state.fields) {
      let field = this.state.fields[fieldName]
      if (field.errors.length > 0) {
        return false
      }
    }
    return true
  }

  render() {
    return (
      <form action="" onSubmit={this.onSubmit}>
        {this.props.children.map((child, index) => {
          return React.cloneElement(child, {
            formState: this.state,
            handleInputChange: this.handleInputChange,
            key: index
          })
        })}
      </form>
    )
  }
}

Form.propTypes = {
  onFormSubmit: PropTypes.func.isRequired
}



export class Label extends React.Component {
  render() {
    const lowerCaseName = this.props.name.toLowerCase()
    return (
      <label htmlFor={lowerCaseName} className="form">{this.props.name}:</label>
    )
  }
}

Label.propTypes = {
  name: PropTypes.string.isRequired
}



export class FormError extends React.Component {
  constructor(props) {
    super(props)
  }

  render() {
    if (this.props.errors.length > 0) {
      return (
        <div className="form-error">
          {this.props.errors[0]}
        </div>
      )
    } else {
      return null
    }
  }
}

FormError.propTypes = {
  errors: PropTypes.array.isRequired
}



export class Input extends React.Component {
  constructor(props) {
    super(props)
  }

  render() {
    const lowerCaseName = this.props.name.toLowerCase()
    if (this.props.type === 'hidden') {
      return <input type={this.props.type} id={lowerCaseName} name={lowerCaseName} value={this.props.formState.fields[lowerCaseName].value} />
    } else {
      return (
        <div className="form-row">
          <div className="form-col-25">
            <Label name={this.props.name}/>
          </div>
          <div className="form-col-75">
            <div>
              <input type={this.props.type}
                id={lowerCaseName}
                name={lowerCaseName}
                className='form'
                placeholder={this.props.name + '...'}
                autoFocus={this.props.autoFocus}
                value={this.props.formState.fields[lowerCaseName].value}
                onChange={this.props.handleInputChange}/>
            </div>
            <FormError errors={this.props.formState.fields[lowerCaseName].errors}/>
          </div>
        </div>
      )
    }
  }
}

Input.propTypes = {
  type: PropTypes.string.isRequired,
  name: PropTypes.string.isRequired,
  mandatory: PropTypes.bool,
  autoFocus: PropTypes.bool,
  value: PropTypes.string
}



export class Button extends React.Component {
  render() {
    const type = this.props.type ? this.props.type : 'button'
    return (
      <input type={type} value={this.props.value} className={this.props.className} onClick={this.props.onClick} />
    )
  }
}

Button.propTypes = {
  value: PropTypes.string.isRequired,
  type: PropTypes.string,
  className: PropTypes.string,
  onClick: PropTypes.func
}



export class Submit extends React.Component {
  submitValue() {
    return this.props.value ? this.props.value : 'Submit'
  }

  render() {
    return (
      <div className="form-row">
        <Button className="form" type="submit" value={this.submitValue()}/>
      </div>
    )
  }
}

Submit.propTypes = {
  value: PropTypes.string
}