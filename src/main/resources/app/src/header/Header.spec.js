import React from 'react';
import {Header} from './Header';
import {shallow} from 'enzyme';

describe('test header', () => {
  it('should display header', () => {
    // When
    const component = shallow(<Header/>);

    // Then
    const headerLeft = component.find('#header-left');
    const headerLeftLinks = headerLeft.find('NavLink');
    expect(headerLeftLinks.length).toEqual(1);

    const headerRight = component.find('#header-right');
    const headerRightLinks = headerRight.find('NavLink');
    expect(headerRightLinks.length).toEqual(1);
  });
});