import React from 'react'
import {mount} from 'enzyme'
import {TicTacToe} from './TicTacToe'
import {Utils} from '../../utils/Utils'

describe('TicTacToe', () => {
  it('should display login form', () => {
    // Given
    const component = mount(<TicTacToe/>)

    // When
    Utils.dispatchEvent('tic-tac-toe-init-event', {
      gamePlayId: 1,
      playersInfo: [
        {
          username: 'User1',
          symbol: 'X'
        },
        {
          username: 'User2',
          symbol: 'O'
        }
      ]
    })

    component.update()

    // Then
    expect(component.find('#tic-tac-toe-status-players-current span').at(1).text()).toContain('User1')
    expect(component.find('#tic-tac-toe-status-players-current span').at(3).text()).toContain('vs')
    expect(component.find('#tic-tac-toe-status-players-current span').at(5).text()).toContain('User2')
  })
})