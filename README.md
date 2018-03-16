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
 * Install MongoDB

        # install and start mongo in docker   
        docker pull mongo
        docker run --name open-games-mongo -p 27017:27017 -d mongo --auth
        
        # create a mongo admin user
        docker exec -it open-games-mongo mongo admin
        db.createUser({ user: 'open-games-admin', pwd: 'open-games-admin', roles: [ { role: "userAdminAnyDatabase", db: "admin" } ] });
        quit()
        
        # connect to mongo using open-games-admin and create a open-games-owner and open-games-db 
        docker run -it --rm --link open-games-mongo:mongo mongo mongo -u open-games-admin -p open-games-admin --authenticationDatabase admin open-games-mongo/open-games-db
        db.createUser({ user: 'open-games-owner', pwd: 'open-games-owner-password', roles: [ { role: "dbOwner", db: "open-games-db" } ] });
        quit()
        
        # connect to mongo using open-games-owner
        docker run -it --rm --link open-games-mongo:mongo mongo mongo -u open-games-owner -p open-games-owner-password open-games-mongo/open-games-db

### Build & Run

    npm install
    npm run build-dev
    mvn spring-boot:run
