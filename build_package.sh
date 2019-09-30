#!/bin/bash

mvn clean
mvn compile
mvn package
mvn assembly:single
