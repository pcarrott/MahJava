# MahJava
An implementation of a Hong Kong MahJong Simulator and multiple playing Agents, in the Java Programming Language.
Project for the Agentes Autónomos e Sistemas Multi-Agente course, 2020/21. 

## Dependencies
This project needs Maven and Java SDK 11 installed in order to run.

## Build
Execute `mvn clean compile assembly:single -DskipTests`. The resulting executable (JAR file) should be in the target/ folder, 
with name: `MahJava-1.0-SNAPSHOT-jar-with-dependencies.jar`.

## Launch

### With Maven
Execute `mvn clean compile exec:java` on the root of the project.

## With JAR file
Build the project, as described above and execute `java -jar target/MahJava-1.0-SNAPSHOT-jar-with-dependencies.jar`.



