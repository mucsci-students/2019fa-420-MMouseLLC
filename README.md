# MMouseLLC

## Installation guide

* Ensure that maven package manager is installed
  * [Maven](https://maven.apache.org/install.html)

### Option 1: Run bash scripts

In the root project directory, run

> ./build_package.sh </br>
> ./run_uml.sh

This should successfully build and test the project and then open the CLI. 

### Option 2: Run commands 

In the root project directory, run

> mvn clean install <br>
> mvn package <br>
> java -cp target/mmouse-uml-0.0.2-SNAPSHOT-jar-with-dependencies.jar utility.Main
