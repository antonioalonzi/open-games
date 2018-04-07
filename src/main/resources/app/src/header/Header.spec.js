import React from 'react';
import {Header, HeaderLeft, HeaderRight} from './Header';
import {shallow} from 'enzyme';

describe('test header', () => {
  it('should display header', () => {
    // When
    const component = shallow(<Header/>);

    // Then
    expect(component.find('HeaderLeft').length).toEqual(1);
    expect(component.find('HeaderRight').length).toEqual(1);
  });
});