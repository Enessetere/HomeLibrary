FROM openjdk:14
WORKDIR '/home_library'
ARG ARTIFACT_NAME=home-library-core-0.0.1-SNAPSHOT.jar
ARG ARTIFACT_PATH=target/${ARTIFACT_NAME}
COPY $ARTIFACT_PATH .
EXPOSE 9081
ENV SERVER_PORT=9081
ENTRYPOINT ["java", "-jar", "home-library-core-0.0.1-SNAPSHOT.jar"]