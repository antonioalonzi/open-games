import React from 'react'
import {mount} from 'enzyme'
import {TicTacToe} from './TicTacToe'
import {Utils} from '../../utils/Utils'

describe('TicTacToe', () => {
  it('should play the game', () => {
    // Given
    const sendMessage = jest.fn()

    const component = mount(<TicTacToe user={{username: 'User1'}} sendMessage={sendMessage}/>)

    // When the server sends the initialize event
    Utils.dispatchEvent('tic-tac-toe-init-event', {
      id: 'game-play-id',
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
      ],
      currentPlayerIndex: 0
    })
    component.update()

    // Then initialized
    expect(component.find('#tic-tac-toe-status-players-current span').at(1).text()).toContain('User1')
    expect(component.find('#tic-tac-toe-status-players-current span').at(2).children().first().props()).toEqual({"className": "fa fa-hand-o-left"})
    expect(component.find('#tic-tac-toe-status-players-current span').at(3).text()).toContain('vs')
    expect(component.find('#tic-tac-toe-status-players-current span').at(4).children().length).toEqual(0)
    expect(component.find('#tic-tac-toe-status-players-current span').at(5).text()).toContain('User2')


    // When click on cell 0 0
    component.find('#tic-tac-toe-cell-00').simulate('click')

    // Then the click event is sent to the server
    expect(sendMessage.mock.calls.length).toEqual(1)
    expect(sendMessage.mock.calls[0][0]).toEqual('/api/games/tic-tac-toe/actions')
    expect(sendMessage.mock.calls[0][1]).toEqual({action: {i: 0, j: 0}, id: 'game-play-id'})


    // When the server sends an update event (action by User1)
    Utils.dispatchEvent('tic-tac-toe-update-event', {
      gameId: 'game-play-id',
      gameState: {
        board:
          [
            ['X', '', ''],
            ['', '', ''],
            ['', '', '']
          ],
        currentPlayerIndex: 1
      }
    })
    component.update()

    expect(component.find('#tic-tac-toe-status-players-current span').at(2).children().length).toEqual(0)
    expect(component.find('#tic-tac-toe-status-players-current span').at(4).children().first().props()).toEqual({"className": "fa fa-hand-o-right"})
    expect(component.find('#tic-tac-toe-cell-00').children().first().props()).toEqual({"className": "tic-tac-toe-symbol", "symbol": "X"})


    // When User1 click on a cell in his opponent's turn
    component.find('#tic-tac-toe-cell-22').simulate('click')

    // Then nothing happens
    expect(sendMessage.mock.calls.length).toEqual(1)


    // When the server sends an update event (action by User2)
    Utils.dispatchEvent('tic-tac-toe-update-event', {
      gameId: 'game-play-id',
      gameState: {
        board:
          [
            ['X', 'O', ''],
            ['', '', ''],
            ['', '', '']
          ],
        currentPlayerIndex: 0
      }
    })
    component.update()

    expect(component.find('#tic-tac-toe-status-players-current span').at(2).children().first().props()).toEqual({"className": "fa fa-hand-o-left"})
    expect(component.find('#tic-tac-toe-status-players-current span').at(4).children().length).toEqual(0)
    expect(component.find('#tic-tac-toe-cell-01').children().first().props()).toEqual({"className": "tic-tac-toe-symbol", "symbol": "O"})


    // When User1 click on a cell that is already selected
    component.find('#tic-tac-toe-cell-00').simulate('click')

    // Then nothing happens
    expect(sendMessage.mock.calls.length).toEqual(1)


    // When the server sends the finish event
    Utils.dispatchEvent('tic-tac-toe-finish-event', {
      winningSymbol: 'X'
    })
    component.update()

    expect(component.find('#tic-tac-toe-status-players-current span').at(2).children().first().props()).toEqual({"className": "fa fa-angellist"})
    expect(component.find('#tic-tac-toe-status-players-current span').at(4).children().length).toEqual(0)
    expect(component.find('#tic-tac-toe-status-players-winner').text()).toEqual('User1 win!')


    // When User1 click on a cell after the game finished
    component.find('#tic-tac-toe-cell-22').simulate('click')

    // Then nothing happens
    expect(sendMessage.mock.calls.length).toEqual(1)
  })

  it('should play the game with a draw', () => {
    // Given
    const sendMessage = jest.fn()

    const component = mount(<TicTacToe user={{username: 'User1'}} sendMessage={sendMessage}/>)

    // When the server sends the initialize event
    Utils.dispatchEvent('tic-tac-toe-init-event', {
      id: 'game-play-id',
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
      ],
      currentPlayerIndex: 0
    })
    component.update()

    // When the server sends the finish event
    Utils.dispatchEvent('tic-tac-toe-finish-event', {
      winningSymbol: ''
    })
    component.update()

    expect(component.find('#tic-tac-toe-status-players-current span').at(2).children().length).toEqual(0)
    expect(component.find('#tic-tac-toe-status-players-current span').at(4).children().length).toEqual(0)
    expect(component.find('#tic-tac-toe-status-players-winner').text()).toEqual("It's a draw!")
  })
})