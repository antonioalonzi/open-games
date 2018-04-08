import React from 'react';
import {Login} from './Login';
import {mount} from 'enzyme';

describe('test login', () => {
  it('should display login form', () => {
    // When
    const component = mount(<Login/>);

    // Then
    expect(component.find('input[name="username"]').length).toEqual(1);
    expect(component.find('input[type="submit"]').length).toEqual(1);
  });
});