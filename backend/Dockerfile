FROM openjdk:11-jdk

# FROM gradle:7.6.1-jdk11

ENV APP_HOME=/usr/app

WORKDIR $APP_HOME

COPY . .

# COPY ./manamana/build/libs/*.jar ./application.jar

RUN chmod +x gradlew

RUN ./gradlew clean build -x test

COPY ./resources ./resources

RUN ls -al build/libs
#RUN pwd

RUN cp ./build/libs/*.jar application.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","-Dspring.config.location=resources/application.yml,resources/application-deploy.yml", "-Dspring.profiles.active=deploy", "application.jar"]
