FROM maven:3.8.3-openjdk-16 AS build
WORKDIR /account-sharing-app
COPY . .
RUN mvn clean package -DskipTests
##
FROM openjdk:16-jdk-alpine
WORKDIR /account-sharing-app
COPY --from=build /account-sharing-app/target .
EXPOSE 8080
# this should be dynamic as the account sharing app will not be named this all the time
# ENTRYPOINT [ "java","-jar","account-sharing-app-0.0.1-SNAPSHOT.jar" ]
# an approach m thinking abt is to rename the file
RUN mv `ls *.jar | head -1` output.jar
ENTRYPOINT [ "java","-jar","output.jar" ]