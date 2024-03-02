FROM openjdk:17-jdk-slim
WORKDIR /app
COPY ./target/hs-auth-0.0.1-SNAPSHOT.jar ./hs-auth.jar
ENTRYPOINT ["java","-jar","/app/hs-auth.jar"]