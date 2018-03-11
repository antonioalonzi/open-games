import React from 'react';
import {Utils} from "../utils/Utils";

export class Messages extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      messages: [],
      lastId: 0
    }
  }

  componentDidMount() {
    Utils.addEventListener('message', this._onMessage);
    Utils.messagesComponent = this;
  }

  componentWillUnmount() {
    Utils.removeEventListener('message', this._onMessage);
  }

  _onMessage(message) {
    Utils.messagesComponent.onMessage(message);
  }

  onMessage(event) {
    const messageId = this.generateMessageId();
    let messages = this.state.messages;
    messages.push({
      id: messageId,
      type: event.value.type,
      text: event.value.text
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