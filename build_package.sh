#!/bin/bash

mvn clean
mvn compile
mvn package
mvn assembly::single
java -cp target/mmouse-uml-0.0.2-SNAPSHOT.jar:target/mmouse-uml-0.0.2-SNAPSHOT-jar-with-dependencies.jar utility.Main
