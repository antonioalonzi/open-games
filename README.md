# Open Games

Wonderful gaming platform application.



## Development

The application is written using:
 * Java (Spring)
 * Javascript (React)



### Requisites

 * Install JDK 1.8
 * Install maven
 * Install npm

### Build & Run

#### Build on Travis

[![Build Status](https://travis-ci.com/antonioalonzi/open-games.svg?branch=master)](https://travis-ci.com/antonioalonzi/open-games)

#### Build locally
    
Build js:

    npm install
    npm run build-dev

Run tests:
    
    mvn clean test
    npm run test

Run from command line:

    mvn spring-boot:run


## Deployment

Application is deployed at: https://open-games.herokuapp.com/

The deploy happens automatically after each commit on master if the build passed. 

 * Heroku: https://dashboard.heroku.com/apps/open-games
 * Travis: https://travis-ci.com/antonioalonzi/open-games
