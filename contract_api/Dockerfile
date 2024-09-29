FROM ubuntu:22.04

RUN apt-get update
RUN apt-get install -y openjdk-17-jdk openjdk-17-jre maven

COPY . /order_contract-app

WORKDIR /order_contract-app

RUN mvn clean install

WORKDIR /order_contract-app/target

CMD ["java","-jar","order_contract-0.0.1-SNAPSHOT.jar"]

