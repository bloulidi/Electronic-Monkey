## Description 
**Electronic Monkey** is an Online buy and sell application, built with Spring Boot for the backend and Angular for the frontend.

![MicrosoftTeams-image (6)](https://user-images.githubusercontent.com/39305283/132733042-2c9eddc4-f5f9-4e17-adb4-e4f2ed4b4609.png)

## Requirements
1. Java 11
2. Maven -3.x.x
3. MongoDB 5.0
4. MySQL 8.0

## Steps to Setup
1. Clone the application
git clone https://gitlab-cgi.stackroute.in/cgi-canada-wave1-capstone-projects/onlinebuyandsell.git

1. Start MySQL service and MongoDB service in your local machine
(the password for My SQL should be password by default otherwise you can set it in the file application.properties in userservice)

2. Build and run the backend app using maven with IntelliJ IDEA :
    *     If it is the first run, then run `mvn clean install`.
    *     Run eureka-service. It will start at http://localhost:8761/.
    *     Run api-gateway-service. It will start at http://localhost:8080/.
    *     Run userservice. It will start at http://localhost:8090/.
    *     Run orderservice. It will start at http://localhost:8880/.
    *     Run catalogservice. It will start at http://localhost:8800/.

3. Run the frontend app using npm with VSCode
    *  `cd webApp`    
    *  `npm install`
    *  Run `ng serve -o` for a dev server. Navigate to http://localhost:4200/. The app will automatically reload if you change any of the source files.
   ![MicrosoftTeams-image (5)](https://user-images.githubusercontent.com/39305283/132732728-88be6ae3-5309-4727-81f6-2703f0eaeb9a.png)




    * Click the sign up button and fill the form to create an acount. 
   ![MicrosoftTeams-image (4)](https://user-images.githubusercontent.com/39305283/132732705-21535f77-fe54-4a91-bd14-bb75d425d7c4.png)


    
    * login in with the new account to use the app ! 
   ![MicrosoftTeams-image (3)](https://user-images.githubusercontent.com/39305283/132732886-9a3a1625-932e-4689-adb7-dbd76a44e1c3.png)

## Swagger API
For API Documentation and direct interactions with the backend app using Swagger, navigate to **http://localhost:xxxx/swagger-ui/**                                                                                                                                     
xxxx is the port number for the microservice you want to interact with.
