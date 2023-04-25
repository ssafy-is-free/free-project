FROM openjdk:11-jdk

ENV APP_HOME=/usr/app

WORKDIR $APP_HOME

COPY . .

#COPY ./manamana/build/libs/*.jar ./application.jar

RUN chmod +x gradlew

RUN ./gradlew clean build -x test

COPY ./resources ./resources

COPY ./backend/build/libs/*.jar ./application.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","-Dspring.config.location=resources/application.yml,resources/application-deploy.yml", "-Dspring.profiles.active=deploy", "application.jar"]
