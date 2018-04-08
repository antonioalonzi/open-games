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

To deploy latest code:

    npm run build-prod
    git push heroku master

To see production logs
    
    heroku logs
