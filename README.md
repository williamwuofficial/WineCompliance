# winecompliance

This project is an example full stack application for viewing grape breakdowns of different wines. 
Wine data has been provided and saved into the /serverside/resources/data folder.

## Project requirements
JDK 1.8 or later
https://www.oracle.com/java/technologies/downloads/
Gradle 4+
https://gradle.org/install/

## Running Application
The easiest way to run the application is to simply build and run the jar with the following commands
1. ./gradlew build
2. java -jar ./serverside/build/libs/serverside.jar
3. Navigate to localhost:8080 in Chrome

## Setup for development
During development it's easier to run the client and server separately. Therefore, navigate to the clientside and serverside folders and run the following commands. 

### Clientside
1. 'npm install'
2. 'npm start'

### Serverside
2. ./gradlew booRun

## Assumptions
1. No application security - authentication, authorisation, etc. 
2. No database setup. The data is loaded into the application at startup
3. Immutable data files during runtime - if the data files in the resource folder are edited, then the server must be restarted or the jar file recompiled. 
4. Null values are handled by the frontend, not backend. The backend will return null value matching the provided data files. Afterwards null values will be displayed by the frontend as "No description", "Not provided", etc.  
5. Since the provided data uses floats as percentage values, the serverside responses will also use floats as percentage values. 



