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

    npm install
    npm run build-dev
    mvn spring-boot:run

### Deploy

Application is deployed at: https://open-games.herokuapp.com/

To deploy latest code:

    npm run build-dev
    git add -f src/main/resources/public/js/open-games.js
    git push heroku master

To see production logs
    
    heroku logs