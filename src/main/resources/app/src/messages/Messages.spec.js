import React from 'react'
import {mount} from 'enzyme'
import {Messages} from './Messages'
import {Utils} from '../utils/Utils'

describe('test messages', () => {
  it('should display some messages', () => {
    // Given
    const component = mount(<Messages/>)

    // When
    Utils.dispatchEvent('message', {type: 'error', text: 'First error.'})
    Utils.dispatchEvent('message', {type: 'error', text: 'Second error.'})
    Utils.dispatchEvent('message', {type: 'info', text: 'First message.'})
    Utils.dispatchEvent('message', {type: 'info', text: 'Second message.'})
    component.update()

    // Then
    let errors = component.find('div.message-error')
    expect(errors.length).toEqual(2)
    expect(errors.at(0).text()).toEqual('First error.')
    expect(errors.at(1).text()).toEqual('Second error.')
    let infos = component.find('div.message-info')
    expect(infos.at(0).text()).toEqual('First message.')
    expect(infos.at(1).text()).toEqual('Second message.')
    expect(infos.length).toEqual(2)
  })
})