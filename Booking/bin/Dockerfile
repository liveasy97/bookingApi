FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8090
VOLUME /tmp
ADD target/Booking-0.0.1-SNAPSHOT.jar  booking.jar
ENTRYPOINT ["java","-jar","/booking.jar"]
