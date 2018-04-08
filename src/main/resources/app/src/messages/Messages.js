import React from 'react';
import {Utils} from '../utils/Utils';

export class Messages extends React.Component {
  constructor(props) {
    super(props);
    this.onMessage = this.onMessage.bind(this);
    this.state = {
      messages: [],
      lastId: 0
    };
  }

  componentWillMount() {
    Utils.addEventListener('message', this.onMessage);
    Utils.messagesComponent = this;
  }

  componentWillUnmount() {
    // TODO this does not work
    // Utils.removeEventListener('message', this.onMessage);
  }

  onMessage(message) {
    const messageId = this.generateMessageId();
    this.addMessage({
      id: messageId,
      type: message.value.type,
      text: message.value.text
    });
    setTimeout(() => {this.removeMessage(messageId);}, 6000);
  }

  addMessage(message) {
    let messages = this.state.messages;
    messages.push(message);
    this.setState({
      messages: messages
    });
  }

  removeMessage(messageId) {
    let messages = this.state.messages;
    messages.forEach((message, index) => {
      if (message.id === messageId) {
        messages.splice(index, 1);
      }
    });
    this.setState({
      messages: messages
    });
  }

  generateMessageId() {
    const messageId = this.state.lastId + 1;
    this.setState({
      lastId: messageId
    });
    return messageId;
  }

  render() {
    return (
      <div id={'messages-container'}>
        {
          this.state.messages.map(message => (
            <Message type={message.type} key={message.id}>{message.text}</Message>
          ))
        }
      </div>
    );
  }
}

export class Message extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        <span className={'message-' + this.props.type}>
          {this.props.children}
        </span>
      </div>
    );
  }
}