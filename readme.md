## Description 
**Electronic Monkey** ![monkey](/uploads/1cb55cfc47a1fc8e1d8157794f93d3b1/monkey.png) is an Online buy and sell application, built with Spring Boot for the backend and Angular for the frontend.

## Requirements
1. Java 11
2. Maven -3.x.x
3. MongoDB 
4. MySQL 

## Steps to Setup
1. Clone the application
git clone https://gitlab-cgi.stackroute.in/cgi-canada-wave1-capstone-projects/onlinebuyandsell.git

1. Start MySQL service and MongoDB service in your local machine.

2. Build and run the backend app using maven with IntelliJ IDEA :
    *     Run api-gateway-service. It will start at http://localhost:8080/.
    *     Run userservice. It will start at http://localhost:8090/.
    *     Run orderservice. It will start at http://localhost:8880/.
    *     Run catalogservice. It will start at http://localhost:8800/.

3. Run the frontend app using npm with VSCode
    *  `cd webApp`    
    *  `npm install`
    *  Run `ng serve -o` for a dev server. Navigate to http://localhost:4200/. The app will automatically reload if you change any of the source files.
