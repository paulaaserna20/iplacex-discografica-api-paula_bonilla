FROM gradle:9-jdk21 AS build

WORKDIR /app

COPY . .

RUN gradle clean bootWar --no-daemon


FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/discografia-1.war app.war

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.war"]