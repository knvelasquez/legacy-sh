FROM openjdk:11

MAINTAINER Kevin Velásquez

LABEL Kevin Velasquez <knvelasquez@outlook.com>

WORKDIR /app

EXPOSE 8585

ENTRYPOINT ["java","-jar","target/SuperHeroe-2.0.0.jar"]