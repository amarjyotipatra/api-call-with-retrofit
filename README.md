# How to Run a Java File Using JAR and Gradle Build Tools from CLI

## Setup a Basic Gradle Project Using CLI
To initialize a basic Gradle project for a Java application, run:

```sh
gradle init --use-defaults --type java-application
```
This command sets up a basic Gradle project structure.

## Build the Project
To build the project, use:

```sh
./gradlew build
```

## Configure the Manifest for Executable JAR
To make the JAR file executable, specify the `Main-Class` in `build.gradle`:

```groovy
jar {
    manifest {
        attributes (
            'Main-Class': 'org.example.Main'
        )
    }
}
```

## Generate the JAR File
Run the following command to create a JAR file:

```sh
./gradlew jar
```
The JAR file will be created inside the `build/libs` directory.

## Run the Executable JAR
Execute the JAR file using:

```sh
java -jar build/libs/filename.jar
```
This command will run your Java application.

