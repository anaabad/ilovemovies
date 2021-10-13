FROM adoptopenjdk/openjdk11

COPY build/libs/ilovemovies-0.0.1-SNAPSHOT.jar ilovemovies.jar
CMD ["java","-jar","ilovemovies.jar"]
