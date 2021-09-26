FROM openjdk:11

#ADD target/bloo
#ARG jar_file=target/*.jar
#COPY ${jar_file} blogapi.jar

#This line is add for the purpose of knowing the owner of this code
LABEL maintainer="fushioncode"
ADD target/blogApiTestingwithSwagger-0.0.1-SNAPSHOT.jar blogapi.jar
#EXPOSE "7000:7000"
ENTRYPOINT ["java", "-jar", "blogapi.jar"]