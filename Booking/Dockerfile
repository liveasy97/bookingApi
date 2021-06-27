FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 9090
VOLUME /tmp
ADD target/booking.jar booking.jar
ENTRYPOINT ["java","-jar","/booking.jar"]
