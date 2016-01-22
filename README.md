# Spring Boot GWT

[![Heroku](http://heroku-badge.herokuapp.com/?app=spring-boot-gwt&style=flat)](https://spring-boot-gwt.herokuapp.com/)
[![Build Status](https://travis-ci.org/feedm3/spring-boot-gwt.svg)](https://travis-ci.org/feedm3/spring-boot-gwt)
[![License](http://img.shields.io/:license-mit-blue.svg)](http://badges.mit-license.org)
[![Actively Maintained](https://maintained.tech/badge.svg)](https://maintained.tech/)

[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy?template=https://github.com/feedm3/spring-boot-gwt/blob/master)

This is a demo project to demonstrate Spring Boot in conjunction with GWT. It uses the latest
dependencies (Spring Boot 1.3.2 and GWT 2.8 beta) and Java 8. The live deployed app can be found [here](https://spring-boot-gwt.herokuapp.com/).

## Run

To run this project you have to start Spring Boot and GWT separate. If you use IntelliJ the run configurations
to do this are already present in this repo.

Spring Boot can also be started with gradle.

```
gradlew bootRun
```

## Test

Only the server side code is currently tested. To run the tests use the following command

```
gradlew test
```

We use [Spock](https://github.com/spockframework/spock) as testing framework because of the great
readability, syntax and build in features.

## Build

The project gets build to a single jar file with an embedded tomcat.

```
gradlew build
```

After gradle build the project the finished jar file is in `build/libs/spring-boot-gwt-1.0.0.jar`
and can simply be started with

```
java -jar spring-boot-gwt-0.0.1-SNAPSHOT.jar
```

The build tasks compiles all GWT related stuff and puts it into the [`static`](src/main/resources/static) folder.

### Heroku

To deploy this app to heroku use the __Deploy to Heroku__ Button on the top.

Heroku uses the gradle `stage` task to build the project. Because Spring Boot puts everything we
need into the jar file we only have to tell heroku to execute this jar file.

## Technical Details

### Architecture

![Architecture](docs/architecture.jpg)

The client side and server side are strictly separated. The GWT files are in the `client` package
(except the `.gwt.xml`) and the server side code is in the `server` package. All static client code
like the `index.html` and css files are inside the [`static`](src/main/resources/static) folder. Gradle
will also put the compiled sources in this folder.

The communication is made via JSON for which reason we have make 2 implementations of the object we send.

### Dependencies

Spring Boot uses the classpath to determine which servlet container to use (tomcat comes with the Spring
Boot dependencies). Since GWT also has Jetty within the classpath we have to put all GWT dependencies
as provided. This is normally only possible with the `war` task so we had to make our own `provided` task.
The instructions for this can be found on [Stackoverflow](http://stackoverflow.com/a/20841280/3141881).

If you add any dependencies for GWT add them to the provided dependencies.

## Known Issues

- If you start the server and client with IntelliJ the requests may not work and an `IllegalArgumentException`
rises. The error can be solved by __starting the server with gradle__. It seems that IntelliJ does something
wrong with spring boot. The exact error message is

```Caused by: java.lang.IllegalArgumentException: No converter found for return value of type: class java.util.LinkedHashMap```