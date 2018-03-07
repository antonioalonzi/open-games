import React from 'react';
import {Login} from './Login';
import {shallow} from 'enzyme';

describe('test login', () => {
  it('should display login form', () => {
    // When
    const component = shallow(<Login/>);

    // Then
    const usernameInput = component.find('input[name=\'username\']');
    expect(usernameInput.length).toEqual(1);

    const passwordInput = component.find('input[name=\'password\']');
    expect(passwordInput.length).toEqual(1);

    const submitButton = component.find('input[type=\'submit\']');
    expect(submitButton.length).toEqual(1);
  });

  it('should display error if username not typed', () => {
    // Given
    const component = shallow(<Login/>);

    // When
    component.find('input[type=\'submit\']').simulate('click');

    // Then
    const errorDiv = component.find('div.error');
    expect(errorDiv.length).toEqual(1);
  });
});