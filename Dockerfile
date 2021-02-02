FROM openjdk:8
COPY ./target/workoutlog-V1.0.jar workoutlog-V1.0.jar
ENTRYPOINT ["java","-jar","workoutlog-V1.0.jar"]