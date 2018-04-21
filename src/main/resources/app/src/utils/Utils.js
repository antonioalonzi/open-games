import React from 'react'

export class Utils extends React.Component {
  static isBlank(str) {
    return (!str || /^\s*$/.test(str))
  }

  static dispatchEvent(name, value) {
    let customEvent = document.createEvent('Event')
    customEvent.initEvent(name, true, true)
    customEvent.value = value
    document.getElementsByTagName('body')[0].dispatchEvent(customEvent)
  }

  static addEventListener(type, func) {
    document.getElementsByTagName('body')[0].addEventListener(type, func)
  }

  static removeEventListener(type, func) {
    document.getElementsByTagName('body')[0].removeEventListener(type, func)
  }

  static checkAuthenticatedUser(user, router) {
    if (!user) {
      router.history.push('/portal/')
    }
  }

  static isMobile() {
    return window.innerWidth < 900
  }

  static isDesktop() {
    return window.innerWidth >= 900
  }
}