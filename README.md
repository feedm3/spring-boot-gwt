# Spring Boot GWT

[![Build Status](https://travis-ci.org/feedm3/spring-boot-gwt.svg)](https://travis-ci.org/feedm3/spring-boot-gwt)
[![GitHub license](https://img.shields.io/github/license/feedm3/spring-boot-gwt.svg)](http://choosealicense.com/licenses/mit/)

[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy?template=https://github.com/feedm3/spring-boot-gwt/blob/master)

## Run

To run this project you have to start Spring Boot and GWT separate. If you use IntelliJ the run configurations
to do this are already present in this repo.

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

The build tasks compiles all GWT related stuff and puts it into the `static` folder.

### Heroku

Heroku uses the gradle `stage` task to build the project. Because Spring Boot puts everything we
need into the jar file we only have to tell heroku to execute this jar file.

### Dependencies

Spring Boot uses the classpath to determine which servlet container to use. Since GWT has Jetty
in the classpath we have to put all GWT dependencies as provided. This is normally only possible
with the `war` task so we had to make our own `provided` task. The instructions for this can be
found on [Stackoverflow](http://stackoverflow.com/a/20841280/3141881).

If you add any dependencies for GWT add them to the provided dependencies.