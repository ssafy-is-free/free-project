FROM openjdk:11-jdk

ENV APP_HOME=/usr/app

WORKDIR $APP_HOME

COPY . .

#COPY ./manamana/build/libs/*.jar ./application.jar

RUN ./gradlew clean build -x test

COPY ./resources ./resources

EXPOSE 8080

ENTRYPOINT ["java","-jar","-Dspring.config.location=resources/application.yml,resources/application-deploy.yml", "-Dspring.profiles.active=deploy", "application.jar"]
